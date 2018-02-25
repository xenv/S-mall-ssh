package tmall.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * DAO层，获取 SessionFactory
 */

public interface DAO {
    public SessionFactory getSessionFactory();
}
