package tmall.action;

import tmall.pojo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理pojo的注入和取出
 */
public class Action4Pojo extends Action4Pagination {
 
    protected Category category;
     
    protected List<Category> categories;

    protected Property property;

    protected ProductImage productImage;

    protected List<Property> properties;

    protected Product product;

    protected List<Product> products;

    protected List<ProductImage> productTopImages;
    protected List<ProductImage> productDetailImages;
    protected ProductImage productCoverImage;

    protected List<PropertyValue> propertyValues;

    protected User user;

    protected List<User> users;
    protected Order order;
    protected List<Order> orders;

    protected List<Config> configs;
    protected CartItem cartItem;
    protected List<Comment> comments;

    protected List<CartItem> cartItems;
    protected OrderItem orderItem;
    protected Comment comment;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public ProductImage getProductCoverImage() {
        return productCoverImage;
    }

    public void setProductCoverImage(ProductImage productCoverImage) {
        this.productCoverImage = productCoverImage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<ProductImage> getProductTopImages() {
        return productTopImages;
    }

    public void setProductTopImages(List<ProductImage> productTopImages) {
        this.productTopImages = productTopImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }
}