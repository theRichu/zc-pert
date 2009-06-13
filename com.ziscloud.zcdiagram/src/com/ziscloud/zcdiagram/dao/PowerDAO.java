package com.ziscloud.zcdiagram.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;

import com.ziscloud.zcdiagram.pojo.Power;

/**
 * A data access object (DAO) providing persistence and search support for Power
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.ziscloud.zcdiagram.pojo.Power
 * @author MyEclipse Persistence Tools
 */

public class PowerDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(PowerDAO.class);
	// property constants
	public static final String REMARKS = "remarks";

	public void save(Power transientInstance) {
		log.debug("saving Power instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Power persistentInstance) {
		log.debug("deleting Power instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Power findById(java.lang.Integer id) {
		log.debug("getting Power instance with id: " + id);
		try {
			Power instance = (Power) getSession().get(
					"com.ziscloud.zcdiagram.pojo.Power", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Power> findByExample(Power instance) {
		log.debug("finding Power instance by example");
		try {
			List<Power> results = (List<Power>) getSession().createCriteria(
					"com.ziscloud.zcdiagram.pojo.Power").add(create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Power instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Power as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Power> findByRemarks(Object remarks) {
		return findByProperty(REMARKS, remarks);
	}

	public List findAll() {
		log.debug("finding all Power instances");
		try {
			String queryString = "from Power";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Power merge(Power detachedInstance) {
		log.debug("merging Power instance");
		try {
			Power result = (Power) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Power instance) {
		log.debug("attaching dirty Power instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Power instance) {
		log.debug("attaching clean Power instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}