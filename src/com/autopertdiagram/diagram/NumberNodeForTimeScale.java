package com.autopertdiagram.diagram;

import com.autopertdiagram.dao.HibernateSessionFactory;
import com.autopertdiagram.dao.NodeDAO;
import com.autopertdiagram.dao.PrerelationDAO;
import com.autopertdiagram.dao.ProcessDAO;
import com.autopertdiagram.pojo.Node;
import com.autopertdiagram.pojo.Prerelation;
import com.autopertdiagram.pojo.Process;
import com.autopertdiagram.pojo.Project;
import net.sourceforge.stripes.exception.StripesServletException;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * User: ZisCloud
 * Date: 2009-3-1
 * Time: 8:15:31
 */
public class NumberNodeForTimeScale implements NumberNode {
    private List<Process> processList;
    private HashSet<Node> nodeSet;
    private Project project;

    public NumberNodeForTimeScale(Project project) {
        ProcessDAO processDAO = new ProcessDAO();
        this.project = project;
        this.processList = processDAO.findAllOrdered(project);
        this.nodeSet = new HashSet<Node>();
    }

    public void number() throws StripesServletException {
        // 对工序进行排序
        sortProcess();
        // 保存排序的结果
        saveSortResult();
        // 对工序进行编号
        Node maxLastNode = generateNumber();
        // 保存编号的结果
        saveNumberedResult();
        // 结尾汇点
        mergeLastNode(maxLastNode);
    }

    /**
     * 根据紧前工序序列的size（升序）及包含关系（小集合在大集合之前）来排序工序，
     */
    private void sortProcess() {
        nextPrcs:
        for (int i = 0; i < processList.size() - 1; i++) {
            Process prcsa = processList.get(i);
            Process prcsb = processList.get(i + 1);
            List<Prerelation> preForA = prcsa.getPrerelationsForProcess();
            List<Prerelation> preForB = prcsb.getPrerelationsForProcess();
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
            for (Prerelation pre : preForA) {
                if (!preForB.contains(pre)) {
                    continue nextPrcs;
                }
            }
            // 如果存在包含关系，那么调整第i个工序和第i+1个工序的次序
            Process tempPrcs = prcsa;
            processList.set(i, prcsb);
            processList.set(i + 1, tempPrcs);
        }
    }

    /**
     * 对工序进行编号
     *
     * @throws StripesServletException
     */
    private Node generateNumber() throws StripesServletException {
        int flag = 1;
        Node tempNode = null;
        Node firstNode = new Node(generateNodeId(flag), flag, project);
        Node maxLastNode = firstNode;
        List<Process> virtualPrcsList = new ArrayList<Process>();
        for (Process process : processList) {
            // System.out.println("go");
            // 当前工序的紧前工序集合
            List<Prerelation> preList = process.getPrerelationsForProcess();
            // List<Prerelation> preList =
            // prerelationDAO.findByProcess(process);
            Node sNode = null;
            Node tNode = new Node(generateNodeId(flag + 1), ++flag, project);
            // 处理没有紧前工序的工序
            if (null == preList || 0 == preList.size()) {
                sNode = firstNode;
            }
            // 处理只有一个紧前工序的工序
            if (null != preList && 1 == preList.size()) {
                Prerelation pre = preList.get(0);
                tempNode = pre.getProcessByPreProcess().getNodeByTargetNode();
                if (null != tempNode) {
                    sNode = tempNode;
                    // System.out.println("只有一个紧前，紧前的结束节点的ID:" + sNode.getId());
                } else {
                    throw new StripesServletException(process.getSymbol()+"DD");
                }
            }
            // 处理有多个紧前的工序
            if (null != preList && preList.size() > 1) {
                // 对紧前工序集合进行分类，1、需要进行合并的，2、不需要合并的
                List[] splitedPreList = splitPreList(preList);
                List<Prerelation> noMergePrcsList = splitedPreList[0];
                List<Prerelation> mergePrcsList = splitedPreList[1];
                // System.out.println("需要进行合并的");
                // for (Prerelation prerelation : mergePrcsList) {
                // System.out.println(prerelation.getId());
                // }
                // 合并结束节点
                Node maxNode = null;
                if (0 < mergePrcsList.size()) {
                    maxNode = findMaximumNode(mergePrcsList);
                    noMergePrcsList.addAll(mergeTargetNode(maxNode,
                            mergePrcsList));
                }
                // 找出合并后的当前工序的紧前工序中最大的结束节点
                maxNode = findMaximumNode(noMergePrcsList);
                sNode = maxNode;
                // 添加虚工序
                List<Node> targetNodes = findAllTargetNode(noMergePrcsList,
                        maxNode);
                if (null != targetNodes && 0 < targetNodes.size()) {
                    for (Node virtualSourceNode : targetNodes) {
                        virtualPrcsList.add(createVirtualProcess(
                                virtualSourceNode, maxNode));
                    }
                }
                // System.out.println("不需要进行合并的");
                // for (Prerelation prerelation : noMergePrcsList) {
                // System.out.println(prerelation.getId());
                // }
            }
            process.setNodeBySourceNode(sNode);
            process.setNodeByTargetNode(tNode);
            if (!nodeSet.contains(sNode)) {
                nodeSet.add(sNode);
            }
            if (!nodeSet.contains(tNode)) {
                nodeSet.add(tNode);
            }
            if (tNode.getLabel() > maxLastNode.getLabel()) {
                maxLastNode = tNode;
            }
            // System.out.println("正在编号的工序：" + process.getSymbol());
            // printNode();
            // System.out.println("stop");
            // System.out.println();
        }
        processList.addAll(virtualPrcsList);
        // System.out.println("编号结束：");
        // printNode();
        return maxLastNode;
    }

