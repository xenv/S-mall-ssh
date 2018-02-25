package tmall.action.admin;

import tmall.action.Action4Service;
import tmall.interceptor.annotation.Auth;
import tmall.pojo.User;

@Auth(User.Group.superAdmin)
public class Action4Auth extends Action4Service {
}
