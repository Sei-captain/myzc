package com.sei.service.impl;

import com.sei.bean.Permission;
import com.sei.bean.ResetPassword;
import com.sei.bean.User;
import com.sei.dao.UserDao;
import com.sei.service.UserService;
import com.sei.util.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User queryLogin(String loginacct, String password) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginacct,password);
        try {
            subject.login(token);
            User user = userDao.queryAccount(loginacct);
            return user;
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public User queryAccount(String loginacct) throws Exception {
        return userDao.queryAccount(loginacct);
    }

    @Override
    public List<User> queryUser(String queryText) throws Exception {

        List<User> userList = userDao.queryUser(queryText);
        return userList;
    }

    @Override
    public User queryUserById(Integer id) throws Exception {
        return userDao.queryUserById(id);
    }

    @Override
    public int addUser(User user) throws Exception {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(date);
        user.setCreatetime(createTime);
        String md5 = new SimpleHash("MD5", user.getUserpswd(), ByteSource.Util.bytes(user.getLoginacct()), 3).toString();
        user.setUserpswd(md5);
        return userDao.addUser(user);
    }

    @Override
    public void addUserRole(Integer userid, Data data) throws Exception {
        List<Integer> roleids = data.getIds();
        for (Integer roleid : roleids) {
            userDao.addUserRole(userid, roleid);
        }
    }

    @Override
    public void deleteUserRole(Integer userid, Data data) throws Exception {
        List<Integer> roleids = data.getIds();
        for (Integer roleid : roleids) {
            userDao.deleteUserRole(userid, roleid);
        }
    }

    @Override
    public int doUpdateUser(User user) throws Exception {
        return userDao.doUpdateUser(user);
    }


    @Override
    public int deleteUser(Integer[] ids) throws Exception {
        int totalCount = 0;
        for (Integer id : ids) {
            int count = userDao.deleteUser(id);
            totalCount += count;
        }
        if (totalCount != ids.length) {
            throw new RuntimeException("批量删除失败");
        }
        return totalCount;
    }

    @Override
    public void deleteRoleIdByUserId(Integer[] ids) throws Exception {
        for (Integer userid : ids) {
            userDao.deleteRoleIdByUserId(userid);

        }
    }

    @Override
    public List<Permission> queryPermissionByUserid(Integer id) throws Exception {
        return userDao.queryPermissionByUserid(id);
    }

    @Override
    public void addResetPassword(ResetPassword resetPassword) throws Exception {
        userDao.addResetPassword(resetPassword);
    }

    @Override
    public ResetPassword queryResetPassword(ResetPassword resetPassword) throws Exception {
        return userDao.queryResetPassword(resetPassword);
    }

    @Override
    public void updateUserPassword(User user) throws Exception {
        userDao.updateUserPassword(user);
    }

    @Override
    public void updateResetPasswordStatus(String loginacct, String mkey) {
        userDao.updateResetPasswordStatus(loginacct,mkey);
    }

    @Override
    public ResetPassword queryResetPasswordByAccount(String loginacct) throws Exception {
        return userDao.queryResetPasswordByAccount(loginacct);
    }

    @Override
    public void updateResetPassword(ResetPassword resetPassword) throws Exception {
        userDao.updateResetPassword(resetPassword);
    }
}