    /**
     * 对一个工序的紧前工序序列进行分组，分组的标准： 1、是否是同一个开始节点 2、是否有其他的工序
     * 如果一个紧前工序没有其他紧前工序，而且其开始节点与其他的工序都不相同， 那么这个紧前工序就是应该进行合并的。
     *
     * @param preList 某一个工序的紧前工序集合
     * @return 分组后的集合
     * @throws StripesServletException
     */
    private List[] splitPreList(List<Prerelation> preList)
            throws StripesServletException {
        List[] Lists = new List[2];
        List<Prerelation> noMergePrcsList = new ArrayList<Prerelation>();
        List<Prerelation> mergeProcsList = new ArrayList<Prerelation>();
        List<Prerelation> tempPrcsList = new ArrayList<Prerelation>();
        HashMap<Node, Integer> nodeOutDegreeStatics = new HashMap<Node, Integer>();
        // 根据有无其他紧后将紧前工序进行分组
        for (Prerelation pre : preList) {
            int size = new PrerelationDAO().countByPreProcess(pre
                    .getProcessByPreProcess());
            // System.out.println("size=" + size);
            if (1 == size) {
                // 如果紧前工序的紧后工序只有一个，那么此紧前工序可以进行合并
                // 每一个紧前工序的开始节点的出度要进行统计，如果出度>=2，
                // 那么就移除以此节点开始的所有工序，放入到不需要合并的集合中
                Node sourceNode = pre.getProcessByPreProcess()
                        .getNodeBySourceNode();
                if (null == sourceNode) {
                    throw new StripesServletException("紧前工序("
                            + pre.getProcessByPreProcess().getSymbol()
                            + ")的开始节点为Null");
                }
                if (nodeOutDegreeStatics.containsKey(sourceNode)) {
                    int tempNum = nodeOutDegreeStatics.get(sourceNode)
                            .intValue();
                    nodeOutDegreeStatics.put(sourceNode, ++tempNum);
                } else {
                    nodeOutDegreeStatics.put(sourceNode, 1);
                }
                tempPrcsList.add(pre);
            } else {
                // 如果紧前工序的紧后工序不只一个，那么此紧前工序不需要进行合并
                noMergePrcsList.add(pre);
            }
        }
        // 再根据开始节点的出度将节点进行分组
        for (Prerelation pre : tempPrcsList) {
            Node sourceNode = pre.getProcessByPreProcess()
                    .getNodeBySourceNode();
            if (1 == nodeOutDegreeStatics.get(sourceNode)) {
                mergeProcsList.add(pre);
            } else {
                noMergePrcsList.add(pre);
            }
        }
        // 保存分组结果
        Lists[0] = noMergePrcsList;
        Lists[1] = mergeProcsList;
        return Lists;
    }

