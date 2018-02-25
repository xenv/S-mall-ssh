package tmall.action.front;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.*;
import tmall.interceptor.annotation.Auth;
import tmall.interceptor.verification.SetUser;
import tmall.pojo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Auth(User.Group.user)
@Namespace("/")
@ParentPackage("basic-struts")
@Results(
        {
                @Result(name="buyPage",location="/buy.jsp"),
                @Result(name="cartPage",location="/cart.jsp"),
                @Result(name="myOrderPage",location="/myOrder.jsp"),
                @Result(name="myOrder",location="/myOrder",type="redirect"),
                @Result(name="payPage",location="/pay.jsp"),
                @Result(name="payedPage",location="/payed.jsp"),
                @Result(name="confirmPayPage",location="/confirmPay.jsp"),
                @Result(name="confirmedPage",location="/confirmed.jsp"),
                @Result(name="commentPage",location="/comment.jsp"),
                @Result(name="buy",location="/buy?ciids=-1",type="redirect"),
                @Result(name="pay",location="/pay?order.id=${order.id}",type="redirect"),
                @Result(name="msgPage",location="/msg.jsp")
        }
)

public class OrderAction extends Action4Auth implements SetUser {
    //购物车相关Action
    @Action("addCart")
    public String addCart(){
        fill(product);
        //获取原来就在购物车的数据
        CartItem cartItem = (CartItem) cartItemService.getOne("user",user,"product",product);
        Boolean isInDB = cartItem != null;
        //判断是否超出库存

        if(isInDB){
            num += cartItem.getNumber();
        }else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setUser(user);
        }

        if(num>product.getStock()){
            msg = "OutOfStock";
            return "msgPage";
        }

        cartItem.setNumber(num);
        cartItem.setSum(product.getNowPrice().subtract(new BigDecimal(num)));

        if(isInDB){
            cartItemService.update(cartItem);
        }else{
            cartItemService.add(cartItem);
        }

        msg = "success";
        return "msgPage";
    }
    @Action("cart")
    public String cart(){
        cartItems = cartItemService.list("user",user);
        return "cartPage";
    }
    @Action("changeCartNum")
    public String changeCartNum(){
        fill(cartItem);
        if(cartItem.getUser().getId()==user.getId()&&cartItem.getProduct().getStock()>num){
            cartItem.setNumber(num);
            cartItem.setSum(product.getNowPrice().subtract(new BigDecimal(num)));
            cartItemService.update(cartItem);
            msg = "success";
            return "msgPage";
        }
        msg = "fail";
        return "msgPage";
    }
    @Action("deleteCartItem")
    public String deleteCartItem(){
        fill(cartItem);
        if(cartItem.getUser().getId()==user.getId()){
            cartItemService.delete(cartItem);
            msg = "success";
            return "msgPage";
        }
        msg = "fail";
        return "msgPage";
    }
    @Action("cartNumber")
    public String cartNumber() {
        int number = cartItemService.total("user",user);
        msg = String.valueOf(number);
        return "msgPage";
    }
    @Action("buyOne")
    public String buyOne(){
        fill(product);
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setNumber(num);
        cartItem.setSum(product.getNowPrice().multiply(new BigDecimal(num)));
        cartItem.setId(-1);
        ActionContext.getContext().getSession().put("tempCartItem", cartItem);
        return "buy";
    }
    @Action("buy")
    public String buy(){
        cartItems = new ArrayList<>();
        for(Integer ciid:ciids){
            if(ciid == -1){
                //由buyOne跳转而来
                cartItem = (CartItem) ActionContext.getContext().getSession().get("tempCartItem");
            }else{
                //由购物车跳转而来
                cartItem = (CartItem) cartItemService.get(ciid);
            }
            if(cartItem.getUser().getId() == user.getId()){
                totalNum += cartItem.getNumber();
                sum = sum.add(cartItem.getSum());
                cartItems.add(cartItem);
            }
        }
        ActionContext.getContext().getSession().put("cartItems", cartItems);
        return "buyPage";
    }

    @Action("createOrder")
    public String createOrder(){
        cartItems = (List<CartItem>) ActionContext.getContext().getSession().get("cartItems");
        order.setUser(user);
        orderService.createOrder(order,cartItems);
        return "pay";
    }
    @Action("pay")
    public String pay(){
        fill(order);
        if(order.getUser().getId() != user.getId()){
            msg = "fail";
            return "msgPage";
        }
        return "payPage";
    }
    @Action("payed")
    public String payed(){
        fill(order);
        if(order.getUser().getId() != user.getId()){
            msg = "fail";
            return "msgPage";
        }
        order.setStatus(Order.Status.waitDeliver);
        order.setPayDate(new Date());
        orderService.update(order);
        return "payedPage";
    }
    @Action("myOrder")
    public String myOrder(){
        orders = orderService.list("user",user);
        return "myOrderPage";
    }
    @Action("confirmPay")
    public String confirmPay(){
        fill(order);
        if(order.getUser().getId() != user.getId()){
            msg = "fail";
            return "msgPage";
        }
        return "confirmPayPage";
    }
    @Action("confirmed")
    public String confirmed(){
        fill(order);
        if(order.getUser().getId() != user.getId()){
            msg = "fail";
            return "msgPage";
        }
        order.setStatus(Order.Status.waitComment);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return "confirmedPage";
    }
    @Action("deleteOrder")
    public String deleteOrder(){
        fill(order);
        if(order.getUser().getId() == user.getId()){
            order.setStatus(Order.Status.deleted);
            orderService.update(order);
            msg = "success";
        }else{
            msg = "fail";
        }
        return "msgPage";
    }
    @Action("deliver")
    public String deliver(){
        fill(order);
        if(order.getUser().getId() != user.getId()) {
            msg = "fail";
            return "msgPage";
        }
        order.setStatus(Order.Status.waitConfirm);
        order.setDeliverDate(new Date());
        orderService.update(order);
        return "myOrder";
    }
    @Action("comment")
    public String comment(){
        fill(orderItem);
        if(orderItem.getOrder().getUser().getId() != user.getId()) {
            msg = "fail";
            return "msgPage";
        }
        return "commentPage";
    }
    @Action("addComment")
    public String addComment(){
        fill(orderItem);
        Order order = orderItem.getOrder();
        if(order.getUser().getId() != user.getId()) {
            msg = "fail";
            return "msgPage";
        }
        comment.setProduct(orderItem.getProduct());
        comment.setUser(user);
        comment.setCreateDate(new Date());
        commentService.add(comment);
        orderItem.setComment(comment);
        orderItemService.update(orderItem);
        Product product = cartItem.getProduct();
        product.setCommentCount(product.getCommentCount()+1);
        productService.update(product);
        if(commentService.checkFinishComment(order)){
            order.setStatus(Order.Status.finish);
            orderService.update(order);
        }
        return "myOrder";
    }

}
