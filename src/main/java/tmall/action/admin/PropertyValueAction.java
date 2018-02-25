package tmall.action.admin;

import org.apache.struts2.convention.annotation.*;
import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.PropertyValue;
import tmall.pojo.User;

import java.util.List;

@Namespace("/admin/product/propertyValue")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="editPage",location="/admin/editPropertyValue.jsp"),
                @Result(name="list",location = "../list?category.id=${product.category.id}",type = "redirect")
        }
)
public class PropertyValueAction extends Action4Auth {
    @Auth(User.Group.admin)
    @Action("edit")
    public String edit(){
        fill(product);
        propertyValueService.init(product);
        propertyValues = propertyValueService.list("product",product);
        return "editPage";
    }

    @Action("update")
    public String update(){
        fill(product);
        propertyValueService.update(propertyValues,"value");
        return "list";
    }

}