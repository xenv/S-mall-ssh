package tmall.action.admin;

import org.apache.struts2.convention.annotation.*;
import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.User;
import tmall.util.FileUtil;
import tmall.util.Pagination;

@Namespace("/admin/user")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="listPage",location="/admin/listUser.jsp")
        }
)
public class UserAction extends Action4Auth {
    @Auth(User.Group.admin)
    @Action("list")
    @SuppressWarnings("unchecked")
    public String list(){
        if(pagination == null) {
            pagination = new Pagination();
        }
        pagination.setTotal(userService.total());
        users = userService.list("pagination",pagination);
        return "listPage";
    }
}