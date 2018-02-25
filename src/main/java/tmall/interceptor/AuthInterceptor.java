package tmall.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;
import tmall.interceptor.annotation.Auth;
import tmall.interceptor.verification.SetUser;
import tmall.pojo.User;

import java.lang.reflect.Method;

/**
 * 根据注解来鉴权，不指定类注解权限为 0（unLogin），不指定方法注解，权限为类注解权限，
 * 指定方法注解会覆盖掉类注解权限
 * 暂时的权限级为 ： unLogin(0) user(1) admin(2) superAdmin(3)
 * 定义 @Auth() 注解，value值为最低权限，低于此权限会被禁止访问
 */

public class AuthInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        //获取访问页面的权限
        //获取方法上的注解
        Object action =actionInvocation.getAction();
        String methodName=actionInvocation.getProxy().getMethod();
        Auth authInMethod=action.getClass().getMethod(methodName).getAnnotation(Auth.class);
        //获取类上的注解
        Auth authInClass = action.getClass().getAnnotation(Auth.class);
        //获取Enum方法的ordinal，根据大小来确定该页面权限
        int pageRate = authInClass==null?0:authInClass.value().ordinal();
        pageRate = authInMethod == null?pageRate:authInMethod.value().ordinal();


        //获取用户的权限
        int userRate = 0;
        User user = (User) ActionContext.getContext().getSession().get("user");
        if(user!=null){
            userRate = user.getGroup().ordinal();
        }

        //顺带注入user
        if(user!=null && action instanceof SetUser){
            action.getClass().getMethod("setUser",User.class).invoke(action,user);
        }

        //根据权限决定是否放行
        if(pageRate>userRate){
            if(userRate == 0) {
                ServletActionContext.getResponse().sendRedirect("/login");
                return null;
            }
            return "/noAuth";
        }
        return  actionInvocation.invoke();
    }
}
