package com.ziscloud.zcdiagram.draw;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.dao.DrawMetaDAO;
import com.ziscloud.zcdiagram.dao.DrawNodeDAO;
import com.ziscloud.zcdiagram.dao.RelationDAO;
import com.ziscloud.zcdiagram.dao.SessionFactory;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.pojo.DrawMeta;
import com.ziscloud.zcdiagram.pojo.DrawNode;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.pojo.Relation;

/**
 * User: ZisCloud Date: 2009-3-1 Time: 8:15:31
 */
public class NumberNodeForNoTimeScale implements INumberNode {
	private List<DrawMeta> drawMetaList;
	private HashSet<DrawNode> nodeSet;
	private Project project;
	private int model;

	public NumberNodeForNoTimeScale(Project project, int model) {
		DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
		this.project = project;
		this.model = model;
		this.drawMetaList = drawMetaDAO.findAllOrdered(project, model);
		this.nodeSet = new HashSet<DrawNode>();
	}

	public void number() {
		// 对工序进行排序
		sortProcess();
		// 保存排序的结果
		saveSortResult();
		// 对工序进行编号
		DrawNode maxLastNode = generateNumber();
		// 保存编号的结果
		saveNumberedResult();
		// 结尾汇点
		mergeLastNode(maxLastNode);
	}

	/**
	 * 根据紧前工序序列的size（升序）及包含关系（小集合在大集合之前）来排序工序，
	 */
	private void sortProcess() {
		nextDrawMeta: for (int i = 0; i < drawMetaList.size() - 1; i++) {
			DrawMeta drawMetaA = drawMetaList.get(i);
			DrawMeta drawMetaB = drawMetaList.get(i + 1);
			List<Relation> preForA = drawMetaA.getRltnForCurAct();
			List<Relation> preForB = drawMetaB.getRltnForCurAct();
			// 如果第i个工序、第i+1个工序的紧前为空，那么就跳过此次循环
			if (null == preForA || 0 == preForA.size()) {
				continue;
			}
			if (null == preForB || 0 == preForB.size()) {
				continue;
			}
			// 如果第i个工序的紧前工序的数量小于第i+1个工序的紧前工序数量，那么跳过此次循环
			if (preForA.size() >= preForB.size()) {
				continue;
			}
			// 如果第i+1个工序的紧前序列中不包含第i个工序的紧前工序，那么跳过此次循环
			for (Relation pre : preForA) {
				if (!preForB.contains(pre)) {
					continue nextDrawMeta;
				}
			}
			// 如果存在包含关系，那么调整第i个工序和第i+1个工序的次序
			DrawMeta tempDrawMeta = drawMetaA;
			drawMetaList.set(i, drawMetaB);
			drawMetaList.set(i + 1, tempDrawMeta);
		}
	}

