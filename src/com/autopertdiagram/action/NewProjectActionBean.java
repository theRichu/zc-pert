package com.autopertdiagram.action;

import com.autopertdiagram.dao.HibernateSessionFactory;
import com.autopertdiagram.dao.ProjectDAO;
import com.autopertdiagram.pojo.Project;
import com.autopertdiagram.util.Utils;
import net.sf.json.JSONObject;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.exception.StripesServletException;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Calendar;
import java.util.Locale;

/**
 * User: ZisCloud Date: 2009-2-20 Time: 15:15:19
 */
@UrlBinding("/project/NewProject")
public class NewProjectActionBean extends DefaultActionBean {
    @ValidateNestedProperties({
            @Validate(field = "name", on = "saveProject", trim = true, required = true, minlength = 1, maxlength = 255),
            @Validate(field = "preSymbol", on = "saveProject", trim = true, required = true, minlength = 1, maxlength = 4, mask = "[a-zA-Z]"),
            @Validate(field = "planPeriod", on = "saveProject", trim = true, required = true, minvalue = 1, maxvalue = Integer.MAX_VALUE),
            @Validate(field = "planCost", on = "saveProject", trim = true, required = true, minvalue = 0.0, maxvalue = Double.MAX_VALUE),
            @Validate(field = "planStartDate", on = "saveProject", trim = true, required = true, minlength = 1, maxlength = 10),
            //@Validate(field = "planEndDate", on = "saveProject", trim = true, required = true, minlength = 1, maxlength = 10),
            @Validate(field = "contractor", on = "saveProject", trim = true, required = true, minlength = 1, maxlength = 255)
    })
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Resolution saveProject() throws StripesServletException {
        ProjectDAO projectDAO = new ProjectDAO();
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            //通过计划工期和计划开工时间来计算计划完工时间
            Calendar planStartCalendar = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
            planStartCalendar.setTime(project.getPlanStartDate());
            planStartCalendar.add(Calendar.DAY_OF_MONTH, project.getPlanPeriod());
            project.setPlanEndDate(planStartCalendar.getTime());
            projectDAO.save(project);
            tx.commit();
        } catch (HibernateException he) {
            if (null != tx)
                tx.rollback();
            he.printStackTrace();
            throw new StripesServletException("保存工程项目信息失败", he);
        } finally {
            HibernateSessionFactory.getSession().close();
        }
        //传统方式的反馈
        //return getSuccessResolution().redirect();
        //ajax方式的反馈
        return new StreamingResolution("text", Utils.JsonResponse(true,"添加工程项目成功！"));
    }

    private SuccessResolution getSuccessResolution() {
        HashMap<String, String> nextStep = new HashMap<String, String>();
        nextStep.put("继续添加工程项目", "/newProject.jsp");
        nextStep.put("打开项目以便添加工序", "/process/NewProcess?project.id=" + project.getId());
        nextStep.put("返回首页", "/index.jsp");
        return new SuccessResolution("sss", nextStep);
    }

}
