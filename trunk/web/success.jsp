<%--
  User: ZisCloud
  Date: 2009-2-20
  Time: 20:05:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
    <s:layout-component name="pageTitle">
        操作成功
    </s:layout-component>
    <s:layout-component name="content">
        <table align="center" cellpadding="4" cellspacing="0" border="0">
            <tr>
                <td>
                    ${param.message}
                </td>
            </tr>
            <tr>
                <td>
                    <%
                        String nextStep = request.getParameter("nextStep");
                        nextStep = nextStep.substring(1,nextStep.length()-1);
                        String[] nextSteps = nextStep.split(",");
                        out.println("您现在可以进行的操作：");
                        out.println("<ol>");
                        for (String step : nextSteps) {
                            String[] map = step.split("=", 2);
                            out.println("<li><a href=\"" + request.getContextPath() +map[1] + "\" target=\"_self\">" + map[0] + "</a></li>");
                        }
                        out.println("</ol>");
                    %>
                </td>
            </tr>
        </table>
    </s:layout-component>
</s:layout-render>