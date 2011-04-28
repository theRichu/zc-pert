<%--
    Licensed to the Apache Software Foundation (ASF) under one or more
    contributor license agreements.  See the NOTICE file distributed with
    this work for additional information regarding copyright ownership.
    The ASF licenses this file to You under the Apache License, Version 2.0
    (the "License"); you may not use this file except in compliance with
    the License.  You may obtain a copy of the License at
   
         http://www.apache.org/licenses/LICENSE-2.0
   
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
--%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="l"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="h"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="b"%>
<script type="text/javascript">
function submitCancel() {
	var action = document.forms[0].action;
	document.forms[0].action = "<%=request.getContextPath()%>/preview/cancel.do";
	document.forms[0].submit();
	document.forms[0].action = action;
}

function submitEdit() {
	var action = document.forms[0].action;
	document.forms[0].action = "<%=request.getContextPath()%>/preview/edit.do";
	document.forms[0].submit();
	document.forms[0].action = action;
}
</script>
<h1>Preview Screen</h1>
<b:define id="loginValue" name="login_value" scope="session" type="com.struts1.demo.valueobject.LoginValue" />
<h:form action="/submit.do">
	<p>
		username:<br />
		<%--
		<h:text property="username"></h:text>
		 --%>
		 <b:write name="loginValue" property="username"/>
		 <%-- 
		 <h:hidden property="username"/>
		 --%>
	</p>
	<p>
		password:<br />
		<%-- 
		<h:password property="password"></h:password>
		--%>
		<b:write name="loginValue" property="password"/>
		<%-- 
		<h:hidden property="password"/>
		--%>
	</p>
	<p>
		<h:button property="cancelBtn" onclick="submitCancel()">cancel</h:button><h:button property="editBtn" onclick="submitEdit()">edit</h:button><h:submit>submit</h:submit>
	</p>
</h:form>

<%--

Redirect default requests to Welcome global ActionForward.
By using a redirect, the user-agent will change address to match the path of our Welcome ActionForward. 

--%>