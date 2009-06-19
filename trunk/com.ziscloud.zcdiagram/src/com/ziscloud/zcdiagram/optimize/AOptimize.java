package com.ziscloud.zcdiagram.optimize;

import java.util.List;

import com.ziscloud.zcdiagram.pojo.Activity;

public abstract class AOptimize {
	/**
	 * the activities as the optimization's input
	 */
	protected List<Activity> activities;

	/**
	 * construct the optimization using the giving activities
	 * 
	 * @param activities
	 *            the activities as the optimization's input
	 */
	public AOptimize(List<Activity> activities) {
		this.activities = activities;
	}

	/**
	 * the optimization using the model one.
	 * 
	 * @return the start date and end date of the activities. String[0] is the
	 *         start date, String[1] is the end date, and they are also an array
	 *         with the same length.
	 */
	abstract public List<Info> modelOneOptimize();

	/**
	 * the optimization using the model two
	 * 
	 * @return the start date and end date of the activities. String[0] is the
	 *         start date, String[1] is the end date, and they are also an array
	 *         with the same length.
	 */
	abstract public List<Info> modelTwoOptimize();

}
