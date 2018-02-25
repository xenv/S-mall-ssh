package tmall.action.admin;

import org.apache.struts2.convention.annotation.*;
import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.Product;
import tmall.pojo.ProductImage;
import tmall.pojo.User;
import tmall.service.ProductImageService;
import tmall.util.FileUtil;

@Namespace("/admin/product/image")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="listPage",location="/admin/listProductImage.jsp"),
                @Result(name="list",location = "list?product.id=${productImage.product.id}",type = "redirect")
        }
)
public class ProductImageAction extends Action4Auth {
    @Auth(User.Group.admin)
    @Action("list")
    @SuppressWarnings("unchecked")
    public String list(){
        fill(product);
        productCoverImage = product.getImage();
        productTopImages = productImageService.list("product",product,"type",ProductImage.Type.top);
        productDetailImages = productImageService.list("product",product,"type",ProductImage.Type.detail);
        return "listPage";
    }

    @Action("add")
    public String add(){
        productImageService.add(productImage);
        FileUtil.saveImg(img,"product",productImage.getId());
        //判断是否是封面图片
        if(productImage.getType()==ProductImage.Type.cover){
            product = productImage.getProduct();
            fill(product);
            product.setImage(productImage);
            productService.update(product);
        }
        return "list";
    }

    @Action("delete")
    public String delete(){
        fill(productImage);
        productImageService.delete(productImage);
        return "list";
    }


}