package tmall.action.front;

import tmall.interceptor.annotation.Auth;
import tmall.pojo.User;

@Auth(User.Group.unLogin)
public class Action4Auth extends Action4Params {
}
