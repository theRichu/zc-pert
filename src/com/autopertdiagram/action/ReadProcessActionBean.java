package com.autopertdiagram.action;

import com.autopertdiagram.dao.ProcessDAO;
import com.autopertdiagram.pojo.Process;
import com.autopertdiagram.pojo.Project;
import com.autopertdiagram.util.Utils;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

/**
 * User: ZisCloud Date: 2009-4-12 Time: 15:48:52
 */
@UrlBinding("/process/ReadProcess")
public class ReadProcessActionBean extends DefaultActionBean {
    private Project project;
    private Process process;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Resolution readProcessById() {
        Process prcs = new ProcessDAO().findById(process.getId());
        if (null == prcs) {
            return new StreamingResolution("text", "error");
        } else {
            JSONObject info = new JSONObject();
            info.put("工序ID(只读)", prcs.getId());
            info.put("工序编号(只读)", prcs.getSymbol());
            info.put("工序名称", prcs.getName());
            info.put("计划工期", prcs.getPlanPeriod());
            info.put("实际工期", null == prcs.getActualPeriod() ? "未知": prcs.getActualPeriod());
            info.put("预算资金", prcs.getPlanCost());
            info.put("实际资金", null == prcs.getActualCost() ? "未知": prcs.getActualCost());
            info.put("年产出", prcs.getOutput());
            info.put("计划开工时间", Utils.formatDate(prcs.getPlanStartDate()));
            info.put("实际开工时间", Utils.formatDate(prcs.getActualStartDate()));
            info.put("计划完工时间(只读)", Utils.formatDate(prcs.getPlanEndDate()));
            info.put("实际完工时间(只读)", Utils.formatDate(prcs.getActualEndDate()));
            info.put("虚工序(只读)", Utils.convertBoolean(prcs.getIsVirtual()));
            info.put("关键工序(只读)", Utils.convertBoolean(prcs.getIsCriticl()));
            return new StreamingResolution("text", info.toString());
        }
    }

    public Resolution readProcessByProject() {
        List<Process> prcsList = new ProcessDAO().findNotVirtualByproject(project);
        JSONArray items = new JSONArray();
        for (Process prcs : prcsList) {
            JSONArray item = new JSONArray();
            item.add(prcs.getId().toString());
            item.add(prcs.getName());
            items.add(item);
        }
        return new StreamingResolution("text",items.toString());
    }
}