	/**
	 * 对工序进行编号
	 * 
	 * @throws RuntimeException
	 */
	private DrawNode generateNumber() {
		int flag = 1;
		DrawNode tempNode = null;
		DrawNode firstNode = new DrawNode(generateNodeId(flag), flag, project);
		DrawNode maxLastNode = firstNode;
		List<DrawMeta> virtualDrawMetaList = new ArrayList<DrawMeta>();
		for (DrawMeta drawMeta : drawMetaList) {
			// System.out.println("go");
			// 当前工序的紧前工序集合
			List<Relation> preList = drawMeta.getRltnForCurAct();
			// List<Relation> preList =
			// prerelationDAO.findByProcess(drawMeta);
			DrawNode sNode = null;
			DrawNode tNode = new DrawNode(generateNodeId(flag + 1), ++flag,
					project);
			// 处理没有紧前工序的工序
			if (null == preList || 0 == preList.size()) {
				sNode = firstNode;
			}
			// 处理只有一个紧前工序的工序
			if (null != preList && 1 == preList.size()) {
				Relation pre = preList.get(0);
				tempNode = pre.getDrawMetaByPreAct().getDrawNodeByEndNode();
				if (null != tempNode) {
					sNode = tempNode;
					// System.out.println("只有一个紧前，紧前的结束节点的ID:" + sNode.getId());
				} else {
					throw new RuntimeException(drawMeta.getSymbol() + "的紧前工序"
							+ pre.getDrawMetaByPreAct().getSymbol()
							+ "结束节点为null.");
				}
			}
			// 处理有多个紧前的工序
			if (null != preList && preList.size() > 1) {
				// 对紧前工序集合进行分类，1、需要进行合并的，2、不需要合并的
				List[] splitedPreList = splitPreList(preList);
				List<Relation> noMergeDrawMetaList = splitedPreList[0];
				List<Relation> mergeDrawMetaList = splitedPreList[1];
				// System.out.println("需要进行合并的");
				// for (Relation prerelation : mergeDrawMetaList) {
				// System.out.println(prerelation.getId());
				// }
				// 合并结束节点
				DrawNode maxNode = null;
				if (0 < mergeDrawMetaList.size()) {
					maxNode = findMaximumNode(mergeDrawMetaList, -1);
					noMergeDrawMetaList.addAll(mergeTargetNode(maxNode,
							mergeDrawMetaList));
				}
				// 找出合并后的当前工序的紧前工序中最大的结束节点
				maxNode = findMaximumNode(noMergeDrawMetaList, flag);
				sNode = maxNode;
				if (sNode.getId() >= tNode.getId()) {
					flag += (Integer.parseInt(sNode.getLabel()) - Integer
							.parseInt(tNode.getLabel()));
					tNode = new DrawNode(generateNodeId(flag + 1), ++flag,
							project);
				}
				// 添加虚工序
				List<DrawNode> targetNodes = findAllTargetNode(
						noMergeDrawMetaList, maxNode);
				if (null != targetNodes && 0 < targetNodes.size()) {
					for (DrawNode virtualSourceNode : targetNodes) {
						virtualDrawMetaList.add(createVirtualProcess(
								virtualSourceNode, maxNode));
						// System.out.println(virtualSourceNode.getLabel() + ","
						// + maxNode.getLabel());
					}
				}
				// System.out.println("不需要进行合并的");
				// for (Relation prerelation : noMergeDrawMetaList) {
				// System.out.println(prerelation.getId());
				// }
			}
			drawMeta.setDrawNodeByStartNode(sNode);
			drawMeta.setDrawNodeByEndNode(tNode);
			if (!nodeSet.contains(sNode)) {
				nodeSet.add(sNode);
			}
			if (!nodeSet.contains(tNode)) {
				nodeSet.add(tNode);
			}
			if (Integer.parseInt(tNode.getLabel()) > Integer
					.parseInt(maxLastNode.getLabel())) {
				maxLastNode = tNode;
			}
			// System.out.println("正在编号的工序：" + drawMeta.getSymbol());
			// printNode();
			// System.out.println("stop");
			// System.out.println();
		}
		drawMetaList.addAll(virtualDrawMetaList);
		// System.out.println("编号结束：");
		// printNode();
		return maxLastNode;
	}

	/**
	 * 对一个工序的紧前工序序列进行分组，分组的标准：
	 * <p>
	 * 1、是否是同一个开始节点
	 * </p>
	 * <p>
	 * 2、是否有其他的工序 如果一个紧前工序没有其他紧前工序，而且其开始节点与其他的工序都不相同， <br />
	 * 那么这个紧前工序就是应该进行合并的。
	 * </p>
	 * 
	 * @param preList
	 *            某一个工序的紧前工序集合
	 * @return 分组后的集合
	 * @throws RuntimeException
	 */
	private List[] splitPreList(List<Relation> preList) {
		List[] Lists = new List[2];
		List<Relation> noMergeDrawMetaList = new ArrayList<Relation>();
		List<Relation> mergeProcsList = new ArrayList<Relation>();
		List<Relation> tempDrawMetaList = new ArrayList<Relation>();
		HashMap<DrawNode, Integer> nodeOutDegreeStatics = new HashMap<DrawNode, Integer>();
		// 根据有无其他紧后将紧前工序进行分组
		for (Relation pre : preList) {
			int size = new RelationDAO().countByPreDrawMeta(pre
					.getDrawMetaByPreAct());
			// System.out.println("size=" + size);
			if (1 == size) {
				// 如果紧前工序的紧后工序只有一个，那么此紧前工序可以进行合并
				// 每一个紧前工序的开始节点的出度要进行统计，如果出度>=2，
				// 那么就移除以此节点开始的所有工序，放入到不需要合并的集合中
				DrawNode sourceNode = pre.getDrawMetaByPreAct()
						.getDrawNodeByStartNode();
				if (null == sourceNode) {
					throw new RuntimeException("紧前工序("
							+ pre.getDrawMetaByPreAct().getSymbol()
							+ ")的开始节点为Null");
				}
				if (nodeOutDegreeStatics.containsKey(sourceNode)) {
					int tempNum = nodeOutDegreeStatics.get(sourceNode)
							.intValue();
					nodeOutDegreeStatics.put(sourceNode, ++tempNum);
				} else {
					nodeOutDegreeStatics.put(sourceNode, 1);
				}
				tempDrawMetaList.add(pre);
			} else {
				// 如果紧前工序的紧后工序不只一个，那么此紧前工序不需要进行合并
				noMergeDrawMetaList.add(pre);
			}
		}
		// 再根据开始节点的出度将节点进行分组
		for (Relation pre : tempDrawMetaList) {
			DrawNode sourceNode = pre.getDrawMetaByPreAct()
					.getDrawNodeByStartNode();
			if (1 == nodeOutDegreeStatics.get(sourceNode)) {
				mergeProcsList.add(pre);
			} else {
				noMergeDrawMetaList.add(pre);
			}
		}
		// 保存分组结果
		Lists[0] = noMergeDrawMetaList;
		Lists[1] = mergeProcsList;
		return Lists;
	}

