package com.ziscloud.zcdiagram.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.core.IModelChangeProvider;
import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.core.ModelChangedEvent;
import com.ziscloud.zcdiagram.dao.ActivitiyDAO;
import com.ziscloud.zcdiagram.dao.ProjectDAO;
import com.ziscloud.zcdiagram.dao.SessionFactory;
import com.ziscloud.zcdiagram.dialog.PrePickerDialog;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.util.ImageUtil;

public class DeleteActivity extends Action implements
		ISelectionChangedListener, IModelChangeProvider {
	private Project project;
	private Activity delAct;
	private Set<Activity> suffix;
	private TableViewer viewer;
	private Shell shell;
	private List<IModelChangedListener> listeners = new ArrayList<IModelChangedListener>();

	public DeleteActivity(Shell shell, IModelChangedListener[] listeners) {
		super();
		setText("删除工序");
		setImageDescriptor(ImageUtil.DEL_ACT);
		suffix = new HashSet<Activity>();
		this.shell = shell;
		this.setEnabled(false);
		if (null != listeners) {
			for (IModelChangedListener listener : listeners) {
				this.addModelChangedListener(listener);
			}
		}
	}

	@Override
	public void run() {
		if (null != delAct
				&& MessageDialog.openConfirm(shell, "删除工序",
						"工程项目工序删除后，将不可恢复，是否继续？")) {
			suffixHandle();
			saveToDatabase();
			fireModelObjectChanged(null, delAct, delAct);
		}
	}

	private void suffixHandle() {
		ActivitiyDAO activitiyDAO = new ActivitiyDAO();
		List<Activity> aList = activitiyDAO.findByProject(project);
		String preStr = null;
		String[] preArr = null;
		for (Activity act : aList) {
			preStr = act.getPreActivity();
			if (null == preStr) {
				continue;
			} else {
				preArr = preStr.split(PrePickerDialog.PRE_TOKEN);
				for (int j = 0; j < preArr.length; j++) {
					String pre = preArr[j];
					if (pre.equals(delAct.getSymbol())) {
						// get pre of the deleting activity
						String[] delPreArr = delAct.getPreActivity().split(
								PrePickerDialog.PRE_TOKEN);
						Set<String> newPreSet = null;
						for (String delPre : delPreArr) {
							// if the pre of suffix not contains the deleting
							// pre
							// add pre to the suffix
							if (!ArrayUtils.contains(preArr, delPre)) {
								if (null == newPreSet) {
									newPreSet = new HashSet<String>();
								}
								newPreSet.add(delPre);
							}
						}
						// delete the pre
						preArr[j] = setToPreStr(newPreSet);
						// get the new pre
						String newPre = arrayToPreStr(preArr);
						// System.out.println(act.getSymbol() + ":" + newPre);
						// update with the new pre
						act.setPreActivity(newPre);
						suffix.add(act);
					}
				}
			}
		}
	}

	private void saveToDatabase() {
		viewer.remove(delAct);
		viewer.refresh(delAct);
		ActivitiyDAO activitiyDAO = new ActivitiyDAO();
		ProjectDAO projectDAO = new ProjectDAO();
		Transaction tx = null;
		tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			for (Activity activity : suffix) {
				activitiyDAO.merge(activity);
				viewer.refresh(activity);
			}
			activitiyDAO.delete(delAct);
			project.setModifyTime(new Date().getTime());
			projectDAO.merge(project);
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			throw new RuntimeException("保存删除工序后的信息失败！", he);
		} finally {
			SessionFactory.closeSession();
		}
	}

	private String arrayToPreStr(String[] array) {
		if (null == array) {
			return null;
		}
		StrBuilder sb = new StrBuilder();
		for (String str : array) {
			if (!StringUtils.isBlank(str)) {
				sb.append(str).append(PrePickerDialog.PRE_TOKEN);
			}
		}
		if (sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		return sb.toString();
	}

	private String setToPreStr(Set<String> set) {
		if (null == set) {
			return null;
		}
		StrBuilder sb = new StrBuilder();
		for (String str : set) {
			if (!StringUtils.isBlank(str)) {
				sb.append(str).append(PrePickerDialog.PRE_TOKEN);
			}
		}
		if (sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		return sb.toString();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			Object object = ss.getFirstElement();
			if (object instanceof Activity) {
				this.setEnabled(true);
				this.delAct = new ActivitiyDAO().findById(((Activity) object)
						.getId());
				this.project = delAct.getProject();
				this.viewer = (TableViewer) event.getSource();
			} else {
				this.setEnabled(false);
			}
		}
	}

	@Override
	public void addModelChangedListener(IModelChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void fireModelChanged(IModelChangedEvent event) {

	}

	@Override
	public void fireModelObjectChanged(Object object, Object oldValue,
			Object newValue) {
		for (IModelChangedListener listener : listeners) {
			listener.modelChanged(new ModelChangedEvent(this,
					IModelChangedEvent.REMOVE, oldValue, newValue));
		}
	}

	@Override
	public void removeModelChangedListener(IModelChangedListener listener) {
		listeners.remove(listener);
	}

}
