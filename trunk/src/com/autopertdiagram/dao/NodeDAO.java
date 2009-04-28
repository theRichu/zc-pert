package com.autopertdiagram.dao;

import com.autopertdiagram.pojo.Node;
import com.autopertdiagram.pojo.Project;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;

import java.util.List;

/**
 * A data access object (DAO) providing persistence and search support for Node
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @author MyEclipse Persistence Tools
 * @see com.autopertdiagram.pojo.Node
 */

public class NodeDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(NodeDAO.class);
    // property constants
    public static final String LABEL = "label";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String OUT_DEGREE = "outDegree";
    public static final String IN_DEGREE = "inDegree";
    public static final String PROJECT = "project";

    public void save(Node transientInstance) {
        log.debug("saving Node instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Node persistentInstance) {
        log.debug("deleting Node instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void deleteByProject(Project project) {
        log.debug("deleting Node instance of the project:" + project.getId());
        try {
            String hqlDelete = "DELETE Node n WHERE n.project = ?";
            getSession().createQuery(hqlDelete)
                    .setParameter(0, project)
                    .executeUpdate();
        } catch (RuntimeException re) {
            log.error("delete Node instance of the project:" + project.getId() + " failed");
            throw re;
        }
    }

    public Node findById(java.lang.Integer id) {
        log.debug("getting Node instance with id: " + id);
        try {
            Node instance = (Node) getSession().get(
                    "com.autopertdiagram.pojo.Node", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Node> findByExample(Node instance) {
        log.debug("finding Node instance by example");
        try {
            List<Node> results = (List<Node>) getSession().createCriteria(
                    "com.autopertdiagram.pojo.Node").add(create(instance))
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
        log.debug("finding Node instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Node as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<Node> findByLabel(Object label) {
        return findByProperty(LABEL, label);
    }

    public List<Node> findByX(Object x) {
        return findByProperty(X, x);
    }

    public List<Node> findByY(Object y) {
        return findByProperty(Y, y);
    }

    public List<Node> findByOutDegree(Object outDegree) {
        return findByProperty(OUT_DEGREE, outDegree);
    }

    public List<Node> findByInDegree(Object inDegree) {
        return findByProperty(IN_DEGREE, inDegree);
    }

    public List<Node> findByProject(Object project) {
        return findByProperty(PROJECT, project);
    }

    public List findAll() {
        log.debug("finding all Node instances");
        try {
            String queryString = "from Node";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Node merge(Node detachedInstance) {
        log.debug("merging Node instance");
        try {
            Node result = (Node) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Node instance) {
        log.debug("attaching dirty Node instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void updateCoordinate(int id, int x, int y) {
        log.debug("updating node with x and y coordinate");
        try {
            String hqlUpdate = "update Node n set n.x = :newX, n.y = :newY where n.id = :nId";
            getSession().createQuery(hqlUpdate)
                    .setInteger("newX", x)
                    .setInteger("newY", y)
                    .setInteger("nId", id)
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
            String hqlDelete = "delete from Node n where n.x is null or n.y is null";
            getSession().createQuery(hqlDelete).executeUpdate();
            log.debug("delete redundancy node successful");
        } catch (RuntimeException re) {
            log.error("delete redundancy node failed", re);
            throw re;
        }
    }

    public void attachClean(Node instance) {
        log.debug("attaching clean Node instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}