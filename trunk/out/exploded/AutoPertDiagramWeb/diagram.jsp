<%--
  User: ZisCloud
  Date: 2009-3-1
  Time: 9:20:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/default.jsp">
    <s:layout-component name="pageTitle">
        title of this page
    </s:layout-component>
    <s:layout-component name="html-head">
        <!--css-->
        <link rel="stylesheet" type="text/css" href="css/grapheditor.css"/>
        <!--javascript-->
        <script type="text/javascript" src="editors/js/ext-base.js"></script>
        <script type="text/javascript" src="editors/js/ext-all.js"></script>
        <script type="text/javascript" src="editors/js/mxClient.js"></script>
        <script type="text/javascript" src="js/GraphEditor.js"></script>
        <script type="text/javascript" src="js/MainPanel.js"></script>
        <script type="text/javascript" src="js/LibraryPanel.js"></script>
        <script type="text/javascript">
            // Replaces the built-in alert dialog with ExtJs message dialog (can't
            // replace confirm and prompt as they are callback-based and we require
            // a synchronous call with a return value).
            // Ext.MessageBox.prompt('Prompt', message, null, null, null, defaultValue);
            // Ext.MessageBox.confirm('Confirm', message);
            mxUtils.alert = function(message)
            {
                Ext.example.msg(message, '', '');
            };
        </script>

    </s:layout-component>
    <s:layout-component name="content">
        <div id="header"><div style="float:right;margin:5px;" class="x-small-editor"></div></div>
    </s:layout-component>
</s:layout-render>