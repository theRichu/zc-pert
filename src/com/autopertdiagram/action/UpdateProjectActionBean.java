package com.autopertdiagram.action;

import com.autopertdiagram.pojo.Project;
import com.autopertdiagram.dao.ProjectDAO;
import com.autopertdiagram.dao.HibernateSessionFactory;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.exception.StripesServletException;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.Calendar;
import java.util.Locale;

/**
 * Author: shunyunwang
 * Date: 2009-4-22
 * Time: 11:11:12
 */
@UrlBinding("/project/UpdateProject")
public class UpdateProjectActionBean extends DefaultActionBean {
    @ValidateNestedProperties({
            @Validate(field = "name", on = "save", trim = true, required = true, minlength = 1, maxlength = 255),
            @Validate(field = "planPeriod", on = "save", trim = true, required = true, minvalue = 1, maxvalue = Integer.MAX_VALUE),
            @Validate(field = "planCost", on = "save", trim = true, required = true, minvalue = 0.0, maxvalue = Double.MAX_VALUE),
            @Validate(field = "planStartDate", on = "save", trim = true, required = true, minlength = 1, maxlength = 10),
            @Validate(field = "contractor", on = "save", trim = true, required = true, minlength = 1, maxlength = 255)
    })
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Resolution save() throws StripesServletException {
        ProjectDAO projectDAO = new ProjectDAO();
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            //通过计划工期和计划开工时间来计算计划完工时间
            Calendar planStartCalendar = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
            planStartCalendar.setTime(project.getPlanStartDate());
            planStartCalendar.add(Calendar.DAY_OF_MONTH, project.getPlanPeriod());
            project.setPlanEndDate(planStartCalendar.getTime());
            projectDAO.update(project);
            tx.commit();
        }catch (HibernateException he) {
            if (null != tx) {
                tx.rollback();
            }
            throw new StripesServletException("更新工程项目信息失败", he);
        }finally {
            HibernateSessionFactory.closeSession();
        }
        return new StreamingResolution("text", "修改工程项目信息成功！");
    }
}
