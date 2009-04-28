<%--
  User: ZisCloud
  Date: 2009-2-21
  Time: 9:10:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
    <s:layout-component name="pageTitle">
        添加工序
    </s:layout-component>
    <s:layout-component name="html-head">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/theme/ui.all.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.2.6.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.5.3.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/i18n/ui.datepicker-zh-CN.js"></script>
        <script type="text/javascript">
            $(function() {
                $("#planStartDate").datepicker($.datepicker.regional['zh-CN']);
            });
        </script>
    </s:layout-component>
    <s:layout-component name="content">
        <table align="center" border="0" cellpadding="4" cellspacing="0">
            <s:form beanclass="com.autopertdiagram.action.NewProcessActionBean" method="post">
                <tr>
                    <td colspan="3" align="center">添加工序</td>
                </tr>
                <tr>
                    <td><s:label for="process.name"/>:</td>
                    <td><s:text name="process.name"/></td>
                    <td><s:errors field="process.name"/></td>
                </tr>
                <tr>
                    <td>紧前工序：</td>
                    <td>
                        <s:select name="preProcess" multiple="true" size="8">
                            <s:options-map map="${actionBean.processMap}"/>
                        </s:select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td><s:label for="process.planPeriod"/>:</td>
                    <td><s:text name="process.planPeriod"/></td>
                    <td><s:errors field="process.planPeriod"/></td>
                </tr>
                <tr>
                    <td><s:label for="process.planStartDate"/>:</td>
                    <td><s:text id="planStartDate" name="process.planStartDate"/></td>
                    <td><s:errors field="process.planStartDate"/></td>
                </tr>
                <tr>
                    <td><s:label for="process.planCost"/>:</td>
                    <td><s:text name="process.planCost"/></td>
                    <td><s:errors field="process.planCost"/></td>
                </tr>
                <tr>
                    <td><s:label for="process.output"/>:</td>
                    <td><s:text name="process.output"/></td>
                    <td><s:errors field="process.output"/></td>
                </tr>
                <tr>
                    <td colspan="3" align="center"><s:submit name="saveProcess" value="保存"/></td>
                </tr>
            </s:form>
            <s:form beanclass="com.autopertdiagram.action.NewProcessActionBean" method="post"
                    enctype="multipart/form-data">
                <tr>
                    <td colspan="3">
                        <hr>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><s:errors field="excel"/></td>
                </tr>
                <tr>
                    <td>Excel:</td>
                    <td><s:file name="excel"/></td>
                    <td><s:submit name="importProcess" value="导入数据"/></td>
                </tr>
            </s:form>
        </table>
    </s:layout-component>
</s:layout-render>