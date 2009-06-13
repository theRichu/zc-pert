package com.ziscloud.zcdiagram.draw;


/**
 * 编号的接口类，封装编号算法的类必须实现此接口
 * @author shunyunwang
 */
public interface NumberNode {
	/**
	 * 进行编号的实际操作
	 * @throws StripesServletException
	 */
	public void number();
}
