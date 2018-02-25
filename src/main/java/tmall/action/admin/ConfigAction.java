package tmall.action.admin;

import org.apache.struts2.convention.annotation.*;
import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.Config;
import tmall.pojo.PropertyValue;
import tmall.pojo.User;

import java.util.List;

@Namespace("/admin/config")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="editPage",location="/admin/editConfig.jsp"),
                @Result(name="edit",location = "edit",type = "redirect")
        }
)
public class ConfigAction extends Action4Auth {

    @Action("edit")
    @Auth(User.Group.admin)
    public String edit(){
        configs = configService.list("desc","rate");
        return "editPage";
    }

    @Action("update")
    public String update(){
        configService.update(configs,"value");
        return "edit";
    }

}