package tmall.service;

import java.util.List;
/**
 *
 * 服务层抽象类，封装了大部分的服务层代码，普通Service需继承本类使用
 * 查询数据库表会根据不同的Service识别对应的类
 * 例如，CategoryService对象使用本类方法，会自动在Category的对应的表中查询，无需人工指定
 * list方法和total方法的调用比较特别，需要注意
 */
public interface BaseService extends Service4DAO{
    /**
     *
     * @param object 要添加的数据
     * @return 影响的行数（可判断有无插进）
     */
    public Integer add(Object object);

    /**
     *
     * @param object 要更新的数据
     */
    public void update(Object object);

    /**
     * 更新一个列表，适用于一次性更改多个对象，并且每个对象只传入一个新值的情况
     * 例：configService.update(list,"value")，
     * 将会逐个遍历list的每个config对象，从数据库读取原有数据，将value属性的新值插入原有数据，并更新
     * @param objects 一个对象列表
     * @param changeFiled 新值的字段
     */
    public void update(List objects,String changeFiled);
    /**
     * 本方法会自动调用该对象的setDeleteAt方法，向数据库中插入deleteAt日期并更新，不会真的删除数据库中的数据
     * @param object 要删除的对象
     */
    public void delete(Object object);

    /**
     *
     * @param clazz 指定一个表，形式为pojo的类对象
     * @param id 要获取的id
     * @return 指定表，指定id的一行
     */
    public Object get(Class clazz,int id);


    /**
     *
     * @param id 数据库中的id
     * @return 自动识别表的id的一行数据
     */
    public Object get(int id);

    /**
     * @param paramAndObjects (propertyName,object,propertyName,object....)
     *                例子：productService调用 list("category",category) ,可获取所有该分类的 products
     *                可堆叠使用，UserService 调用 list("username","xxx","password","xxx")可获取符合条件的 user
     *                特殊object：
     *                null 值识别为 isnull
     *                特殊 propertyName:
     *                pagination(,paginationObject) 会自动添加分页输出
     *                order(,String)      会自动添加order排序方式
     * @return 加查询约束的列表
     * @see tmall.service.impl.BaseServiceImpl
     */
    public List list(Object... paramAndObjects);

    /**
     * @param paramAndObjects 同上
     * @return 只返回第一个
     */
    public Object getOne(Object... paramAndObjects);
    /**
     * @param paramAndObjects (propertyName,object,propertyName,object....)
     * @return 加查询约束的总数，分页用，第一个可以是分页，自动识别
     * 特殊object: null 值自动添加该属性 isnull
     */
    public int total(Object... paramAndObjects);

    /**
     * 注入懒加载的一对多数据
     * 例如：在action调用 productService.inject(category) ，
     * 本方法会自动尝试调用 category.setProducts方法，
     * 自动注入相关的懒加载数据
     * @param object 待注入的数据
     */
    public void inject(Object object);

    /**
     * 注入对象列表的所有对象的懒加载数据
     * @param objects 待填充的数据的列表
     */
    public void inject(List objects);


}