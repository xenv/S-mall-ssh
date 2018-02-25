package tmall.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import tmall.service.BaseService;
import tmall.util.Pagination;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;



/**
 * 该类继承 Service4DAOImpl，会自动识别继承子类的前缀，自动预装载表（使用createCriteria()函数）
 * 该类是 BaseService 的实现，接口要求见BaseService
 * @see Service4DAOImpl
 * @see BaseService
 */
@Transactional
public class BaseServiceImpl<P> extends Service4DAOImpl<P> implements BaseService {
    /**
     * @see BaseService
     */
    @Override
    public List list(Object... paramAndObjects) {
        Criteria c= createCriteria();

        if(paramAndObjects.length%2 !=0){
            return null;
        }
        for (int i=0;i<paramAndObjects.length;i+=2){
            if(paramAndObjects[i+1] == null){
                c.add(Restrictions.isNull(paramAndObjects[i].toString()));
                continue;
            }
            if(paramAndObjects[i].equals("pagination") && paramAndObjects[i+1] instanceof Pagination) {
                c.setFirstResult(((Pagination)paramAndObjects[i+1]).getStart());
                c.setMaxResults(((Pagination)paramAndObjects[i+1]).getCount());
                continue;
            }
            if(paramAndObjects[i].equals("desc") && paramAndObjects[i+1] instanceof String){
                c.addOrder(Order.desc(paramAndObjects[i+1].toString()));
                continue;
            }
            if(paramAndObjects[i].equals("asc") && paramAndObjects[i+1] instanceof String){
                c.addOrder(Order.asc(paramAndObjects[i+1].toString()));
                continue;
            }
            if(paramAndObjects[i].equals("max") && NumberUtils.isDigits(paramAndObjects[i+1].toString())){
                c.setMaxResults(Integer.valueOf(paramAndObjects[i+1].toString()));
                continue;
            }
            if(paramAndObjects[i].toString().contains("_like") && paramAndObjects[i+1] instanceof String){
                String keyword = "%"+paramAndObjects[i+1].toString()+"%";
                c.add(Restrictions.like(StringUtils.removeEnd(paramAndObjects[i].toString(),"_like"),keyword));
                continue;
            }
            if(paramAndObjects[i].toString().contains("_gt") && NumberUtils.isDigits(paramAndObjects[i+1].toString())){
                c.add(Restrictions.gt(StringUtils.removeEnd(paramAndObjects[i].toString(),"_gt"),paramAndObjects[i+1]));
                continue;
            }
            c.add(Restrictions.eq(paramAndObjects[i].toString(),paramAndObjects[i+1]));
        }
        c.addOrder(Order.desc("id"));
        return c.list();
    }

    @Override
    public Object getOne(Object... paramAndObjects) {
        Object object = null;
        try{
            object = list(paramAndObjects).get(0);
        }catch (Exception ignored){}
        return object;
    }

    /**
     * @see BaseService
     */
    @Override
    public int total(Object... paramAndObjects) {
        Criteria c= createCriteria();
        if(paramAndObjects.length%2 !=0){
            return 0;
        }
        for (int i=0;i<paramAndObjects.length;i+=2){
            if(paramAndObjects[i+1] == null){
                c.add(Restrictions.isNull(paramAndObjects[i].toString()));
            }else{
                c.add(Restrictions.eq(paramAndObjects[i].toString(),paramAndObjects[i+1]));
            }
        }
        c.setProjection(Projections.rowCount());
        return ((Long)c.uniqueResult()).intValue();
    }

    @Override
    public Integer add(Object object) {
        Session session = getSession();
        return (Integer)session.save(object);
    }


    @Override
    public void update(Object object) {
        Session session = getSession();
        session.update(object);
    }

    @Override
    public void update(List objects, String changeFiled) {
        for(Object object:objects){
            try {
                //获取要更新的值的id
                Integer id  = (Integer) object.getClass().getMethod("getId").invoke(object);
                String FiledName = StringUtils.capitalize(changeFiled);
                //获取要更新的值
                Object newValue = object.getClass().getMethod("get"+FiledName).invoke(object);
                //从数据库获取旧对象
                Object objectFromDB = get(id);
                //把新值插入旧对象
                objectFromDB.getClass().getMethod("set"+FiledName,newValue.getClass()).invoke(objectFromDB,newValue);
                //更新旧对象
                update(objectFromDB);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object get(int id) {
        Session session = getSession();
        return session.get(clazz,id);
    }
    @Override
    public Object get(Class clazz,int id) {
        Session session = getSession();
        return session.get(clazz,id);
    }

    /**
     * @see BaseService
     */
    @Override
    public void delete(Object object) {
        Session session = getSession();
        try {
            //获取对象的setDeleteAt方法，插入一个时间
            Method  setDeleteAt = object.getClass().getMethod("setDeleteAt",Date.class);
            setDeleteAt.invoke(object,new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.update(object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void inject(Object parent) {
        Class parentClass = parent.getClass();
        String parentClassName = StringUtils.uncapitalize(parentClass.getSimpleName());
        String thisClassName = StringUtils.capitalize(clazz.getSimpleName());

        try {
            Method setMethod = null;
            try {
                //获取相关方法，直接前面加set后面加s
                setMethod = parentClass.getMethod("set"+thisClassName+"s", List.class);
            }catch (NoSuchMethodException e){
                //没拿到，去y加 ies
                String methodName = "set"+StringUtils.removeEnd(thisClassName,"y")+"ies";
                setMethod = parentClass.getMethod(methodName, List.class);
            }
            setMethod.invoke(parent, list(parentClassName, parent));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inject(List objects) {
        for(Object object:objects){
            inject(object);
        }
    }
}