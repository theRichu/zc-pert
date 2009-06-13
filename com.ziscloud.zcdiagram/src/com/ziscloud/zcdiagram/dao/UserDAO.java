package com.ziscloud.zcdiagram.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;

import com.ziscloud.zcdiagram.pojo.User;

/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.ziscloud.zcdiagram.pojo.User
 * @author MyEclipse Persistence Tools
 */

public class UserDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(UserDAO.class);
	// property constants
	public static final String LOGIN_NAME = "loginName";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String DEPARTMENT = "department";
	public static final String TELEPHONE = "telephone";
	public static final String EMAIL = "email";
	public static final String OFFICE = "office";

	public void save(User transientInstance) {
		log.debug("saving User instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getSession().get(
					"com.ziscloud.zcdiagram.pojo.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<User> findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List<User> results = (List<User>) getSession().createCriteria(
					"com.ziscloud.zcdiagram.pojo.User").add(create(instance))
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
		log.debug("finding User instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<User> findByLoginName(Object loginName) {
		return findByProperty(LOGIN_NAME, loginName);
	}

	public List<User> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<User> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<User> findByDepartment(Object department) {
		return findByProperty(DEPARTMENT, department);
	}

	public List<User> findByTelephone(Object telephone) {
		return findByProperty(TELEPHONE, telephone);
	}

	public List<User> findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List<User> findByOffice(Object office) {
		return findByProperty(OFFICE, office);
	}

	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}