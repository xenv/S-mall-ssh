package tmall.interceptor.verification;

import tmall.pojo.User;

/**
 * 验证接口，确保拦截器注入的时候不会出错
 * 实现该类，会自动注入 user
 */
public interface SetUser {
    public void setUser(User user);
}
