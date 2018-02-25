package tmall.action.admin;

import org.apache.struts2.convention.annotation.*;
import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.User;
import tmall.util.Pagination;

@Namespace("/admin/property")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="listPage",location="/admin/listProperty.jsp"),
                @Result(name="editPage",location="/admin/editProperty.jsp"),
                @Result(name="list",location = "list?category.id=${property.category.id}",type = "redirect")
        }
)
public class PropertyAction extends Action4Auth {
    @Auth(User.Group.admin)
    @Action("list")
    @SuppressWarnings("unchecked")
    public String list(){
        if(pagination == null) {
            pagination = new Pagination();
        }
        pagination.setTotal(propertyService.total("category",category));
        pagination.setParam("&category.id="+category.getId());
        properties = propertyService.list("pagination",pagination,"category",category);
        fill(category);
        return "listPage";
    }

    @Action("add")
    public String add(){
        propertyService.add(property);
        return "list";
    }

    @Action("delete")
    public String delete(){
        fill(property);
        propertyService.delete(property);
        return "list";
    }
    @Auth(User.Group.admin)
    @Action("edit")
    public String edit(){
        fill(property);
        return "editPage";
    }

    @Action("update")
    public String update(){
        propertyService.update(property);
        return "list";
    }

}