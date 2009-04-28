package com.autopertdiagram.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

/**
 * User: ZisCloud
 * Date: 2009-2-19
 * Time: 17:02:38
 */
public class Log4jInit extends HttpServlet {
    static Logger logger = Logger.getLogger(Log4jInit.class);

    public Log4jInit() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        String prefix = config.getServletContext().getRealPath("/");
        String file = config.getInitParameter("log4j");
        String filePath = prefix + file;
        Properties props = new Properties();
        try {
            // 读取log4j配置文件
            FileInputStream istream = new FileInputStream(filePath);
            props.load(istream);
            istream.close();
            // 设置相对路径下的文件夹
            prefix += "logs" + File.separator;
            // 从配置文件中读取将用来保存日志的文件的文件名
            String logFile = prefix
                    + props.getProperty("log4j.appender.R.File");
            props.setProperty("log4j.appender.R.File", logFile);
            // 装入log4j配置信息
            PropertyConfigurator.configure(props);
        } catch (IOException e) {
            toPrint("Could not read configuration file [" + filePath + "].");
            toPrint("Ignoring configuration file [" + filePath + "].");
            return;
        }
    }

    public static void toPrint(String content) {
        System.out.println(content);
    }
}
