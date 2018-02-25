package tmall.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import tmall.dao.DAO;

/**
 * @see DAO
 */
@Repository
public class DAOImpl implements DAO {
    private SessionFactory sessionFactory;

    @Resource(name="sf")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
