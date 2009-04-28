package com.autopertdiagram.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;
import com.autopertdiagram.pojo.Project;

/**
 * A data access object (DAO) providing persistence and search support for
 * Project entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.autopertdiagram.pojo.Project
 * @author MyEclipse Persistence Tools
 */

public class ProjectDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ProjectDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String PLAN_PERIOD = "planPeriod";
	public static final String PLAN_COST = "planCost";
	public static final String CONTRACTOR = "contractor";
	public static final String ACTUAL_PERIOD = "actualPeriod";
	public static final String ACTUAL_COST = "actualCost";

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
					"com.autopertdiagram.pojo.Project", id);
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
					.createCriteria("com.autopertdiagram.pojo.Project").add(
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

	public List<Project> findByPlanPeriod(Object planPeriod) {
		return findByProperty(PLAN_PERIOD, planPeriod);
	}

	public List<Project> findByPlanCost(Object planCost) {
		return findByProperty(PLAN_COST, planCost);
	}

	public List<Project> findByContractor(Object contractor) {
		return findByProperty(CONTRACTOR, contractor);
	}

	public List<Project> findByActualPeriod(Object actualPeriod) {
		return findByProperty(ACTUAL_PERIOD, actualPeriod);
	}

	public List<Project> findByActualCost(Object actualCost) {
		return findByProperty(ACTUAL_COST, actualCost);
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

    public void update(Project project) {
        log.debug("update project:" + project.getId());
        try {
            String hqlUpdate = "update Project p set p.planPeriod = ?" +
                    ", p.planCost = ?, p.planStartDate = ?, p.planEndDate = ?, p.contractor = ?" +
                    ", p.actualStartDate = ?, p.actualEndDate = ?, p.actualCost = ?, p.actualPeriod = ?, p.name = ? where p.id = ?";
            getSession().createQuery(hqlUpdate)
                    .setParameter(0, project.getPlanPeriod())
                    .setParameter(1, project.getPlanCost())
                    .setParameter(2, project.getPlanStartDate())
                    .setParameter(3, project.getPlanEndDate())
                    .setParameter(4, project.getContractor())
                    .setParameter(5, project.getActualStartDate())
                    .setParameter(6, project.getActualEndDate())
                    .setParameter(7, project.getActualCost())
                    .setParameter(8, project.getActualPeriod())
                    .setParameter(9, project.getName())
                    .setParameter(10, project.getId())
                    .executeUpdate();
            log.debug("update project:" + project.getId() + "successful");
        } catch (RuntimeException re) {
            log.error("update project:" + project.getId() + "failed", re);
            throw re;
        }
    }

    public void updateModifyDate(Date date, int project) {
        log.debug("update last modify date of project:" + project);
        try {
            String hqlUpdate = "update Project p set p.lastModifyDate = ? where p.id = ?";
            getSession().createQuery(hqlUpdate)
                    .setParameter(0, date.getTime())
                    .setParameter(1, project)
                    .executeUpdate();
            log.debug("update last modify date of project:" + project + "successful");
        } catch (RuntimeException re) {
            log.error("update last modify date of project:" + project + "failed", re);
            throw re;
        }
    }
}