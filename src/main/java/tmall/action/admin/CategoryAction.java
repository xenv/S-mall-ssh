package tmall.action.admin;

import org.apache.struts2.convention.annotation.*;

import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.User;
import tmall.util.FileUtil;
import tmall.util.Pagination;

@Namespace("/admin/category")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="listPage",location="/admin/listCategory.jsp"),
                @Result(name="editPage",location="/admin/editCategory.jsp"),
                @Result(name="list",location = "list",type = "redirect")
        }
)
public class CategoryAction extends Action4Auth {
    @Action("list")
    @Auth(User.Group.admin)
    @SuppressWarnings("unchecked")
    public String list(){
        if(pagination == null) {
            pagination = new Pagination();
        }
        pagination.setTotal(categoryService.total());
        categories = categoryService.list("pagination",pagination,"desc","recommend");
        return "listPage";
    }

    @Action("add")
    public String add(){
        categoryService.add(category);
        FileUtil.saveImg(img,"category",category.getId());
        return "list";
    }

    @Action("delete")
    public String delete(){
        fill(category);
        categoryService.delete(category);
        return "list";
    }
    @Action("edit")
    @Auth(User.Group.admin)
    public String edit(){
        fill(category);
        return "editPage";
    }

    @Action("update")
    public String update(){
        categoryService.update(category);
        if(img!=null){
            FileUtil.saveImg(img,"category",category.getId());
        }
        return "list";
    }

}