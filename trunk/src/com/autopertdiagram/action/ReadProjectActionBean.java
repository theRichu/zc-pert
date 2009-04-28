package com.autopertdiagram.action;

import com.autopertdiagram.dao.ProjectDAO;
import com.autopertdiagram.pojo.Project;
import com.autopertdiagram.util.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.io.StringReader;
import java.text.DateFormat;
import java.util.List;

/**
 * User: ZisCloud Date: 2009-4-12 Time: 15:54:17
 */
@UrlBinding("/project/readProject")
public class ReadProjectActionBean extends DefaultActionBean {
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Resolution readAllProjects() {
        List<Project> projectList = new ProjectDAO().findAll();
        StringBuilder sb = new StringBuilder("[");
        for (Project p : projectList) {
            sb.append("[");
            sb.append(p.getId());
            sb.append(",");
            sb.append("'" + p.getName() + "'");
            sb.append(",");
            sb.append(p.getPlanPeriod());
            sb.append(",");
            sb.append("'" + DateFormat.getDateInstance().format(p.getPlanStartDate()) + "'");
            sb.append(",");
            sb.append(p.getPlanCost());
            sb.append(",");
            sb.append("'" + p.getContractor() + "'");
            sb.append("],");
        }
        sb.replace(sb.length() - 1, sb.length(), "]");
        return new StreamingResolution("text", new StringReader(sb.toString()));
    }

    public Resolution readAllProject() {
        List<Project> projectList = new ProjectDAO().findAll();
        JSONArray itemList = new JSONArray();
        for (Project prjct : projectList) {
            JSONObject item = new JSONObject();
            item.put("cls", "file");
            item.put("id", prjct.getId());
            item.put("leaf", true);
            item.put("iconCls", "new-process-icon");
            item.put("children", null);
            item.put("text", prjct.getName());
            itemList.add(item);
        }
        System.out.println(itemList);
        return new StreamingResolution("text", itemList.toString());
    }

    public Resolution readProjectById() {
        Project prjct = new ProjectDAO().findById(project.getId());
        if (null == prjct) {
            return new StreamingResolution("text", "error");
        } else {
            JSONObject info = new JSONObject();
            info.put("工程ID(只读)", prjct.getId());
            info.put("项目名称", prjct.getName());
            info.put("工序编号前缀(只读)", prjct.getPreSymbol());
            info.put("计划工期", prjct.getPlanPeriod());
            info.put("实际工期", null == prjct.getActualPeriod() ? "未知": prjct.getActualPeriod());
            info.put("预算资金", prjct.getPlanCost());
            info.put("实际资金", null == prjct.getActualCost() ? "未知": prjct.getActualCost());
            info.put("计划开工时间", Utils.formatDate(prjct.getPlanStartDate()));
            info.put("实际开工时间", Utils.formatDate(prjct.getActualStartDate()));
            info.put("计划完工时间(只读)", Utils.formatDate(prjct.getPlanEndDate()));
            info.put("实际完工时间(只读)", Utils.formatDate(prjct.getActualEndDate()));
            info.put("施工单位", prjct.getContractor());
            return new StreamingResolution("text", info.toString());
        }
    }
}
