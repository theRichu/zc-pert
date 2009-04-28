package com.autopertdiagram.action;

import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * User: ZisCloud
 * Date: 2009-2-19
 * Time: 17:10:13
 */
public class DefaultActionBeanContext extends ActionBeanContext {
    /**
     * Gets the currently logged in user, or null if no-one is logged in.
     */
    public int getUserId() {
        return (Integer) getRequest().getSession().getAttribute("userId");
    }

    /**
     * Sets the currently logged in user.
     */
    public void setUserId(int userId) {
        getRequest().getSession().setAttribute("userId", userId);
    }
}
