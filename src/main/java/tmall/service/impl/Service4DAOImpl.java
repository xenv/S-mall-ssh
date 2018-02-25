package tmall.service.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import tmall.dao.DAO;
import tmall.dao.impl.DAOImpl;
import tmall.service.Service4DAO;

import java.lang.reflect.ParameterizedType;

/**
 * Service层处理dao层的基类，通过反射获取 baseService 子类的实际业务子类的名称，拉取相应的 dao类和 pojo类，
 * 代理 createCriteria方法和 getSession方法
 */

public class Service4DAOImpl<P> implements Service4DAO {
    protected DAO dao;

    protected Class clazz;

    public Service4DAOImpl() {
        ParameterizedType t = (ParameterizedType) (getClass().getGenericSuperclass());
        if (t != null) {
            clazz = (Class) t.getActualTypeArguments()[0];
        }
    }

    @Autowired
    public void setDao(DAO dao) {
        this.dao = dao;
    }

    public Criteria createCriteria() {
        return getSession().createCriteria(clazz).add(Restrictions.isNull("deleteAt")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }

    public Session getSession() {
        return dao.getSessionFactory().getCurrentSession();
    }

}
