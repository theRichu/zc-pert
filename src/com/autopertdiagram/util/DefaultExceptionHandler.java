package com.autopertdiagram.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.config.Configuration;

/**
 *
 * @author ZisCloud
 */
public class DefaultExceptionHandler implements net.sourceforge.stripes.exception.ExceptionHandler {

    public void handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throwable.printStackTrace();
        request.setAttribute("exception", throwable);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    public void init(Configuration configuration) throws Exception {
    }
}
