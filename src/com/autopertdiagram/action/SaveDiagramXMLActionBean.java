package com.autopertdiagram.action;

import com.autopertdiagram.pojo.Node;
import com.autopertdiagram.pojo.Project;
import com.autopertdiagram.pojo.Process;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

/**
 * User: shunyunwang
 * Date: 2009-3-24
 * Time: 20:25:08
 */
@UrlBinding("/diagram/SaveDiagramXML")
public class SaveDiagramXMLActionBean extends DefaultActionBean {
    private Project project;
    private String xml;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public Resolution saveXML() {
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            List<Element> cells = root.element("root").elements("mxCell");
            for (Element cell : cells) {
                if (null != cell.attribute("vertex")) {
                    //如果vertex属性不为空，那么说明是节点元素
                    fetchNode(cell);
                } else if (null != cell.attribute("edge")) {
                    //如果edge属性不为空，那么说明是边元素，也就是工序
                    fetchProcess(cell);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new StreamingResolution("msg", new StringReader("Success!"));
    }

    private void fetchNode(Element cell) {
        Node node = new Node();
        //设置节点的label
        node.setLabel(Integer.parseInt(cell.attributeValue("value")));
        //获取节点中，包含节点坐标信息的xml节点
        Element mxGraphic = cell.element("mxGeometry");
        //设置节点的X坐标
        node.setX(Integer.parseInt(mxGraphic.attributeValue("x")));
        //设置节点的Y坐标
        node.setY(Integer.parseInt(mxGraphic.attributeValue("y")));
        //设置节点所属的项目
        node.setProject(project);
    }

    private void fetchProcess(Element cell) {
        Process prcs = new Process();
    }

    private SuccessResolution getSuccessResolution() {
        HashMap<String, String> nextStep = new HashMap();
        nextStep.put("返回首页", "/index.jsp");
        return new SuccessResolution("保存PERT图成功！", nextStep);
    }
}
