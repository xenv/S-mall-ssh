package tmall.action.front;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.web.util.HtmlUtils;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.User;

@Namespace("/")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="loginPage",location="/login.jsp"),
                @Result(name="refer",location="${refer}", type="redirect"),
                @Result(name="registerPage",location="/register.jsp"),
                @Result(name="registerSuccessPage",location="/registerSuccess.jsp"),
                @Result(name="msgPage",location="/msg.jsp")
        }
)
public class UserAction extends Action4Auth {
    @Action("register")
    public String register(){
        return "registerPage";
    }
    @Action("registerAdd")
    public String registerAdd(){
        //判断用户名合法性
        if(!HtmlUtils.htmlEscape(user.getName()).equals(user.getName())){
            msg = "用户名含有特殊字符，无法注册，请重新输入";
            return "registerPage";
        }
        if(userService.isExist(user.getName())){
            msg = "用户名已存在，无法注册，请重新输入";
            return "registerPage";
        }
        user.setGroup(User.Group.user);
        userService.add(user);
        return "registerSuccessPage";
    }
    @Action("login")
    public String login(){
        return "loginPage";
    }
    @Action("loginIn")
    public String loginIn(){
        user = userService.get(user.getName(),user.getPassword());
        if(user==null){
            msg = "用户名密码错误，请重试";
            return "loginPage";
        }
        ActionContext.getContext().getSession().put("user",user);
        return "refer";
    }
    @Action("logout")
    public String logout(){
        ActionContext.getContext().getSession().remove("user");
        return "refer";
    }
    @Action("checkLogin")
    public String checkLogin(){
        msg = ActionContext.getContext().getSession().get("user")!=null?"success":"fail";
        return "msgPage";
    }
    @Auth(User.Group.unLogin)
    @Action("noAuth")
    public String noAuth(){
        msg = "没有权限访问此页面";
        return "msgPage";
    }
}