	/**
	 * 将mergeList集合中的紧前工序的结束节点合并到targetNode
	 * 
	 * @param targetNode
	 *            结束节点
	 * @param mergeList
	 *            将要进行结束节点合并的紧前工序集合
	 * @throws RuntimeException
	 */
	private List<Relation> mergeTargetNode(DrawNode targetNode,
			List<Relation> mergeList) {
		if (null == targetNode || null == mergeList) {
			return null;
		}
		for (Relation pre : mergeList) {
			DrawMeta preDrawMeta = pre.getDrawMetaByPreAct();
			if (null == preDrawMeta) {
				throw new RuntimeException("合并结束节点时，紧前工序为Null");
			} else {
				preDrawMeta.setDrawNodeByEndNode(targetNode);
			}
		}
		return mergeList;
	}

	/**
	 * 结尾汇点： 1、 查询出所有没有紧后的工工序，并放入集合A中
	 * <p/>
	 * 2、 找到集合A中，最大的结束节点N
	 * <p/>
	 * 3、 将结合A进行分离，从每一个开始节点取出一个工序来构成集合B，剩余的工序构成集合C
	 * <p/>
	 * 4、 将集合B中的所有工序的结束节点设置为N
	 * <p/>
	 * 5、 从集合C中的每个工序的结束节点引虚工序到N
	 * <p/>
	 * 6、 保存汇点结果
	 * 
	 * @throws RuntimeException
	 */
	private void mergeLastNode(DrawNode maxLastNode) {
		// System.out.println("最大结束节点：" + maxLastNode.getLabel());
		// 查询出没有紧后的非虚工序
		DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
		List<DrawMeta> noSuffixDrawMetaList = drawMetaDAO.findNoSuffix(project,
				model);
		HashSet<DrawNode> nodeOfMergeDrawMetaSet = new HashSet<DrawNode>();
		List<DrawMeta> virtualDrawMetaSet = new LinkedList<DrawMeta>();
		for (DrawMeta drawMeta : noSuffixDrawMetaList) {
			// System.out.println(drawMeta.getSymbol());
			DrawNode sourceNode = drawMeta.getDrawNodeByStartNode();
			if (nodeOfMergeDrawMetaSet.contains(sourceNode)) {
				// boolean b =
				// virtualDrawMetaSet.add(createVirtualProcess(drawMeta
				// .getDrawNodeByEndNode(), maxLastNode));
				virtualDrawMetaSet.add(createVirtualProcess(drawMeta
						.getDrawNodeByEndNode(), maxLastNode));
				// System.out.print("create virtual:" +
				// drawMeta.getDrawNodeByEndNode().getLabel());
				// System.out.print("  success:" + b);
				// System.out.println("    virtualDrawMetaSet.size:"+virtualDrawMetaSet.size());
			} else {
				// System.out.println("add source:" + sourceNode.getLabel());
				nodeOfMergeDrawMetaSet.add(sourceNode);
				drawMeta.setDrawNodeByEndNode(maxLastNode);
			}
			// System.out.println("-------------end-------------------");
			// System.out.println();
		}
		noSuffixDrawMetaList.addAll(virtualDrawMetaSet);
		// System.out.println("虚工序:" + virtualDrawMetaSet.size());
		// for (DrawMeta drawMeta : virtualDrawMetaSet) {
		// System.out.print("工序编号：" + drawMeta.getSymbol());
		// System.out.print("	");
		// System.out.print("开始节点：");
		// System.out.print(drawMeta.getDrawNodeByStartNode().getLabel());
		// System.out.print("	");
		// System.out.print("结束节点：");
		// System.out.println(drawMeta.getDrawNodeByEndNode().getLabel());
		// }
		saveResultOfmergeLastNode(noSuffixDrawMetaList);
		// System.out.println("汇点结束到：" + maxLastNode.getLabel());
		// for (DrawMeta drawMeta : noSuffixDrawMetaList) {
		// System.out.print("工序编号：" + drawMeta.getSymbol());
		// System.out.print("	");
		// System.out.print("开始节点：");
		// System.out.print(drawMeta.getDrawNodeByStartNode().getLabel());
		// System.out.print("	");
		// System.out.print("结束节点：");
		// System.out.println(drawMeta.getDrawNodeByEndNode().getLabel());
		// }
	}

