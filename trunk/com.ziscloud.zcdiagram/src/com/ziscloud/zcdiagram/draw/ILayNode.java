package com.ziscloud.zcdiagram.draw;

/**
 * 布点的接口类，封装布点算法的类必须实现此接口
 * 
 * @author shunyunwang
 * 
 */
public interface ILayNode {
	public static final int X_RATIO = 150;
	public static final int Y_RATIO = 50;

	/**
	 * 进行布点的实际方法
	 */
	public void layNode();
}
