package com.ziscloud.zcdiagram.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;

import com.ziscloud.zcdiagram.pojo.DrawNode;
import com.ziscloud.zcdiagram.pojo.Project;

/**
 * A data access object (DAO) providing persistence and search support for
 * DrawNode entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ziscloud.zcdiagram.pojo.DrawNode
 * @author MyEclipse Persistence Tools
 */

public class DrawNodeDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(DrawNodeDAO.class);
	// property constants
	public static final String LABEL = "label";
	public static final String X = "x";
	public static final String Y = "y";
	public static final String IN_DEGREE = "inDegree";
	public static final String OUT_DEGREE = "outDegree";

	public void save(DrawNode transientInstance) {
		log.debug("saving DrawNode instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DrawNode persistentInstance) {
		log.debug("deleting DrawNode instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DrawNode findById(java.lang.Integer id) {
		log.debug("getting DrawNode instance with id: " + id);
		try {
			DrawNode instance = (DrawNode) getSession().get(
					"com.ziscloud.zcdiagram.pojo.DrawNode", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DrawNode> findByExample(DrawNode instance) {
		log.debug("finding DrawNode instance by example");
		try {
			List<DrawNode> results = (List<DrawNode>) getSession()
					.createCriteria("com.ziscloud.zcdiagram.pojo.DrawNode")
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
		log.debug("finding DrawNode instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from DrawNode as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DrawNode> findByLabel(Object label) {
		return findByProperty(LABEL, label);
	}

	public List<DrawNode> findByX(Object x) {
		return findByProperty(X, x);
	}

	public List<DrawNode> findByY(Object y) {
		return findByProperty(Y, y);
	}

	public List<DrawNode> findByInDegree(Object inDegree) {
		return findByProperty(IN_DEGREE, inDegree);
	}

	public List<DrawNode> findByOutDegree(Object outDegree) {
		return findByProperty(OUT_DEGREE, outDegree);
	}

	public List findAll() {
		log.debug("finding all DrawNode instances");
		try {
			String queryString = "from DrawNode";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DrawNode merge(DrawNode detachedInstance) {
		log.debug("merging DrawNode instance");
		try {
			DrawNode result = (DrawNode) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DrawNode instance) {
		log.debug("attaching dirty DrawNode instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DrawNode instance) {
		log.debug("attaching clean DrawNode instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void updateCoordinate(int id, int x, int y) {
		log.debug("updating node with x and y coordinate");
		try {
			String hqlUpdate = "update DrawNode n set n.x = :newX, n.y = :newY where n.id = :nId";
			getSession().createQuery(hqlUpdate).setInteger("newX", x)
					.setInteger("newY", y).setInteger("nId", id)
					.executeUpdate();
			log.debug("update coordinate successful");
		} catch (RuntimeException re) {
			log.error("update coordinate failed", re);
			throw re;
		}
	}

	public void deleteRedundancy() {
		log.debug("deleting node which no x and y coordinate");
		try {
			String hqlDelete = "delete from DrawNode n where n.x is null or n.y is null";
			getSession().createQuery(hqlDelete).executeUpdate();
			log.debug("delete redundancy node successful");
		} catch (RuntimeException re) {
			log.error("delete redundancy node failed", re);
			throw re;
		}
	}

	public void deleteByProject(Project project, int model) {
		log.debug("deleting Node instance of the project:" + project.getId());
		try {
			String hqlDelete = "DELETE DrawNode n WHERE n.project = ? and n.model=?";
			getSession().createQuery(hqlDelete).setParameter(0, project)
					.setParameter(1, model).executeUpdate();
		} catch (RuntimeException re) {
			log.error("delete Node instance of the project:" + project.getId()
					+ " failed");
			throw re;
		}
	}

	public List<DrawNode> findByProject(Project project, int model) {
		log.debug("finding DrawNode instance");
		try {
			String queryString = "from DrawNode as dn where dn.project= ? and dn.model=?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, project);
			queryObject.setParameter(1, model);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

}