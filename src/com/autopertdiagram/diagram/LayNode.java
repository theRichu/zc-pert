package com.autopertdiagram.diagram;

import net.sourceforge.stripes.exception.StripesServletException;

/**
 * 布点的接口类，封装布点算法的类必须实现此接口
 * @author shunyunwang
 *
 */
public interface LayNode {
	/**
	 * 进行布点的实际方法
	 */
	public void layNode() throws StripesServletException;
}
