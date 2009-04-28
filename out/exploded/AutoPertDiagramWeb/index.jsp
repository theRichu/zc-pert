<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: ZisCloud
  Date: 2009-2-19
  Time: 13:28:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
Place your content here
<p>
<ul>
    <li><a href="editors/grapheditor.html">grapheditor</a></li>
    <li><a href="newProject.jsp">添加工程项目</a></li>
    <li>
        <s:link beanclass="com.autopertdiagram.action.DrawPertDiagramActionBean" event="startDraw">
            <s:param name="project.id" value="1"/>
        画图
        </s:link>
    </li>
    <li><a href="NewProject.action?allProjects=">x</a></li>
    <li>
        <s:link beanclass="com.autopertdiagram.action.NewProjectActionBean" event="allProjects">
        全部工程
        </s:link>
    </li>
    <%
        out.print(new Date().getTime());
    %>
</ul>
</p>
</body>
</html>