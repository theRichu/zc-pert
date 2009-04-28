package com.autopertdiagram.action;

import com.autopertdiagram.dao.HibernateSessionFactory;
import com.autopertdiagram.dao.PrerelationDAO;
import com.autopertdiagram.dao.ProcessDAO;
import com.autopertdiagram.dao.ProjectDAO;
import com.autopertdiagram.pojo.Prerelation;
import com.autopertdiagram.pojo.Process;
import com.autopertdiagram.pojo.Project;
import com.autopertdiagram.util.Utils;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.exception.StripesServletException;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: ZisCloud
 * Date: 2009-2-21
 * Time: 9:17:35
 */

@UrlBinding("/process/NewProcess")
//@Wizard(startEvents = "start")
public class NewProcessActionBean extends DefaultActionBean {
    private Project project;
    //private List<Integer> preProcess = new ArrayList<Integer>();
    private String[] preProcess;
    private String preProcessStr;
    @ValidateNestedProperties({
            @Validate(field = "name", on = "saveProcess", trim = true, required = true, minlength = 1, maxlength = 255),
            @Validate(field = "planPeriod", on = "saveProcess", trim = true, required = true, minvalue = 0, maxvalue = Integer.MAX_VALUE),
            @Validate(field = "planStartDate", on = "saveProcess", trim = true, required = true),
            @Validate(field = "planCost", on = "saveProcess", trim = true, required = true, minvalue = 0.0, maxvalue = Double.MAX_VALUE),
            @Validate(field = "output", on = "saveProcess", trim = true, required = true, minvalue = 0.0, maxvalue = Double.MAX_VALUE)
    })
    private Process process;
    private Map<Integer, String> processMap = new HashMap<Integer, String>();
    private FileBean excel;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Map<Integer, String> getProcessMap() {
        return processMap;
    }

    private void setProcessMap(Map<Integer, String> processMap) {
        this.processMap = processMap;
    }

    public FileBean getExcel() {
        return excel;
    }

    public void setExcel(FileBean excel) {
        this.excel = excel;
    }

    public String getPreProcessStr() {
        return preProcessStr;
    }

    public void setPreProcessStr(String preProcessStr) {
        this.preProcessStr = preProcessStr;
    }

    @DefaultHandler
    public Resolution start() throws StripesServletException {
        ProjectDAO projectDAO = new ProjectDAO();
        project = projectDAO.findById(project.getId());
        if (null == project) {
            throw new StripesServletException("请求参数错误!");
        } else {
            ProcessDAO processDAO = new ProcessDAO();
            List<Process> processes = processDAO.findByProject(project);
            for (Process prcs : processes) {
                processMap.put(prcs.getId(), prcs.getSymbol() + ":" + prcs.getName());
            }
            return new ForwardResolution("/newProcess.jsp");
        }
    }

