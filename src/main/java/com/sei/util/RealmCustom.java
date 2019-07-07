package com.sei.util;

import com.sei.bean.Permission;
import com.sei.bean.User;
import com.sei.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RealmCustom extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        //根据用户id拿到用户所拥有的权限
        List<Permission> permissions = null;
        try {
            permissions = userService.queryPermissionByUserid(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (permissions == null || permissions.size() == 0) {
            return null;
        }
        //将角色放入SimpleAuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Permission permission : permissions) {
            String permissionUrl = permission.getUrl();
            if (permissionUrl != null && permissionUrl.length()!=0) {
                info.addStringPermission(permissionUrl);
            }
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String loginacct = (String) authenticationToken.getPrincipal();
        //String password = (String) authenticationToken.getCredentials();
        //以用户账号为盐值
        //String result = new SimpleHash("MD5", "123456", bytes, 3).toString();
        //System.out.println(result);

        User user = null;
        try {
            user = userService.queryAccount(loginacct);
            if (user == null) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUserpswd(), ByteSource.Util.bytes(loginacct), getName());
        return info;
    }
    //清缓存
    public void clearCache() {
        Subject subject = SecurityUtils.getSubject();
        super.clearCache(subject.getPrincipals());
    }
}
