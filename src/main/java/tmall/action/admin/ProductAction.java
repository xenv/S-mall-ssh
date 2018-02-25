package tmall.action.admin;

import org.apache.struts2.convention.annotation.*;
import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.Product;
import tmall.pojo.User;
import tmall.util.Pagination;

@Namespace("/admin/product")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="listPage",location="/admin/listProduct.jsp"),
                @Result(name="editPage",location="/admin/editProduct.jsp"),
                @Result(name="list",location = "list?category.id=${product.category.id}",type = "redirect")
        }
)
public class ProductAction extends Action4Auth {
    @Auth(User.Group.admin)
    @Action("list")
    @SuppressWarnings("unchecked")
    public String list(){
        if(pagination == null) {
            pagination = new Pagination();
        }
        pagination.setParam("&category.id="+category.getId());
        pagination.setTotal(productService.total());
        fill(category);
        products = productService.list("pagination",pagination,"category",category);
        return "listPage";
    }

    @Action("add")
    public String add(){
        productService.add(product);
        return "list";
    }

    @Action("delete")
    public String delete(){
        fill(product);
        productService.delete(product);
        return "list";
    }
    @Auth(User.Group.admin)
    @Action("edit")
    public String edit(){
        fill(product);
        return "editPage";
    }

    @Action("update")
    public String update(){
        productService.update(product);
        Product productFromDB= (Product)productService.get(product.getId());
        product.setCreateDate(productFromDB.getCreateDate());
        return "list";
    }

}