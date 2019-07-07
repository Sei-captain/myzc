package com.sei.service;

import com.sei.bean.Permission;
import com.sei.bean.ResetPassword;
import com.sei.bean.User;
import com.sei.util.Data;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {
    User queryLogin(String loginacct, String password) throws Exception;

    User queryAccount(String loginacct) throws Exception;

    List<User> queryUser(String queryText) throws Exception;

    int addUser(User user) throws Exception;

    int deleteUser(Integer[] ids) throws Exception;

    User queryUserById(Integer id) throws Exception;

    int doUpdateUser(User user) throws Exception;

    void addUserRole(Integer userid, Data data) throws Exception;

    void deleteUserRole(Integer userid,Data data) throws Exception;

    void deleteRoleIdByUserId(Integer[] ids) throws Exception;

    List<Permission> queryPermissionByUserid(Integer id) throws Exception;

    void addResetPassword(ResetPassword resetPassword) throws Exception;

    ResetPassword queryResetPassword(ResetPassword resetPassword) throws Exception;

    void updateUserPassword(User user) throws Exception;

    void updateResetPasswordStatus(String loginacct, String mkey) throws Exception;

    ResetPassword queryResetPasswordByAccount(String loginacct) throws Exception;

    void updateResetPassword(ResetPassword resetPassword) throws Exception;
}
