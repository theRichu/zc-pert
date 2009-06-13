package com.ziscloud.zcdiagram.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;

import com.ziscloud.zcdiagram.pojo.Project;

/**
 * A data access object (DAO) providing persistence and search support for
 * Project entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ziscloud.zcdiagram.pojo.Project
 * @author MyEclipse Persistence Tools
 */

public class ProjectDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ProjectDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String SYMBOL = "symbol";
	public static final String PLAN_PERIOD = "planPeriod";
	public static final String PLAN_COST = "planCost";
	public static final String MANAGER = "manager";
	public static final String BUILDER = "builder";
	public static final String DESIGNER = "designer";
	public static final String ACTUAL_PERIOD = "actualPeriod";
	public static final String ACTUAL_COST = "actualCost";
	public static final String REMARKS = "remarks";
	public static final String MODIFY_TIME = "modifyTime";
	public static final String DRAW_TIME = "drawTime";
	public static final String IS_DELETED = "isDeleted";

	public void save(Project transientInstance) {
		log.debug("saving Project instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Project persistentInstance) {
		log.debug("deleting Project instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Project findById(java.lang.Integer id) {
		log.debug("getting Project instance with id: " + id);
		try {
			Project instance = (Project) getSession().get(
					"com.ziscloud.zcdiagram.pojo.Project", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Project> findByExample(Project instance) {
		log.debug("finding Project instance by example");
		try {
			List<Project> results = (List<Project>) getSession()
					.createCriteria("com.ziscloud.zcdiagram.pojo.Project").add(
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
		log.debug("finding Project instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Project as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Project> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Project> findBySymbol(Object symbol) {
		return findByProperty(SYMBOL, symbol);
	}

	public List<Project> findByPlanPeriod(Object planPeriod) {
		return findByProperty(PLAN_PERIOD, planPeriod);
	}

	public List<Project> findByPlanCost(Object planCost) {
		return findByProperty(PLAN_COST, planCost);
	}

	public List<Project> findByManager(Object manager) {
		return findByProperty(MANAGER, manager);
	}

	public List<Project> findByBuilder(Object builder) {
		return findByProperty(BUILDER, builder);
	}

	public List<Project> findByDesigner(Object designer) {
		return findByProperty(DESIGNER, designer);
	}

	public List<Project> findByActualPeriod(Object actualPeriod) {
		return findByProperty(ACTUAL_PERIOD, actualPeriod);
	}

	public List<Project> findByActualCost(Object actualCost) {
		return findByProperty(ACTUAL_COST, actualCost);
	}

	public List<Project> findByRemarks(Object remarks) {
		return findByProperty(REMARKS, remarks);
	}

	public List<Project> findByModifyTime(Object modifyTime) {
		return findByProperty(MODIFY_TIME, modifyTime);
	}

	public List<Project> findByDrawTime(Object drawTime) {
		return findByProperty(DRAW_TIME, drawTime);
	}

	public List<Project> findByIsDeleted(Object isDeleted) {
		return findByProperty(IS_DELETED, isDeleted);
	}

	public List findAll() {
		log.debug("finding all Project instances");
		try {
			String queryString = "from Project";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Project merge(Project detachedInstance) {
		log.debug("merging Project instance");
		try {
			Project result = (Project) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Project instance) {
		log.debug("attaching dirty Project instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Project instance) {
		log.debug("attaching clean Project instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}