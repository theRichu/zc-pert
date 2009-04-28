package com.autopertdiagram.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;
import com.autopertdiagram.pojo.Prerelation;
import com.autopertdiagram.pojo.Process;

/**
 * A data access object (DAO) providing persistence and search support for
 * Prerelation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autopertdiagram.pojo.Prerelation
 * @author MyEclipse Persistence Tools
 */

public class PrerelationDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(PrerelationDAO.class);
	private static final String PROCESS = "processByProcess";
	private static final String PREPROCESS = "processByPreProcess";

	// property constants
	
	public int countByPreProcess(Process preProcess) {
		log.debug("counting Prerelation instance with preProcess: " + preProcess.getId());
		try {
			String queryString = "select count(*) from Prerelation as model where model."
					+ PREPROCESS + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, preProcess);
			return ((Long) queryObject.uniqueResult()).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}	
	}
	
	public List<Prerelation> findByPreProcess(Process preProcess) {
		return findByProperty(PREPROCESS, preProcess);
	}
	
	public List<Prerelation> findByProcess(Process process) {
		return findByProperty(PROCESS, process);
	}

	public void save(Prerelation transientInstance) {
		log.debug("saving Prerelation instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Prerelation persistentInstance) {
		log.debug("deleting Prerelation instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Prerelation findById(java.lang.Integer id) {
		log.debug("getting Prerelation instance with id: " + id);
		try {
			Prerelation instance = (Prerelation) getSession().get(
					"com.autopertdiagram.pojo.Prerelation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Prerelation> findByExample(Prerelation instance) {
		log.debug("finding Prerelation instance by example");
		try {
			List<Prerelation> results = (List<Prerelation>) getSession()
					.createCriteria("com.autopertdiagram.pojo.Prerelation")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Prerelation> findByProperty(String propertyName, Object value) {
		log.debug("finding Prerelation instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Prerelation as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Prerelation instances");
		try {
			String queryString = "from Prerelation";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Prerelation merge(Prerelation detachedInstance) {
		log.debug("merging Prerelation instance");
		try {
			Prerelation result = (Prerelation) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Prerelation instance) {
		log.debug("attaching dirty Prerelation instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Prerelation instance) {
		log.debug("attaching clean Prerelation instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}