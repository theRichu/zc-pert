package com.autopertdiagram.action;

import com.autopertdiagram.pojo.Process;
import com.autopertdiagram.dao.ProcessDAO;
import com.autopertdiagram.dao.HibernateSessionFactory;
import com.autopertdiagram.dao.ProjectDAO;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.exception.StripesServletException;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

/**
 * Author: shunyunwang
 * Date: 2009-4-22
 * Time: 13:49:04
 */
@UrlBinding("/process/UpdateProcess")
public class UpadteProcessActionBean extends DefaultActionBean {
    private Process process;
    private int project;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public Resolution save() throws StripesServletException {
        ProcessDAO processDAO = new ProcessDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            //通过计划工期和计划开工时间来计算计划完工时间
            Calendar planStartCalendar = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
            planStartCalendar.setTime(process.getPlanStartDate());
            planStartCalendar.add(Calendar.DAY_OF_MONTH, process.getPlanPeriod());
            process.setPlanEndDate(planStartCalendar.getTime());
            processDAO.update(process);
            projectDAO.updateModifyDate(new Date(), project);
            tx.commit();
        } catch (HibernateException he) {
            if (null != tx)
                tx.rollback();
            throw new StripesServletException("修改工序信息失败", he);
        } finally {
            HibernateSessionFactory.closeSession();
        }
        return new StreamingResolution("text", "修改工序信息成功！");    
    }
}
