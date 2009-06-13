package com.ziscloud.zcdiagram.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;

import com.ziscloud.zcdiagram.pojo.UserLog;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserLog entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ziscloud.zcdiagram.pojo.UserLog
 * @author MyEclipse Persistence Tools
 */

public class UserLogDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(UserLogDAO.class);
	// property constants
	public static final String LOG = "log";

	public void save(UserLog transientInstance) {
		log.debug("saving UserLog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserLog persistentInstance) {
		log.debug("deleting UserLog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserLog findById(java.lang.Integer id) {
		log.debug("getting UserLog instance with id: " + id);
		try {
			UserLog instance = (UserLog) getSession().get(
					"com.ziscloud.zcdiagram.pojo.UserLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<UserLog> findByExample(UserLog instance) {
		log.debug("finding UserLog instance by example");
		try {
			List<UserLog> results = (List<UserLog>) getSession()
					.createCriteria("com.ziscloud.zcdiagram.pojo.UserLog").add(
							create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserLog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserLog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<UserLog> findByLog(Object log) {
		return findByProperty(LOG, log);
	}

	public List findAll() {
		log.debug("finding all UserLog instances");
		try {
			String queryString = "from UserLog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserLog merge(UserLog detachedInstance) {
		log.debug("merging UserLog instance");
		try {
			UserLog result = (UserLog) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserLog instance) {
		log.debug("attaching dirty UserLog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserLog instance) {
		log.debug("attaching clean UserLog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}