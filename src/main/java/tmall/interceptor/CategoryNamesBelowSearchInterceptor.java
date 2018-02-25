package tmall.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import tmall.interceptor.annotation.Auth;
import tmall.interceptor.verification.SetUser;
import tmall.pojo.User;
import tmall.service.CategoryService;

import javax.servlet.ServletContext;

/**
 * 自动插入搜索栏下面的Category列表，一分钟更新一次
 */

public class CategoryNamesBelowSearchInterceptor extends AbstractInterceptor {
    @Autowired
    CategoryService categoryService;
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ServletContext servletContext= (ServletContext) actionInvocation.getInvocationContext().get(StrutsStatics.SERVLET_CONTEXT);
        Long oldTime = (Long)servletContext.getAttribute("csTimeOut");
        if(oldTime == null || System.currentTimeMillis()>oldTime) {
            servletContext.setAttribute("cs", categoryService.list("desc", "recommend", "max", 7));
            //1分钟内，全站用户只统一获取一次
            servletContext.setAttribute("csTimeOut", System.currentTimeMillis() + 60 * 1000);
        }
        return  actionInvocation.invoke();
    }
}
