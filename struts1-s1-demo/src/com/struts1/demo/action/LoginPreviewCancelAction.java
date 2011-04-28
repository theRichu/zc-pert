package com.struts1.demo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.struts1.demo.formbean.LoginForm;
import com.struts1.demo.valueobject.LoginValue;

public class LoginPreviewCancelAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		if (null != forward) {
			return forward;
		}

		request.getSession().removeAttribute("login_value");

		LoginValue value = new LoginValue();

		((LoginForm) form).toValueObject(value);

		request.getSession().setAttribute("login_value", value);

		return mapping.findForward("success");
	}

}
