package com.autopertdiagram.util;

import net.sf.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: ZisCloud Date: 2009-4-12 Time: 10:39:03
 */
public class Utils {
    private static DateFormat formator = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date) {
        if (null == date) {
            return "未知";
        } else {
            return formator.format(date);
        }
    }

    public static String convertBoolean(Boolean aBoolean) {
        if (true == aBoolean) {
            return "是";
        } else {
            return "否";
        }
    }

    public static String convertBoolean(String aBoolean) {
        if ("true".equalsIgnoreCase(aBoolean)) {
            return "是";
        } else {
            return "否";
        }
    }

        /**
     * 客户端使用formpanel的异步方式提交数据时，需要返回JSON格式的反馈数据，
     * 此方法用于构建JSON格式反馈数据
     * @param isSuccess 数据提交是否成功
     * @param msg 反馈信息
     * @return JSON格式的反馈信息
     */
    public static String JsonResponse(boolean isSuccess, String msg) {
        JSONObject response = new JSONObject();
        response.put("success", isSuccess);
        if (null == msg) {
            response.put("msg", "success" + isSuccess);
        } else {
            response.put("msg", msg);
        }
        return response.toString();
    }
}
