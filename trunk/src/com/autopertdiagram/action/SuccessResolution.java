package com.autopertdiagram.action;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.ForwardResolution;

import java.util.HashMap;

/**
 * User: ZisCloud
 * Date: 2009-2-20
 * Time: 20:10:48
 */
public class SuccessResolution {
    private String message;
    private HashMap nextStep;
    public static final String MESSAGE = "message";
    public static final String NEXTSTEP = "nextStep";

    public SuccessResolution(String message, HashMap nextStep) {
        this.message = message;
        this.nextStep = nextStep;
    }

    public Resolution redirect() {
        return new RedirectResolution("/success.jsp").addParameter(MESSAGE,message)
                .addParameter(NEXTSTEP,nextStep);
    }

    public Resolution forward() {
        return new ForwardResolution("/success.jsp").addParameter(MESSAGE,message)
                .addParameter(NEXTSTEP,nextStep);
    }
}
