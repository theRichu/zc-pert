package com.ziscloud.zcdiagram.draw;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.dao.DAOUtil;
import com.ziscloud.zcdiagram.dao.DrawMetaDAO;
import com.ziscloud.zcdiagram.dao.DrawNodeDAO;
import com.ziscloud.zcdiagram.dao.ProjectDAO;
import com.ziscloud.zcdiagram.dao.SessionFactory;
import com.ziscloud.zcdiagram.editor.DiagramEditorInput;
import com.ziscloud.zcdiagram.gef.Connection;
import com.ziscloud.zcdiagram.gef.Node;
import com.ziscloud.zcdiagram.pojo.DrawMeta;
import com.ziscloud.zcdiagram.pojo.DrawNode;
import com.ziscloud.zcdiagram.pojo.Project;

public class DrawDiagram {
	private Project project;
	private int model;

	public DrawDiagram(Project project, int model) {
		this.project = project;
		this.model = model;
	}

	public List<Node> draw() {
		List<Node> nodes = new ArrayList<Node>();
		ProjectDAO projectDAO = new ProjectDAO();
		Project curPrjct = projectDAO.findById(project.getId());
		if (null == curPrjct) {
			throw new RuntimeException(
					"Request to get the PERT diagram for project:"
							+ project.getId()
							+ "But this project can not be found in the database.");
		} else {
			// 如果工程项目没有生成过网络图，或者工程项目最后一次修改之后，还没有生成最新的网络图
			if (isDrawed(curPrjct)) {
				Transaction tx = null;
				// delete the out date data
				DAOUtil.deleteOutdateData(project, model);
				// copy the data from activity to drawMeta
				DAOUtil.copyFromActivityToDrawMeta(project, model);
				// start draw
				nodes = runDraw();
				// update field
				switch (model) {
				case DiagramEditorInput.PLAN:
					curPrjct.setDrawTime(new Date().getTime());
					break;
				case DiagramEditorInput.MODELONE:
					curPrjct.setOptOneDrawTime(new Date().getTime());
					break;
				case DiagramEditorInput.MODELTWO:
					curPrjct.setOptTwoDrawTime(new Date().getTime());
					break;
				}
				try {
					// 更新工程的相关信息
					tx = SessionFactory.getSession().beginTransaction();
					projectDAO.merge(curPrjct);
					tx.commit();
				} catch (HibernateException he) {
					if (null != tx)
						tx.rollback();
					throw new RuntimeException(
							"After draw diagram, update project failed.");
				} finally {
					SessionFactory.closeSession();
				}
			} else {
				nodes = readDraw();
			}
		}
		return nodes;
	}

	private boolean isDrawed(Project curPrjct) {
		switch (model) {
		case DiagramEditorInput.PLAN:
			return 0 == curPrjct.getDrawTime()
					|| (curPrjct.getDrawTime() - curPrjct.getModifyTime()) < 0;
		case DiagramEditorInput.MODELONE:
			return 0 == curPrjct.getOptOneDrawTime()
					|| (curPrjct.getOptOneDrawTime() - curPrjct.getModifyTime()) < 0;
		case DiagramEditorInput.MODELTWO:
			return 0 == curPrjct.getOptTwoDrawTime()
					|| (curPrjct.getOptTwoDrawTime() - curPrjct.getModifyTime()) < 0;
		}
		return true;
	}

	private List<Node> runDraw() {
		// 开始编号
		INumberNode numberNode = new NumberNodeForNoTimeScale(project, model);
		numberNode.number();
		// 开始布点
		ILayNode layNode = new LayNodeForNoTimeScale(project, model);
		layNode.layNode();
		return readDraw();
	}

	private List<Node> readDraw() {
		DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
		DrawNodeDAO nodeDAO = new DrawNodeDAO();
		List<DrawMeta> drawMetas = drawMetaDAO.findByProject(project, model);
		List<DrawNode> drawNodes = nodeDAO.findByProject(project, model);
		// start generate the diagram using GEF
		HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
		for (DrawNode dn : drawNodes) {
			// generate node
			nodes.put(dn.getId(), new Node(dn.getId(), new Point(dn.getX(), dn
					.getY()), dn.getLabel()));
			// System.out.println("after read DB");
			// System.out.println(dn.getId() + "-> x:" + dn.getX() + ", y:"
			// + dn.getY());
		}
		for (DrawMeta dm : drawMetas) {
			// generate activity
			String label;
			if (dm.getName().length() > 4) {
				label = dm.getName().substring(0, 4) + "...";
			} else {
				label = dm.getName();
			}
			@SuppressWarnings("unused")
			Connection connection = new Connection(dm.getActivitiy().getId(),
					nodes.get(dm.getDrawNodeByStartNode().getId()), nodes
							.get(dm.getDrawNodeByEndNode().getId()), label,
					Boolean.parseBoolean(dm.getIsCriticl()), Boolean
							.parseBoolean(dm.getIsVirtual()));
			// System.out.println(dm.getName() + "-> start:"
			// + dm.getDrawNodeByStartNode().getId() + ", end:"
			// + dm.getDrawNodeByEndNode().getId());
		}
		List<Node> nList = new ArrayList<Node>();
		nList.addAll(nodes.values());
		return nList;
	}
}
