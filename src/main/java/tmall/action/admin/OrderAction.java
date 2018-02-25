package tmall.action.admin;

import org.apache.struts2.convention.annotation.*;
import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.Order;
import tmall.pojo.User;
import tmall.util.Pagination;

import java.util.Date;

@Namespace("/admin/order")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="listPage",location="/admin/listOrder.jsp"),
                @Result(name="list",location="list",type = "redirect")
        }
)
public class OrderAction extends Action4Auth {
    @Auth(User.Group.admin)
    @Action("list")
    @SuppressWarnings("unchecked")
    public String list(){
        if(pagination == null) {
            pagination = new Pagination();
        }
        pagination.setTotal(orderService.total());
        orders = orderService.list("pagination",pagination);
        return "listPage";
    }
    @Auth(User.Group.admin)
    @Action("deliver")
    public String deliver(){
        fill(order);
        order.setStatus(Order.Status.waitComment);
        order.setDeliverDate(new Date());
        orderService.update(order);
        return "list";
    }
}