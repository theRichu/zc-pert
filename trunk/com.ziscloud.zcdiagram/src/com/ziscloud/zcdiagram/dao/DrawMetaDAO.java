package com.ziscloud.zcdiagram.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;

import com.ziscloud.zcdiagram.pojo.DrawMeta;
import com.ziscloud.zcdiagram.pojo.Project;

/**
 * A data access object (DAO) providing persistence and search support for
 * DrawMeta entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ziscloud.zcdiagram.pojo.DrawMeta
 * @author MyEclipse Persistence Tools
 */

public class DrawMetaDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(DrawMetaDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String SYMBOL = "symbol";
	public static final String PERIOD = "period";
	public static final String IS_VIRTUAL = "isVirtual";
	public static final String IS_CRITICL = "isCriticl";
	public static final String IS_STARTED = "isStarted";
	public static final String IS_ENDED = "isEnded";
	public static final String ORDINAL = "ordinal";

	public void save(DrawMeta transientInstance) {
		log.debug("saving DrawMeta instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DrawMeta persistentInstance) {
		log.debug("deleting DrawMeta instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DrawMeta findById(java.lang.Integer id) {
		log.debug("getting DrawMeta instance with id: " + id);
		try {
			DrawMeta instance = (DrawMeta) getSession().get(
					"com.ziscloud.zcdiagram.pojo.DrawMeta", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DrawMeta> findByExample(DrawMeta instance) {
		log.debug("finding DrawMeta instance by example");
		try {
			List<DrawMeta> results = (List<DrawMeta>) getSession()
					.createCriteria("com.ziscloud.zcdiagram.pojo.DrawMeta")
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
		log.debug("finding DrawMeta instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from DrawMeta as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DrawMeta> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<DrawMeta> findBySymbol(Object symbol) {
		return findByProperty(SYMBOL, symbol);
	}

	public List<DrawMeta> findByPeriod(Object period) {
		return findByProperty(PERIOD, period);
	}

	public List<DrawMeta> findByIsVirtual(Object isVirtual) {
		return findByProperty(IS_VIRTUAL, isVirtual);
	}

	public List<DrawMeta> findByIsCriticl(Object isCriticl) {
		return findByProperty(IS_CRITICL, isCriticl);
	}

	public List<DrawMeta> findByIsStarted(Object isStarted) {
		return findByProperty(IS_STARTED, isStarted);
	}

	public List<DrawMeta> findByIsEnded(Object isEnded) {
		return findByProperty(IS_ENDED, isEnded);
	}

	public List<DrawMeta> findByOrdinal(Object ordinal) {
		return findByProperty(ORDINAL, ordinal);
	}

	public List findAll() {
		log.debug("finding all DrawMeta instances");
		try {
			String queryString = "from DrawMeta";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DrawMeta merge(DrawMeta detachedInstance) {
		log.debug("merging DrawMeta instance");
		try {
			DrawMeta result = (DrawMeta) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DrawMeta instance) {
		log.debug("attaching dirty DrawMeta instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DrawMeta instance) {
		log.debug("attaching clean DrawMeta instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List findAllNodeAndPeriod(Project project, int model) {
		log.debug("finding all DrawMeta instances"
				+ " which ordered by StartDate EndDate");
		try {
			String queryString = "select new com.ziscloud.zcdiagram.draw.NodeAndPeriod(model.drawNodeByStartNode.id,model.drawNodeByEndNode.id,model.period) from DrawMeta as model where model.project = ? and model.model=? order by model.startDate asc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			queryObject.setParameter(1, model);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public void updateCoordinate(String criticl, int sId, int tId) {
		log.debug("updating Process with isCriticl");
		try {
			String hqlUpdate = "update DrawMeta p set p.isCriticl = :criticl where p.drawNodeByStartNode.id = :sId and p.drawNodeByEndNode.id = :tId";
			getSession().createQuery(hqlUpdate).setString("criticl", criticl)
					.setInteger("sId", sId).setInteger("tId", tId)
					.executeUpdate();
			log.debug("update coordinate successful");
		} catch (RuntimeException re) {
			log.error("update coordinate failed", re);
			throw re;
		}
	}

	public List<DrawMeta> findNoSuffix(Project project, int model) {
		log.debug("finding DrawMeta instance which no suffix");
		try {
			String queryString = "from DrawMeta as model where model.rltnForPreAct.size= 0 and model.project=? and model.isVirtual=? and model.model=? order by model.drawNodeByStartNode.label asc , model.drawNodeByEndNode.label desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			queryObject.setString(1, "false");
			queryObject.setParameter(2, model);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("finding DrawMeta instance which no suffix failed", re);
			throw re;
		}
	}

	public List<DrawMeta> findAllOrdered(Project project, int model) {
		log.debug("finding all DrawMeta instances "
				+ "which ordered by StartDate EndDate");
		try {
			String queryString = "from DrawMeta as model where model.project = ? and model.model=? order by model.startDate asc, model.rltnForCurAct.size asc, model.id asc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			queryObject.setParameter(1, model);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public void deleteByProject(Project project, int model) {
		log.debug("deleting DrawMeta instance of the project:"
				+ project.getId());
		try {
			String hqlDelete = "delete DrawMeta p where p.project=? and p.model=?";
			getSession().createQuery(hqlDelete).setParameter(0, project)
					.setParameter(1, model).executeUpdate();
		} catch (RuntimeException re) {
			log.error("delete DrawMeta instance of the project:"
					+ project.getId() + "failed");
			throw re;
		}
	}

	public List<DrawMeta> findByProject(Project project, int model) {
		log.debug("finding all DrawMeta instances ");
		try {
			String queryString = "from DrawMeta as dm where dm.project = ? and dm.model=?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project).setParameter(1, model);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}