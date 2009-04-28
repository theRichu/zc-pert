<%@ page import="net.sourceforge.stripes.exception.StripesServletException" %>
<%--
    Document   : error.jsp
    Created on : 2009-2-5, 21:20:42
    Author     : ZisCloud
--%>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<s:layout-render name="/layout/default.jsp">
    <s:layout-component name="pageTitle">
        有错误发生
    </s:layout-component>
    <s:layout-component name="content">
        <table align="center" cellpadding="4">
            <tr>
                <td align="right">
                    <img src="${pageContext.request.contextPath}/images/error.gif" alt="error" />
                </td>
                <td height="200" align="left">
                    <p>
                        ${requestScope.exception.message}
                    </p>
                    <p>
                        <a href="#" target="_self" onclick="history.go(-1)">click on here to back.</a>
                    </p>
                </td>
            </tr>
        </table>
    </s:layout-component>
</s:layout-render>
