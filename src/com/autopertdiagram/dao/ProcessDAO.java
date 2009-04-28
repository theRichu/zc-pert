package com.autopertdiagram.dao;

import com.autopertdiagram.pojo.Process;
import com.autopertdiagram.pojo.Project;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;

import java.util.List;

/**
 * A data access object (DAO) providing persistence and search support for
 * Process entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 * @see com.autopertdiagram.pojo.Process
 */
public class ProcessDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(ProcessDAO.class);
    // property constants
    public static final String SYMBOL = "symbol";
    public static final String NAME = "name";
    public static final String PLAN_PERIOD = "planPeriod";
    public static final String ACTUAL_PERIOD = "actualPeriod";
    public static final String PLAN_COST = "planCost";
    public static final String ACTUAL_COST = "actualCost";
    public static final String OUTPUT = "output";
    public static final String IS_VIRTUAL = "isVirtual";
    public static final String IS_CRITICL = "isCriticl";
    public static final String IS_STARTED = "isStarted";
    public static final String IS_FINISHED = "isFinished";
    public static final String PROJECT = "project";
    public static final String SOURCE_NODE = "nodeBySourceNode";
    public static final String TARGET_NODE = "nodeByTargetNode";
    public static final String PRE_FOR_PREPROCESS = "prerelationsForPreProcess";
    public static final String PRE_FOR_PROCESS = "prerelationsForProcess";

    public void save(Process transientInstance) {
        log.debug("saving Process instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Process persistentInstance) {
        log.debug("deleting Process instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public void deleteVirtualByProject(Project project) {
        log.debug("deleting Process instance of the project:" + project.getId());
        try {
            String hqlDelete = "delete Process p where p.isVirtual = ? and p.project=?";
            getSession().createQuery(hqlDelete)
                    .setString(0, "true")
                    .setParameter(1, project)
                    .executeUpdate();
        } catch (RuntimeException re) {
            log.error("delete process instance of the project:" + project.getId() + "failed");
            throw re;
        }

    }

    public Process findById(java.lang.Integer id) {
        log.debug("getting Process instance with id: " + id);
        try {
            Process instance = (Process) getSession().get(
                    "com.autopertdiagram.pojo.Process", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Process> findByExample(Process instance) {
        log.debug("finding Process instance by example");
        try {
            List<Process> results = (List<Process>) getSession()
                    .createCriteria("com.autopertdiagram.pojo.Process").add(
                            create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List<Process> findNoSuffix(Object project) {
        log.debug("finding Process instance which no suffix");
        try {
            String queryString = "from Process as model where model."
                    + PRE_FOR_PREPROCESS + ".size= 0 and model." + PROJECT
                    + "=? and model." + IS_VIRTUAL + "=? order by model."
                    + SOURCE_NODE + ".label asc , model." + TARGET_NODE + ".label desc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, project);
            queryObject.setString(1, "false");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("finding Process instance which no suffix failed", re);
            throw re;
        }
    }

    public List findNotVirtualByproject(Project project) {
        log.debug("finding Process instance which is not virtual of project: " + project.getId());
        try {
            String queryString = "from Process as model where model."
                    + IS_VIRTUAL + "= ? and model." + PROJECT + "=?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, "false");
            queryObject.setParameter(1,project);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("finding Process instance which is not virtual of project" + project.getId(), re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Process instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Process as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<Process> findBySymbol(Object symbol) {
        return findByProperty(SYMBOL, symbol);
    }

    public List<Process> findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List<Process> findByProject(Object project) {
        return findByProperty(PROJECT, project);
    }

    public List<Process> findByPlanPeriod(Object planPeriod) {
        return findByProperty(PLAN_PERIOD, planPeriod);
    }

    public List<Process> findByActualPeriod(Object actualPeriod) {
        return findByProperty(ACTUAL_PERIOD, actualPeriod);
    }

    public List<Process> findByPlanCost(Object planCost) {
        return findByProperty(PLAN_COST, planCost);
    }

    public List<Process> findByActualCost(Object actualCost) {
        return findByProperty(ACTUAL_COST, actualCost);
    }

    public List<Process> findByOutput(Object output) {
        return findByProperty(OUTPUT, output);
    }

    public List<Process> findByIsVirtual(Object isVirtual) {
        return findByProperty(IS_VIRTUAL, isVirtual);
    }

    public List<Process> findByIsCriticl(Object isCriticl) {
        return findByProperty(IS_CRITICL, isCriticl);
    }

    public List<Process> findByIsStarted(Object isStarted) {
        return findByProperty(IS_STARTED, isStarted);
    }

    public List<Process> findByIsFinished(Object isFinished) {
        return findByProperty(IS_FINISHED, isFinished);
    }

    public List<Process> findBySourceNode(Object sourceNode) {
        return findByProperty(SOURCE_NODE, sourceNode);
    }

    public List<Process> findByTargetNode(Object targetNode) {
        return findByProperty(TARGET_NODE, targetNode);
    }

    public List findAll() {
        log.debug("finding all Process instances");
        try {
            String queryString = "from Process";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllNodeAndPeriod(Project project) {
        log
                .debug("finding all Process instances which ordered by planStartDate planEndDate");
        try {
            String queryString = "select new com.autopertdiagram.diagram.NodeAndPeriod(model.nodeBySourceNode.id,model.nodeByTargetNode.id,model.planPeriod) from Process as model where model.project = ? order by model.planStartDate asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, project);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllOrdered(Project project) {
        log
                .debug("finding all Process instances which ordered by planStartDate planEndDate");
        try {
            String queryString = "from Process as model where model.project = ? order by model.planStartDate asc, model.prerelationsForProcess.size asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, project);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Process merge(Process detachedInstance) {
        log.debug("merging Process instance");
        try {
            Process result = (Process) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Process instance) {
        log.debug("attaching dirty Process instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Process instance) {
        log.debug("attaching clean Process instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void updateCoordinate(String criticl, int sId, int tId) {
        log.debug("updating Process with isCriticl");
        try {
            String hqlUpdate = "update Process p set p.isCriticl = :criticl where p.nodeBySourceNode.id = :sId and p.nodeByTargetNode.id = :tId";
            getSession().createQuery(hqlUpdate)
                    .setString("criticl", criticl)
                    .setInteger("sId", sId)
                    .setInteger("tId", tId)
                    .executeUpdate();
            log.debug("update coordinate successful");
        } catch (RuntimeException re) {
            log.error("update coordinate failed", re);
            throw re;
        }
    }

    public void update(Process process) {                
        log.debug("update process:" + process.getId());
        try {
            String hqlUpdate = "update Process p set p.name = ?, p.planPeriod = ?, p.planCost = ?, p.planStartDate = ?, " +
                    "p.output = ?, p.actualStartDate = ?, p.actualCost = ?, p.actualPeriod = ? where p.id = ?";
            getSession().createQuery(hqlUpdate)
                    .setParameter(0, process.getName())
                    .setParameter(1, process.getPlanPeriod())
                    .setParameter(2, process.getPlanCost())
                    .setParameter(3, process.getPlanStartDate())
                    .setParameter(4, process.getOutput())
                    .setParameter(5, process.getActualStartDate())
                    .setParameter(6, process.getActualCost())
                    .setParameter(7, process.getActualPeriod())
                    .setParameter(8, process.getId())
                    .executeUpdate();
            log.debug("update process:" + process + "successful");
        } catch (RuntimeException re) {
            log.error("update process: " + process.getId() + " failed", re);
            throw re;
        }
    }
}