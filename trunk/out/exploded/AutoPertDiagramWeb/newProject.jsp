<%--
  User: ZisCloud
  Date: 2009-2-20
  Time: 15:06:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
    <s:layout-component name="pageTitle">
        添加工程项目
    </s:layout-component>
    <s:layout-component name="content">
        <table align="center" cellpadding="4" cellspacing="0" border="0">
            <s:form beanclass="com.autopertdiagram.action.NewProjectActionBean" method="post">
                <s:hidden name="sourcePage" value="${pageContext.request.servletPath}"/>
                <tr>
                    <td colspan="3" align="center">添加工程项目</td>
                </tr>
                <tr>
                    <td><s:label for="project.name"/>：</td>
                    <td><s:text name="project.name"/></td>
                    <td><s:errors field="project.name"/></td>
                </tr>
                <tr>
                    <td><s:label for="project.preSymbol"/></td>
                    <td><s:text name="project.preSymbol"/></td>
                    <td><s:errors field="project.preSymbol"/></td>
                </tr>
                <tr>
                    <td><s:label for="project.planPeriod"/>：</td>
                    <td><s:text name="project.planPeriod"/>&nbsp;（日）</td>
                    <td><s:errors field="project.planPeriod"/></td>
                </tr>
                <tr>
                    <td><s:label for="project.planCost"/>：</td>
                    <td><s:text name="project.planCost"/>&nbsp;（万元）</td>
                    <td><s:errors field="project.planCost"/></td>
                </tr>
                <tr>
                    <td><s:label for="project.planStartDate"/>：</td>
                    <td><s:text name="project.planStartDate"/></td>
                    <td><s:errors field="project.planStartDate"/></td>
                </tr>
                <tr>
                    <td><s:label for="project.planEndDate"/>：</td>
                    <td><s:text name="project.planEndDate"/></td>
                    <td><s:errors field="project.planEndDate"/></td>
                </tr>
                <tr>
                    <td><s:label for="project.contractor"/>：</td>
                    <td><s:text name="project.contractor"/></td>
                    <td><s:errors field="project.contractor"/></td>
                </tr>
                <tr>
                    <td colspan="3" align="center"><s:submit name="saveProject" value=" 保存 "/></td>
                </tr>
            </s:form>
        </table>
    </s:layout-component>
</s:layout-render>