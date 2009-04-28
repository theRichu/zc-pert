package com.autopertdiagram.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import com.autopertdiagram.util.Utils;

/**
 * User: ZisCloud
 * Date: 2009-2-20
 * Time: 15:18:56
 */
public class DefaultActionBean implements ActionBean, ValidationErrorHandler {
    private DefaultActionBeanContext context;

    public void setContext(ActionBeanContext context) {
        this.context = (DefaultActionBeanContext) context;
    }

    public DefaultActionBeanContext getContext() {
        return context;
    }

     @Override
    public Resolution handleValidationErrors(ValidationErrors validationErrors) throws Exception {
        return new StreamingResolution("text", Utils.JsonResponse(false, "您输入的信息不正确，请检查您的输入！" + validationErrors.toString()));
    }
}
