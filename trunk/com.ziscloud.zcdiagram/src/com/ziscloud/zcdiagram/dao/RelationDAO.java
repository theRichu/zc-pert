package com.ziscloud.zcdiagram.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;

import com.ziscloud.zcdiagram.pojo.DrawMeta;
import com.ziscloud.zcdiagram.pojo.Relation;

import static org.hibernate.criterion.Example.create;

/**
 * A data access object (DAO) providing persistence and search support for
 * Relation entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ziscloud.zcdiagram.pojo.Relation
 * @author MyEclipse Persistence Tools
 */

public class RelationDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(RelationDAO.class);
	// property constants
	public static final String ORDINAL = "ordinal";

	public void save(Relation transientInstance) {
		log.debug("saving Relation instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Relation persistentInstance) {
		log.debug("deleting Relation instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Relation findById(java.lang.Integer id) {
		log.debug("getting Relation instance with id: " + id);
		try {
			Relation instance = (Relation) getSession().get(
					"com.ziscloud.zcdiagram.pojo.Relation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Relation> findByExample(Relation instance) {
		log.debug("finding Relation instance by example");
		try {
			List<Relation> results = (List<Relation>) getSession()
					.createCriteria("com.ziscloud.zcdiagram.pojo.Relation")
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
		log.debug("finding Relation instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Relation as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Relation> findByOrdinal(Object ordinal) {
		return findByProperty(ORDINAL, ordinal);
	}

	public List findAll() {
		log.debug("finding all Relation instances");
		try {
			String queryString = "from Relation";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Relation merge(Relation detachedInstance) {
		log.debug("merging Relation instance");
		try {
			Relation result = (Relation) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Relation instance) {
		log.debug("attaching dirty Relation instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Relation instance) {
		log.debug("attaching clean Relation instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public int countByPreDrawMeta(DrawMeta preDrawMeta) {
		log.debug("counting Prerelation instance with preProcess: "
				+ preDrawMeta.getId());
		try {
			String queryString = "select count(*) from Relation as model where model.drawMetaByPreAct= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, preDrawMeta);
			return ((Long) queryObject.uniqueResult()).intValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}