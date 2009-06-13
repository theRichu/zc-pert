package com.ziscloud.zcdiagram.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.dialog.PrePickerDialog;
import com.ziscloud.zcdiagram.editor.DiagramEditorInput;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.pojo.DrawMeta;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.pojo.Relation;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class DAOUtil {

	public static void updateActivityToDababase(Activity activity) {
		ActivitiyDAO activitiyDAO = new ActivitiyDAO();
		ProjectDAO projectDAO = new ProjectDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			activitiyDAO.attachDirty(activity);
			Project project = activity.getProject();
			project.setModifyTime(new Date().getTime());
			projectDAO.attachDirty(project);
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			throw new RuntimeException("保存工序信息失败！", he);
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void updateProjectToDatabase(Project project) {
		ProjectDAO projectDAO = new ProjectDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			projectDAO.attachDirty(project);
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			throw new RuntimeException("保存工程项目时信息失败！", he);
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void saveActivityToDatabase(Activity activity) {
		ActivitiyDAO activitiyDAO = new ActivitiyDAO();
		ProjectDAO projectDAO = new ProjectDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			activitiyDAO.save(activity);
			Project project = activity.getProject();
			project.setModifyTime(new Date().getTime());
			projectDAO.attachDirty(project);
			// set symbol
			activity.setSymbol(project.getSymbol() + activity.getId());
			activitiyDAO.attachDirty(activity);
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			throw new RuntimeException("保存工序信息失败！", he);
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void copyFromActivityToDrawMeta(Project project, int model) {
		ActivitiyDAO activitiyDAO = new ActivitiyDAO();
		DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
		RelationDAO relationDAO = new RelationDAO();
		List<Activity> activities = activitiyDAO.findByProject(project);
		List<DrawMeta> drawMetas = new LinkedList<DrawMeta>();
		Map<String, String[]> actAndPre = new HashMap<String, String[]>();
		Map<String, DrawMeta> actAndDrawMeta = new HashMap<String, DrawMeta>();
		for (Activity act : activities) {
			//
			if (!StringUtils.isBlank(act.getPreActivity())) {
				actAndPre.put(act.getSymbol(), act.getPreActivity().split(
						PrePickerDialog.PRE_TOKEN));
			}
			//
			DrawMeta dm = new DrawMeta();
			dm.setActivitiy(act);
			dm.setProject(project);
			dm.setModel(model);
			dm.setName(act.getName());
			dm.setSymbol(act.getSymbol());
			dm.setIsVirtual("false");
			dm.setIsCriticl("false");
			if (null != act.getActualStartDate()) {
				dm.setIsStarted("true");
			} else {
				dm.setIsStarted("false");
			}
			if (null != act.getActualEndDate()) {
				dm.setIsEnded("true");
			} else {
				dm.setIsEnded("false");
			}
			dm.setOrdinal(1);
			if (DiagramEditorInput.PLAN == model) {
				dm.setStartDate(act.getPlanStartDate());
				dm.setEndDate(act.getPlanEndDate());
				dm.setPeriod(act.getPlanPeriod());
			}
			if (DiagramEditorInput.MODELONE == model) {
				if (null == act.getPopStartDate()
						|| null == act.getPopEndDate()) {
					throw new RuntimeException("还未进行优化，请优化后再选择网络图的优化");
				} else {
					dm.setStartDate(act.getPopStartDate());
					dm.setEndDate(act.getPopEndDate());
					dm.setPeriod(SWTHelper.intervalDays(act.getPopStartDate(),
							act.getPopEndDate()));
				}
			}
			if (DiagramEditorInput.MODELTWO == model) {
				if (null == act.getOptopStartDate()
						|| null == act.getOptopEndDate()) {
					throw new RuntimeException("还未进行优化，请优化后再选择网络图的优化");
				} else {
					dm.setStartDate(act.getOptopStartDate());
					dm.setEndDate(act.getOptopEndDate());
					dm.setPeriod(SWTHelper.intervalDays(
							act.getOptopStartDate(), act.getOptopEndDate()));
				}
			}
			// add it to list
			drawMetas.add(dm);
		}
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			for (DrawMeta dm : drawMetas) {
				drawMetaDAO.save(dm);
				actAndDrawMeta.put(dm.getSymbol(), dm);
			}
			for (String actSymbol : actAndPre.keySet()) {
				for (String preActSymbol : actAndPre.get(actSymbol)) {
					Relation relation = new Relation();
					relation.setDrawMetaByCurAct(actAndDrawMeta.get(actSymbol));
					relation.setDrawMetaByPreAct(actAndDrawMeta.get(preActSymbol));
					relationDAO.save(relation);
				}
			}
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			throw new RuntimeException("读取绘图信息信息失败！", he);
		} finally {
			SessionFactory.closeSession();
		}
	}

	public static void deleteOutdateData(Project project, int model) {
		DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
		DrawNodeDAO nodeDAO = new DrawNodeDAO();
		// 删除该项目的虚工序、节点信息
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			drawMetaDAO.deleteByProject(project, model);
			nodeDAO.deleteByProject(project, model);
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx)
				tx.rollback();
			throw he;
		} finally {
			SessionFactory.closeSession();
		}
	}

}