    /**
     * 将mergeList集合中的紧前工序的结束节点合并到targetNode
     *
     * @param targetNode 结束节点
     * @param mergeList  将要进行结束节点合并的紧前工序集合
     * @throws StripesServletException
     */
    private List<Prerelation> mergeTargetNode(Node targetNode,
                                              List<Prerelation> mergeList) throws StripesServletException {
        if (null == targetNode || null == mergeList) {
            return null;
        }
        for (Prerelation pre : mergeList) {
            Process prePrcs = pre.getProcessByPreProcess();
            if (null == prePrcs) {
                throw new StripesServletException("合并结束节点时，紧前工序为Null");
            } else {
                prePrcs.setNodeByTargetNode(targetNode);
            }
        }
        return mergeList;
    }

    /**
     * 结尾汇点：
     * 1、 查询出所有没有紧后的工工序，并放入集合A中
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
     * @throws StripesServletException
     */
    private void mergeLastNode(Node maxLastNode) throws StripesServletException {
        // System.out.println("最大结束节点：" + maxLastNode.getLabel());
        // 查询出没有紧后的非虚工序
        ProcessDAO processDAO = new ProcessDAO();
        List<Process> noSuffixPrcsList = processDAO.findNoSuffix(project);
        HashSet<Node> nodeOfMergePrcsSet = new HashSet<Node>();
        HashSet<Process> virtualPrcsSet = new HashSet<Process>();
        for (Process prcs : noSuffixPrcsList) {
            // System.out.println(prcs.getSymbol());
            Node sourceNode = prcs.getNodeBySourceNode();
            if (nodeOfMergePrcsSet.contains(sourceNode)) {
                virtualPrcsSet.add(createVirtualProcess(prcs.getNodeByTargetNode(), maxLastNode));
            } else {
                nodeOfMergePrcsSet.add(sourceNode);
                prcs.setNodeByTargetNode(maxLastNode);
            }
        }
        noSuffixPrcsList.addAll(virtualPrcsSet);
        saveResultOfmergeLastNode(noSuffixPrcsList);
        // System.out.println("汇点结束：");
        // for (Process prcs : noSuffixPrcsList) {
        // 	System.out.print("工序编号：" + prcs.getSymbol());
        // 	System.out.print("	");
        // 	System.out.print("开始节点：");
        // 	System.out.print(prcs.getNodeBySourceNode().getLabel());
        // 	System.out.print("结束节点：");
        // 	System.out.println(prcs.getNodeByTargetNode().getLabel());
        // }
    }

    /**
     * 从不需要合并的紧前工序集合中，查找紧前工序的所有结束节点
     *
     * @param noMergePrcsList 不需要合并的紧前工序集合
     * @return 所有结束节点
     * @throws StripesServletException
     */
    private List<Node> findAllTargetNode(List<Prerelation> noMergePrcsList,
                                         Node maxNode) throws StripesServletException {
        if (null == noMergePrcsList) {
            return null;
        }
        List<Node> targetNodes = new ArrayList<Node>();
        for (Prerelation pre : noMergePrcsList) {
            Process prcs = pre.getProcessByPreProcess();
            if (null == prcs) {
                throw new StripesServletException(
                        "查找不需要合并的紧前工序的所有结束节点时，紧前工序为Null");
            }
            Node node = prcs.getNodeByTargetNode();
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
     * @param preList 紧前工序序列
     * @return 编号最大的节点
     */
    private Node findMaximumNode(List<Prerelation> preList) {
        if (null == preList) {
            return null;
        }
        Process last = preList.get(0).getProcessByPreProcess();
        for (Prerelation prerelation : preList) {
            Process prcs = prerelation.getProcessByPreProcess();
            if (last.getPlanEndDate().before(prcs.getPlanEndDate())
                    || (last.getPlanEndDate().equals(prcs.getPlanEndDate()) && last
                    .getNodeByTargetNode().compareTo(
                            prcs.getNodeByTargetNode()) < 0)) {
                last = prcs;
            }
        }
        return last.getNodeByTargetNode();
    }

    /**
     * 根据节点的所在的工程项目的ID和节点的label生成节点的ID
     *
     * @param label 节点的ID
     * @return 生成的节点ID
     */
    private int generateNodeId(int label) {
        return new Integer("" + project.getId() + label);
    }

    /**
     * 保存工序的排序标识
     *
     * @throws net.sourceforge.stripes.exception.StripesServletException
     *
     */
    private void saveSortResult() throws StripesServletException {
        ProcessDAO processDAO = new ProcessDAO();
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            for (int i = 0; i < processList.size(); i++) {
                Process process = processList.get(i);
                process.setOrdering(i);
                processDAO.attachDirty(process);
            }
            tx.commit();
        } catch (HibernateException he) {
            if (null != tx) {
                tx.rollback();
            }
            throw new StripesServletException("保存工序排序信息时发生错误!", he);
        } finally {
            // 这里不能关闭session，否则后面的懒加载将无法进行
            // HibernateSessionFactory.closeSession();
        }
    }

    private void saveResultOfmergeLastNode(List<Process> prcsList) throws StripesServletException {
        ProcessDAO processDAO = new ProcessDAO();
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            for (Process prcs : prcsList) {
                processDAO.attachDirty(prcs);
            }
            tx.commit();
        } catch (HibernateException he) {
            if (null != tx) {
                tx.rollback();
            }
            throw new StripesServletException("保存工序的编号结果时发生错误!", he);
        } finally {
            // 这里关闭session
            HibernateSessionFactory.closeSession();
        }
    }