    public Resolution saveProcess() throws StripesServletException {
        if (null != preProcessStr) {
            preProcess = preProcessStr.split(",");
        } else {
            preProcess = new String[]{};    
        }
        ProcessDAO processDAO = new ProcessDAO();
        PrerelationDAO prerelationDAO = new PrerelationDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            project = projectDAO.findById(project.getId());
            //设置工序所属的工程项目
            process.setProject(project);
            //通过计划工期和计划开工时间来计算计划完工时间
            Calendar planStartCalendar = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
            planStartCalendar.setTime(process.getPlanStartDate());
            planStartCalendar.add(Calendar.DAY_OF_MONTH, process.getPlanPeriod());
            process.setPlanEndDate(planStartCalendar.getTime());
            process.setIsVirtual("false");
            //保存工序
            processDAO.save(process);
            process.setSymbol(project.getPreSymbol() + process.getId());
            processDAO.attachDirty(process);
            if (0 != preProcess.length) {
                //找出紧前工序序列中最早开始的工序
                Process[] processes = new Process[preProcess.length];
                Process first = null;
                for (int i = 0; i < preProcess.length; i++) {
                    Process prcs = processDAO.findById(Integer.parseInt(preProcess[i]));
                    if (null == prcs) {
                        throw new StripesServletException("紧前工序参数错误！");
                    } else {
                        //检查新工序的开始时间是否是在其紧前工序的开始时间之后
                        if (prcs.getPlanStartDate().after(process.getPlanStartDate())) {
                            throw new StripesServletException("新工序的计划开始时间不得早于其紧前工序："
                                    + prcs.getSymbol() + ":" + prcs.getName());
                        }
                        if (0 == i) {
                            first = prcs;
                        }
                        if (prcs.getPlanStartDate().before(first.getPlanStartDate())) {
                            first = prcs;
                        }
                        processes[i] = prcs;
                    }
                }
                //保存紧前工序序列
                for (Process prcs : processes) {
                    //根据各个紧前工序的开始时间，对紧前工序序列进行排序，排序的标识=其他紧前工序的开始时间-最早开始的紧前工序的开始时间
                    Prerelation prerelation = new Prerelation(process, prcs,
                            (int) ((prcs.getPlanStartDate().getTime() - first.getPlanStartDate().getTime()) / 86400000));
                    prerelationDAO.save(prerelation);
                }
            }
            tx.commit();
        } catch (HibernateException he) {
            if (null != tx)
                tx.rollback();
            he.printStackTrace();
            throw new StripesServletException("保存工序信息时发生错误，请稍候重试！", he);
        } finally {
            HibernateSessionFactory.closeSession();
        }
        //return getSuccessResolution().redirect();
        return new StreamingResolution("text", Utils.JsonResponse(true,"创建工序成功！"));
    }

    public Resolution importProcess() throws IOException, StripesServletException {
        if (null != excel) {
            ProjectDAO projectDAO = new ProjectDAO();
            PrerelationDAO prerelationDAO = new PrerelationDAO();
            ProcessDAO processDAO = new ProcessDAO();
            project = projectDAO.findById(project.getId());
            Transaction tx = null;
            Map<String, Process> prcsMap = new HashMap<String, Process>();
            HSSFWorkbook workbook = new HSSFWorkbook(excel.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);
            try {
                tx = HibernateSessionFactory.getSession().beginTransaction();
                for (Iterator rit = sheet.rowIterator(); rit.hasNext();) {
                    HSSFRow row = (HSSFRow) rit.next();
                    Process prcs = new Process();
                    //读取ID
                    if (null == row.getCell(0)) {
                        throw new StripesServletException("Excel文件中“ID”数据不正确，请检查后重新导入！ 行号：" + row.getRowNum());
                    } else {
                        if (row.getCell(0).toString().equals("ID")) {
                            continue;
                        }
                        prcs.setId((int) row.getCell(0).getNumericCellValue());
                    }
                    //读取工序名称
                    if (null == row.getCell(1)) {
                        throw new StripesServletException("Excel文件中“工序名称”数据不正确，请检查后重新导入！ 行号：" + row.getRowNum());
                    } else {
                        prcs.setName(row.getCell(1).toString());
                    }
                    //读取计划工期
                    if (null == row.getCell(3)) {
                        throw new StripesServletException("Excel文件中“计划工期”数据不正确，请检查后重新导入！ 行号：" + row.getRowNum());
                    } else {
                        prcs.setPlanPeriod((int) row.getCell(3).getNumericCellValue());
                    }
                    //读取计划开始时间，计算计划完工时间
                    if (null == row.getCell(4)) {
                        throw new StripesServletException("Excel文件中“计划开工时间”数据不正确，请检查后重新导入！ 行号：" + row.getRowNum());
                    } else {
                        prcs.setPlanStartDate(row.getCell(4).getDateCellValue());
                        Calendar startDate = Calendar.getInstance();
                        startDate.setTime(prcs.getPlanStartDate());
                        startDate.add(Calendar.DAY_OF_MONTH, prcs.getPlanPeriod());
                        prcs.setPlanEndDate(startDate.getTime());
                    }
                    //读取工序预算
                    if (null == row.getCell(5)) {
                        throw new StripesServletException("Excel文件中“预算”数据不正确，请检查后重新导入！ 行号：" + row.getRowNum());
                    } else {
                        prcs.setPlanCost(row.getCell(5).getNumericCellValue());
                    }
                    //读取工序产出
                    if (null == row.getCell(6)) {
                        throw new StripesServletException("Excel文件中“产出”数据不正确，请检查后重新导入！ 行号：" + (row.getRowNum() + 1));
                    } else {
                        prcs.setOutput(row.getCell(6).getNumericCellValue());
                    }
                    //保存工序，紧前工序随后再进行处理
                    prcs.setProject(project);
                    prcs.setIsVirtual("false");
                    prcs.setIsCriticl("false");
                    processDAO.save(prcs);
                    prcs.setSymbol(project.getPreSymbol() + prcs.getId());
                    processDAO.attachDirty(prcs);
                    //添加工序到map中，以便处理紧前时进行查询
                    prcsMap.put(row.getCell(0).toString().replaceAll("\\.0",""), prcs);
                }

                for (Iterator rit = sheet.rowIterator(); rit.hasNext();) {
                    HSSFRow row = (HSSFRow) rit.next();
                    Process prcs = null;
                    //读取ID
                    if (null == row.getCell(0)) {
                        throw new StripesServletException("Excel文件中“ID”数据不正确，请检查后重新导入！ 行号：" + row.getRowNum());
                    } else {
                        if (row.getCell(0).toString().equals("ID")) {
                            continue;
                        }
                        prcs = prcsMap.get(row.getCell(0).toString().replaceFirst("\\.0",""));
                    }
                    //读取计划开始时间，计算计划完工时间
                    if (null == row.getCell(4)) {
                        throw new StripesServletException("Excel文件中“计划开工时间”数据不正确，请检查后重新导入！ 行号：" + row.getRowNum());
                    } else {
                        prcs.setPlanStartDate(row.getCell(4).getDateCellValue());
                    }
                    //处理工序的紧前工序系列
                    if (null != row.getCell(2) && row.getCell(2).toString().trim().length() != 0) {
                        String prePrcsStr = row.getCell(2).toString();
                        prePrcsStr = prePrcsStr.replaceFirst("\\.0", "");
                        //验证紧前工序字符串格式是否正确 (\d(,\d)*?)
                        Pattern pattern = Pattern.compile("(\\d*?(,\\d*?)*?)");
                        Matcher matcher = pattern.matcher(prePrcsStr);
                        if (matcher.matches()) {
                            String[] prePrcsStrArr = prePrcsStr.split(",");
                            //验证紧前工序的开始时间是否都在当前工序之前
                            Process[] prePrcs = new Process[prePrcsStrArr.length];
                            Date earliest = null;
                            for (int i = 0; i < prePrcsStrArr.length; i++) {
                                String str = prePrcsStrArr[i];
                                Process tempPrcs = (Process) prcsMap.get(str);
                                if (prcs.getPlanStartDate().before(tempPrcs.getPlanStartDate())) {
                                    throw new StripesServletException("Excel文件中“紧前工序”数据不正确，当前工序的计划开工时间("+prcs.getPlanStartDate()+")早于紧前工序的" +
                                            "计划开工时间("+tempPrcs.getPlanStartDate()+")，请检查后重新导入！ 行号：" + (row.getRowNum() + 1));
                                } else {
                                    prePrcs[i] = tempPrcs;
                                    if (null == earliest || tempPrcs.getPlanStartDate().before(earliest)) {
                                        earliest = tempPrcs.getPlanStartDate();
                                    }
                                }
                            }
                            //构建工序与其紧前工序之间的工序，同时计算紧前工序之间的排序标志
                            for (Process p : prePrcs) {
                                Prerelation pre = new Prerelation();
                                pre.setProcessByProcess(prcs);
                                pre.setProcessByPreProcess(p);
                                pre.setOrdering((int) ((p.getPlanStartDate().getTime() - earliest.getTime()) / 86400000));
                                prerelationDAO.save(pre);
                            }
                        } else {
                            throw new StripesServletException("Excel文件中“紧前工序”数据(" + prePrcsStr + ")“格式”不正确，请检查后重新导入！ 行号：" + (row.getRowNum() + 1));
                        }
                    }
                }
                tx.commit();
            } catch (HibernateException he) {
                throw new StripesServletException("导入工序信息到数据库时发生错误，请联系系统管理员！", he);
            } finally {
                HibernateSessionFactory.closeSession();
            }
        }
        //return getSuccessResolution().redirect();
        return new StreamingResolution("text",Utils.JsonResponse(true,"导入数据成功！")); 
    }

    private SuccessResolution getSuccessResolution() {
        HashMap<String, String> nextStep = new HashMap();
        nextStep.put("继续添加工序", "/process/NewProcess?project.id=" + project.getId());
        nextStep.put("返回首页", "/index.jsp");
        return new SuccessResolution("添加工序成功！", nextStep);
    }
}
