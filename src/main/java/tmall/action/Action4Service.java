package tmall.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tmall.pojo.Config;
import tmall.pojo.Order;
import tmall.service.*;

import javax.xml.ws.Action;
import java.util.List;

/**
 * 获取各种服务类
 */
public class Action4Service extends Action4Pojo{
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected PropertyService propertyService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected ProductImageService productImageService;
    @Autowired
    protected PropertyValueService propertyValueService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected ConfigService configService;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected CartItemService cartItemService;
    @Autowired
    protected OrderItemService orderItemService;


    /**
     * 自动调用对应的的方法填充pojo对象，方便 update 和 delete 使用
     * @param o 只有id的原始对象
     */
    @SuppressWarnings("unchecked")
    public void fill(Object o){
        try{
            Class clazz = o.getClass();
            int id = (Integer)clazz.getMethod("getId").invoke(o);
            //已经抽象化，哪个服务出来都是一样的，都是那个对象的属性
            Object filledPOJO = categoryService.get(clazz,id);
            String POJOName = clazz.getSimpleName();
            //调用父类的 set*** ，先从本类的类对象获取set方法，再把已经填充好的pojo放过去，就把本类的pojo填充好了
            this.getClass().getMethod("set"+ StringUtils.capitalize(POJOName),clazz).invoke(this,filledPOJO);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}