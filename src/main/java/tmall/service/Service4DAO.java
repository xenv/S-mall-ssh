package tmall.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import tmall.dao.DAO;

/**
 * Service层处理dao层的基类，通过反射获取 baseService 子类的实际业务子类的名称，拉取相应的 dao类和 pojo类，
 * 代理 createCriteria方法和 getSession方法
 */

public interface Service4DAO {

    public Criteria createCriteria();

    public Session getSession();

}
