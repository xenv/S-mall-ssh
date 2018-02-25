package tmall.interceptor;



import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import com.opensymphony.xwork2.interceptor.PreResultListener;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import tmall.service.ConfigService;


import javax.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * 该拦截器读取Config设置，并且负责注入到jsp中
 */

public class ConfigInterceptor extends AbstractInterceptor {
    @Autowired
    ConfigService configService;
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        HttpServletRequest request = (HttpServletRequest) actionInvocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
        Object action = actionInvocation.getAction();
        Map<String,String> config = configService.map();
        //配置首页SEO参数
        if(action.getClass().getSimpleName().equals("ShowAction")&&actionInvocation.getProxy().getMethod().equals("home")){
            String indexTitle = config.get("index_title");
            String indexKeyword = config.get("index_keyword");
            String indexDescription = config.get("index_description");
            request.setAttribute("SEOTitle",indexTitle);
            request.setAttribute("keywords",indexKeyword);
            request.setAttribute("description",indexDescription);
        }
        request.setAttribute("website_name",config.get("website_name"));
        request.setAttribute("productImgDir",config.get("path_product_img"));
        request.setAttribute("categoryImgDir",config.get("path_category_img"));
        return  actionInvocation.invoke();

    }
}
