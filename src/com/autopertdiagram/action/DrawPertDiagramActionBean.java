package com.autopertdiagram.action;

import com.autopertdiagram.dao.HibernateSessionFactory;
import com.autopertdiagram.dao.NodeDAO;
import com.autopertdiagram.dao.ProcessDAO;
import com.autopertdiagram.dao.ProjectDAO;
import com.autopertdiagram.diagram.LayNode;
import com.autopertdiagram.diagram.LayNodeForNoTimeScale;
import com.autopertdiagram.diagram.NumberNode;
import com.autopertdiagram.diagram.NumberNodeForTimeScale;
import com.autopertdiagram.pojo.Node;
import com.autopertdiagram.pojo.Process;
import com.autopertdiagram.pojo.Project;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.exception.StripesServletException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;

/**
 * User: ZisCloud
 * Date: 2009-2-28
 * Time: 19:42:14
 */

@UrlBinding("/diagram/Platform")
public class DrawPertDiagramActionBean extends DefaultActionBean {
    private Project project;
    private static Logger logger = Logger.getLogger(DrawPertDiagramActionBean.class);

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Resolution XmlPath() {
        ProjectDAO projectDAO = new ProjectDAO();
        Project curPrjct = projectDAO.findById(project.getId());
        if (null == curPrjct) {
            logger.info("Request to get the PERT diagram for project:" + project.getId()
                    + "But this project can not be found in the database.");
            return new StreamingResolution("text", new StringReader("invalidParam"));
        } else {
            //如果工程项目没有生成过网络图，或者工程项目最后一次修改之后，还没有生成最新的网络图
            if (0 == curPrjct.getXmlGenerateDate() || (curPrjct.getXmlGenerateDate() - curPrjct.getLastModifyDate()) < 0) {
                Transaction tx = null;
                try {
                    curPrjct.setXmlPath(genXmlFile(curPrjct));
                    curPrjct.setXmlGenerateDate(new Date().getTime());
                } catch (DocumentException e) {
                    logger.error("generate xml for project:" + curPrjct.getId(), e);
                    return new StreamingResolution("text", new StringReader("error"));
                } catch (IOException e) {
                    logger.error("generate xml for project:" + curPrjct.getId(), e);
                    return new StreamingResolution("text", new StringReader("error"));
                } catch (StripesServletException e) {
                    logger.error("generate xml for project:" + curPrjct.getId(), e);
                    return new StreamingResolution("text", new StringReader("error"));
                } catch (HibernateException he) {
                    logger.error("delete the node and virtual process of project:" + curPrjct.getId() + " from DB error.", he);
                    return new StreamingResolution("text", new StringReader("error"));
                }
                try {
                    //更新工程的相关信息
                    tx = HibernateSessionFactory.getSession().beginTransaction();
                    projectDAO.attachDirty(curPrjct);
                    tx.commit();
                } catch (HibernateException he) {
                    if (null != tx)
                        tx.rollback();
                    logger.error("save the xml generation result for project:" + curPrjct.getId() + "error", he);
                    return new StreamingResolution("text", new StringReader("error"));
                } finally {
                    HibernateSessionFactory.closeSession();
                }
            }
            return new StreamingResolution("text", new StringReader(curPrjct.getXmlPath()));
        }
    }

    private String genXmlFile(Project currentProject) throws HibernateException, DocumentException, IOException, StripesServletException {
        ProcessDAO processDAO = new ProcessDAO();
        NodeDAO nodeDAO = new NodeDAO();
        //删除该项目的虚工序、节点信息
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            processDAO.deleteVirtualByProject(currentProject);
            nodeDAO.deleteByProject(currentProject);
            tx.commit();
        } catch (HibernateException he) {
            if (null != tx)
                tx.rollback();
            throw he;
        } finally {
            HibernateSessionFactory.closeSession();
        }
        //开始编号
        NumberNode numberNode = new NumberNodeForTimeScale(project);
        numberNode.number();
        //开始布点
        LayNode layNode = new LayNodeForNoTimeScale(project);
        layNode.layNode();
        List<Process> processes = processDAO.findByProject(project);
        List<Node> nodes = nodeDAO.findByProject(project);
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<mxGraphModel>\n").append("<root>\n").append("<mxCell id=\"0\"/>\n")
                .append("<mxCell id=\"1\" parent=\"0\"/>");
        //生成节点的xml
        for (Node n : nodes) {
            xmlBuilder.append(genXmlForNode(n));
        }
        //生成工序的xml
        for (Process prcs : processes) {
            xmlBuilder.append(genXmlForProcess(prcs));
        }
        xmlBuilder.append("</root>").append("</mxGraphModel>");
        //保存xml到文件
        Document document = DocumentHelper.parseText(xmlBuilder.toString());
        String fileName = "project" + new Date().getTime() + ".xml";
        //String fileName= "test.xml";
        String path = getContext().getServletContext().getRealPath("") + "/xml/";
        File file = new File(path + fileName);
        //System.out.println("xml文件：" + file.exists());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        outputFormat.setEncoding("UTF-8");
        XMLWriter xmlWriter = new XMLWriter(fileOutputStream, outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
        //返回该项目的网络图的xml文件的地址
        return "/xml/" + fileName;
    }

    private String genXmlForNode(Node n) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<mxCell id=\"n").append(n.getId()).append("\" value=\"").append(n.getLabel())
                .append("\" style=\"ellipse;strokeColor=#CDEB8B;fillColor=#CDEB8B;shadow=1;fontStyle=1;fontSize=14\" parent=\"1\" vertex=\"1\">\n")
                .append("<mxGeometry x=\"").append(n.getX() * 100)
                .append("\" y=\"").append(n.getY() * 100).append("\" width=\"30\" height=\"30\" as=\"geometry\"/>\n")
                .append("</mxCell>");
        return xmlBuilder.toString();
    }

    private String genXmlForProcess(Process p) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<mxCell id=\"p").append(p.getId()).append("\" value=\"").append(p.getSymbol()).append(":")
                .append(p.getName()).append("\" style=\"straight;dashed=").append(p.getIsVirtual().equals("true") ? 1 : 0)
                .append(";strokeColor=#").append(p.getIsCriticl().equals("true") ? "FF0000" : "000000")
                .append("\" parent=\"1\" source=\"n").append(p.getNodeBySourceNode().getId()).append("\" target=\"n")
                .append(p.getNodeByTargetNode().getId()).append("\" edge=\"1\">\n")
                .append("<mxGeometry width=\"100\" height=\"100\" as=\"geometry\">\n")
                .append("<mxPoint x=\"100\" as=\"sourcePoint\"/>\n")
                .append("<mxPoint y=\"100\" as=\"targetPoint\"/>\n")
                .append("</mxGeometry>\n").append("</mxCell>");
        return xmlBuilder.toString();
    }

    public Resolution startDraw() throws StripesServletException {
        NumberNode numberNode = new NumberNodeForTimeScale(project);
        numberNode.number();
        LayNode layNode = new LayNodeForNoTimeScale(project);
        layNode.layNode();
        return new RedirectResolution("/editors/grapheditor.html");
    }
}