	/**
	 * 从不需要合并的紧前工序集合中，查找紧前工序的所有结束节点
	 * 
	 * @param noMergeDrawMetaList
	 *            不需要合并的紧前工序集合
	 * @return 所有结束节点
	 * @throws RuntimeException
	 */
	private List<DrawNode> findAllTargetNode(
			List<Relation> noMergeDrawMetaList, DrawNode maxNode) {
		if (null == noMergeDrawMetaList) {
			return null;
		}
		List<DrawNode> targetNodes = new ArrayList<DrawNode>();
		for (Relation pre : noMergeDrawMetaList) {
			DrawMeta drawMeta = pre.getDrawMetaByPreAct();
			if (null == drawMeta) {
				throw new RuntimeException("查找不需要合并的紧前工序的所有结束节点时，紧前工序为Null");
			}
			DrawNode node = drawMeta.getDrawNodeByEndNode();
			if (targetNodes.contains(node) || node.equals(maxNode)) {
				continue;
			} else {
				targetNodes.add(node);
			}
		}
		return targetNodes;
	}

	/**
	 * 查找出紧前工序序列中结束节点编号最大的节点
	 * 
	 * @param preList
	 *            紧前工序序列
	 * @return 编号最大的节点
	 */
	private DrawNode findMaximumNode(List<Relation> preList, int flag) {
		if (null == preList) {
			return null;
		}
		DrawMeta last = preList.get(0).getDrawMetaByPreAct();
		for (Relation prerelation : preList) {
			DrawMeta drawMeta = prerelation.getDrawMetaByPreAct();
			if (last.getEndDate().before(drawMeta.getEndDate())
					|| (last.getEndDate().equals(drawMeta.getEndDate()) && last
							.getDrawNodeByEndNode().compareTo(
									drawMeta.getDrawNodeByEndNode()) < 0)) {
				last = drawMeta;
			}
		}
		// 如果最大的结束节点的工序有其他的紧后，则返回当前最大节点+1后的节点
		if (-1 != flag && last.getRltnForPreAct().size() > 1) {
			return new DrawNode(generateNodeId(flag + 1), ++flag, project);
		} else {
			return last.getDrawNodeByEndNode();
		}
	}

	/**
	 * 根据节点的所在的工程项目的ID和节点的label生成节点的ID
	 * 
	 * @param label
	 *            节点的ID
	 * @return 生成的节点ID
	 */
	private int generateNodeId(int label) {
		return new Integer("" + project.getId() + model + label);
	}

