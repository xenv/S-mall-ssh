package tmall.service.impl;

import org.springframework.stereotype.Service;
import tmall.pojo.User;
import tmall.service.UserService;
import tmall.util.PasswordUtil;


@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Override
    public boolean isExist(String username) {
        return !list("name",username).isEmpty();
    }

    @Override
    public Integer add(User user) {
        String rawPassword = user.getPassword();
        user.setPassword(PasswordUtil.encryptPassword(rawPassword));
        return super.add(user);
    }

    @Override
    public User get(String name, String password) {
        return  (User)getOne("name",name,"password",PasswordUtil.encryptPassword(password));
    }
}