package com.autopertdiagram.security;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.security.controller.StripesSecurityManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: ZisCloud
 * Date: 2009-2-19
 * Time: 17:07:43
 */
public class SecurityManager implements StripesSecurityManager {

    public boolean isUserInRole(List<String> strings, ActionBeanContext actionBeanContext) {
        return false;
    }

    public boolean isUserInRole(List<String> strings, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return false;
    }
}