    /**
     * 1、保存编号产生的节点，保证工序的节点外键存在
     * <p/>
     * 2、更新工序的开始节点和结束节点，如果有虚工序，则保存虚工序
     *
     * @throws StripesServletException
     */
    private void saveNumberedResult() throws StripesServletException {
        ProcessDAO processDAO = new ProcessDAO();
        NodeDAO nodeDAO = new NodeDAO();
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            for (Node node : nodeSet) {
                nodeDAO.attachDirty(node);
            }
            for (Process prcs : this.processList) {
                processDAO.attachDirty(prcs);
            }
            tx.commit();
        } catch (HibernateException he) {
            if (null != tx) {
                tx.rollback();
            }
            throw new StripesServletException("保存工序的编号结果时发生错误!", he);
        } finally {
            // 这里不能关闭session，否则后面的懒加载将无法进行
            // HibernateSessionFactory.closeSession();
        }
    }

    /**
     * 创建虚工序
     *
     * @param sourceNode 虚工序的开始节点，也就是虚工序的所有紧前工序的结束节点
     * @param targetNode 虚工序的结束节点，其编号是目前的最大编号
     * @return
     */
    private Process createVirtualProcess(Node sourceNode, Node targetNode) {
        Process virtualPrcs = new Process();
        virtualPrcs.setSymbol("V" + sourceNode.getLabel()
                + targetNode.getLabel());
        virtualPrcs.setName("虚工序");
        virtualPrcs.setIsVirtual("true");
        virtualPrcs.setIsCriticl("false");
        virtualPrcs.setPlanPeriod(0);
        virtualPrcs.setPlanCost(0.0);
        virtualPrcs.setOutput(0.0);
        virtualPrcs.setProject(project);
        virtualPrcs.setNodeBySourceNode(sourceNode);
        virtualPrcs.setNodeByTargetNode(targetNode);
        // 虚工序的开始时间和结束时间，之后再进行处理
        return virtualPrcs;
    }

    private void printPreProcess() {
        for (Process process : processList) {
            System.out.print(process.getSymbol());
            System.out.print(":");
            for (Prerelation prerelation : process.getPrerelationsForProcess()) {
                System.out.print(prerelation.getProcessByPreProcess().getId()
                        + ",");
            }
            System.out.println();
        }
    }

    private void printNode() {
        for (Process process : processList) {
            System.out.print(process.getSymbol());
            System.out.print(":");
            if (null != process.getNodeBySourceNode())
                System.out.print("开始节点："
                        + process.getNodeBySourceNode().getLabel());
			System.out.print("      ");
			if (null != process.getNodeByTargetNode())
				System.out.print("结束节点："
						+ process.getNodeByTargetNode().getLabel());
			System.out.println();
		}
	}
}