	/**
	 * 保存工序的排序标识
	 * 
	 * @throws net.sourceforge.stripes.exception.RuntimeException
	 * 
	 */
	private void saveSortResult() {
		DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			for (int i = 0; i < drawMetaList.size(); i++) {
				DrawMeta drawMeta = drawMetaList.get(i);
				drawMeta.setOrdinal(i);
				// System.out.println(drawMeta.getSymbol() + " -> ordinal:" +
				// i);
				drawMetaDAO.attachDirty(drawMeta);
			}
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			throw new RuntimeException("保存工序排序信息时发生错误!", he);
		} finally {
			// 这里不能关闭session，否则后面的懒加载将无法进行
			// SessionFactory.closeSession();
		}
	}

	private void saveResultOfmergeLastNode(List<DrawMeta> drawMetaList) {
		DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			// System.out.println("saveResultOfmergeLastNode");
			for (DrawMeta drawMeta : drawMetaList) {
				drawMeta.setModel(model);
				// System.out.println(drawMeta.getId()+":"+drawMeta.getDrawNodeByStartNode().getId()+","
				// + drawMeta.getDrawNodeByEndNode().getId());
				drawMetaDAO.attachDirty(drawMeta);
			}
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			throw new RuntimeException("保存工序的编号结果时发生错误!", he);
		} finally {
			// 这里关闭session
			SessionFactory.closeSession();
		}
	}

	/**
	 * 1、保存编号产生的节点，保证工序的节点外键存在
	 * <p/>
	 * 2、更新工序的开始节点和结束节点，如果有虚工序，则保存虚工序
	 * 
	 * @throws RuntimeException
	 */
	private void saveNumberedResult() {
		DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
		DrawNodeDAO nodeDAO = new DrawNodeDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			// System.out.println("saveNumberedResult");
			for (DrawNode node : nodeSet) {
				node.setModel(model);
				nodeDAO.attachDirty(node);
			}
			for (DrawMeta drawMeta : this.drawMetaList) {
				drawMeta.setModel(model);
				// System.out.println(drawMeta.getId()+":"+drawMeta.getDrawNodeByStartNode().getId()+","
				// + drawMeta.getDrawNodeByEndNode().getId());
				drawMetaDAO.attachDirty(drawMeta);
			}
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			throw new RuntimeException("保存工序的编号结果时发生错误!", he);
		} finally {
			// 这里不能关闭session，否则后面的懒加载将无法进行
			// SessionFactory.closeSession();
		}
	}

	/**
	 * 创建虚工序
	 * 
	 * @param sourceNode
	 *            虚工序的开始节点，也就是虚工序的所有紧前工序的结束节点
	 * @param targetNode
	 *            虚工序的结束节点，其编号是目前的最大编号
	 * @return
	 */
	private DrawMeta createVirtualProcess(DrawNode sourceNode,
			DrawNode targetNode) {
		DrawMeta drawMeta = new DrawMeta();
		drawMeta.setSymbol("V" + sourceNode.getLabel() + targetNode.getLabel());
		drawMeta.setName("虚工序");
		drawMeta.setActivitiy(new Activity(0));
		drawMeta.setIsVirtual("true");
		drawMeta.setIsCriticl("false");
		drawMeta.setPeriod(0);
		drawMeta.setStartDate(new Date());
		drawMeta.setEndDate(new Date());
		drawMeta.setProject(project);
		drawMeta.setDrawNodeByStartNode(sourceNode);
		drawMeta.setDrawNodeByEndNode(targetNode);
		// 虚工序的开始时间和结束时间，之后再进行处理
		return drawMeta;
	}

	@SuppressWarnings("unused")
	private void printPreDrawMeta() {
		for (DrawMeta drawMeta : drawMetaList) {
			System.out.print(drawMeta.getSymbol());
			System.out.print(":");
			for (Relation prerelation : drawMeta.getRltnForCurAct()) {
				System.out.print(prerelation.getDrawMetaByPreAct().getId()
						+ ",");
			}
			System.out.println();
		}
	}

	@SuppressWarnings("unused")
	private void printNode() {
		for (DrawMeta drawMeta : drawMetaList) {
			System.out.print(drawMeta.getSymbol());
			System.out.print(":");
			if (null != drawMeta.getDrawNodeByStartNode())
				System.out.print("开始节点："
						+ drawMeta.getDrawNodeByStartNode().getId());
			System.out.print("      ");
			if (null != drawMeta.getDrawNodeByEndNode())
				System.out.print("结束节点："
						+ drawMeta.getDrawNodeByEndNode().getId());
			System.out.println();
		}
	}

	@SuppressWarnings("unused")
	private void printMeta() {
		for (DrawMeta drawMeta : drawMetaList) {
			System.out.println(drawMeta.getSymbol() + "-> size:"
					+ drawMeta.getRltnForCurAct().size());
		}
	}
}
