package tmall.action.front;

import org.apache.struts2.convention.annotation.*;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.Category;
import tmall.pojo.ProductImage;
import tmall.pojo.User;

@Namespace("/")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="homePage",location="/home.jsp"),
                @Result(name="categoryPage",location="/category.jsp"),
                @Result(name="searchPage",location="/search.jsp"),
                @Result(name="productPage",location="/product.jsp")
        }
)
@SuppressWarnings("unchecked")

public class ShowAction extends Action4Auth {
    @Action("index")

    public String home(){
        categories = categoryService.list("desc","recommend","max",13);
        for(Category category:categories){
            category.setProducts(productService.list("category",category,"stock_gt",0));
        }
        return "homePage";
    }

    @Action("product")
    public String product(){
        fill(product);
        productTopImages = productImageService.list("product",product,"type", ProductImage.Type.top);
        productDetailImages = productImageService.list("product",product,"type", ProductImage.Type.detail);
        comments = commentService.list("product",product);
        propertyValues = propertyValueService.list("product",product);
        return "productPage";
    }
    @Action("category")
    public String category(){
        fill(category);
        products = productService.list("category",category,handleSort()[0],handleSort()[1],"stock_gt",0);
        return "categoryPage";
    }
    @Action("search")
    public String search(){
        products = productService.list("name_like",keyword,handleSort()[0],handleSort()[1],"stock_gt",0);
        return "searchPage";
    }
}