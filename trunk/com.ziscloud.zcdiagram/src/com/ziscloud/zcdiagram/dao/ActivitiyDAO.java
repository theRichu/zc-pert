package com.ziscloud.zcdiagram.dao;

import static org.hibernate.criterion.Example.create;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;

import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.pojo.Project;

/**
 * A data access object (DAO) providing persistence and search support for
 * Activitiy entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ziscloud.zcdiagram.pojo.Activity
 * @author MyEclipse Persistence Tools
 */

public class ActivitiyDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ActivitiyDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String SYMBOL = "symbol";
	public static final String PRE_ACTIVITY = "preActivity";
	public static final String PLAN_PERIOD = "planPeriod";
	public static final String PLAN_COST = "planCost";
	public static final String OUTPUT = "output";
	public static final String ACTUAL_PERIOD = "actualPeriod";
	public static final String ACTUAL_COST = "actualCost";
	public static final String BUILDER = "builder";
	public static final String RAR_DAYS = "rarDays";
	public static final String RAR_COST = "rarCost";
	public static final String OPRAR_DAYS = "oprarDays";
	public static final String REMARKS = "remarks";
	public static final String PROJECT = "project";
	public static final String PLAN_START = "planStartDate";
	public static final String PLAN_END = "planEndDate";

	public void save(Activity transientInstance) {
		log.debug("saving Activity instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Activity persistentInstance) {
		log.debug("deleting Activity instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Activity findById(java.lang.Integer id) {
		log.debug("getting Activity instance with id: " + id);
		try {
			Activity instance = (Activity) getSession().get(
					"com.ziscloud.zcdiagram.pojo.Activity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Activity> findByExample(Activity instance) {
		log.debug("finding Activity instance by example");
		try {
			List<Activity> results = (List<Activity>) getSession()
					.createCriteria("com.ziscloud.zcdiagram.pojo.Activity")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Activity instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Activity as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Activity> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Activity> findBySymbol(Object symbol) {
		return findByProperty(SYMBOL, symbol);
	}

	public List<Activity> findByPreActivity(Object preActivity) {
		return findByProperty(PRE_ACTIVITY, preActivity);
	}

	public List<Activity> findByPlanPeriod(Object planPeriod) {
		return findByProperty(PLAN_PERIOD, planPeriod);
	}

	public List<Activity> findByPlanCost(Object planCost) {
		return findByProperty(PLAN_COST, planCost);
	}

	public List<Activity> findByOutput(Object output) {
		return findByProperty(OUTPUT, output);
	}

	public List<Activity> findByActualPeriod(Object actualPeriod) {
		return findByProperty(ACTUAL_PERIOD, actualPeriod);
	}

	public List<Activity> findByActualCost(Object actualCost) {
		return findByProperty(ACTUAL_COST, actualCost);
	}

	public List<Activity> findByBuilder(Object builder) {
		return findByProperty(BUILDER, builder);
	}

	public List<Activity> findByRarDays(Object rarDays) {
		return findByProperty(RAR_DAYS, rarDays);
	}

	public List<Activity> findByRarCost(Object rarCost) {
		return findByProperty(RAR_COST, rarCost);
	}

	public List<Activity> findByOprarDays(Object oprarDays) {
		return findByProperty(OPRAR_DAYS, oprarDays);
	}

	public List<Activity> findByRemarks(Object remarks) {
		return findByProperty(REMARKS, remarks);
	}

	public List findAll() {
		log.debug("finding all Activity instances");
		try {
			String queryString = "from Activity";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Activity merge(Activity detachedInstance) {
		log.debug("merging Activity instance");
		try {
			Activity result = (Activity) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Activity instance) {
		log.debug("attaching dirty Activity instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Activity instance) {
		log.debug("attaching clean Activity instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List<Activity> findByProject(Project project) {
		return findByProperty(PROJECT, project);
	}

	public List<Activity> findByProject(Project project, Date planStartDate) {
		log.debug("finding Activity instance with project: " + project
				+ ", and plan start date: " + planStartDate);
		try {
			String queryString = "from Activity as model where model."
					+ PROJECT + "= ? and " + PLAN_END + " <= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			queryObject.setParameter(1, planStartDate);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Integer countActivity(Project project) {
		log.debug("count Activity instance with project: " + project);
		try {
			String queryString = "select count(*) from Activity as model where model."
					+ PROJECT + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			return new Integer(queryObject.uniqueResult().toString());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Integer countStarted(Project project) {
		log.debug("count Activity instance with project: " + project);
		try {
			String queryString = "select count(*) from Activity as model where model."
					+ PROJECT + "= ? and model.actualStartDate is not null";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			return new Integer(queryObject.uniqueResult().toString());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Integer countEnded(Project project) {
		log.debug("count Activity instance with project: " + project);
		try {
			String queryString = "select count(*) from Activity as model where model."
					+ PROJECT + "= ? and model.actualEndDate is not null";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			return new Integer(queryObject.uniqueResult().toString());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Integer countNotEnded(Project project) {
		log.debug("count Activity instance with project: " + project);
		try {
			String queryString = "select count(*) from Activity as model where model."
					+ PROJECT
					+ "= ? and (model.actualEndDate is not null and model.actualEndDate is null)";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			return new Integer(queryObject.uniqueResult().toString());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Double countUsedCost(Project project) {
		log.debug("count Activity instance with project: " + project);
		try {
			String queryString = "select sum(model.actualCost) from Activity as model where model."
					+ PROJECT
					+ "= ? and (model.actualCost is not null and model.actualEndDate is not null)";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			Object o = queryObject.uniqueResult();
			if (null == o)
				return new Double(0);
			else
				return new Double(queryObject.uniqueResult().toString());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Integer countUsedPeriod(Project project) {
		log.debug("count Activity instance with project: " + project);
		try {
			String queryString = "select sum(model.actualPeriod) from Activity as model where model."
					+ PROJECT
					+ "= ? and (model.actualPeriod is not null and model.actualEndDate is not null)";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			Object o = queryObject.uniqueResult();
			if (null == o)
				return new Integer(0);
			else
				return new Integer(queryObject.uniqueResult().toString());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}