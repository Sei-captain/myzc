package com.sei.dao;

import com.sei.bean.Permission;
import com.sei.bean.ResetPassword;
import com.sei.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User queryAccount(String loginacct) throws Exception;

    List<User> queryUser(@Param("queryText") String queryText) throws Exception;

    int addUser(User user) throws Exception;

    int deleteUser(Integer id) throws Exception;

    User queryUserById(Integer id) throws Exception;

    int doUpdateUser(User user) throws Exception;

    void addUserRole(@Param("userid") Integer userid,@Param("roleid") Integer roleid) throws Exception;

    void deleteUserRole(@Param("userid") Integer userid,@Param("roleid") Integer roleid) throws Exception;

    void deleteRoleIdByUserId(Integer userid) throws Exception;

    List<Permission> queryPermissionByUserid(Integer id) throws Exception;

    @Insert("insert into t_resetpassword(loginacct,failuretime,mkey,status) values(#{loginacct},#{failuretime},#{mkey},#{status})")
    void addResetPassword(ResetPassword resetPassword) throws Exception;
    @Select("select * from t_resetpassword where mkey=#{mkey} and loginacct=#{loginacct} and status=#{status}")
    ResetPassword queryResetPassword(ResetPassword resetPassword) throws Exception;
    @Update("update t_user set userpswd=#{userpswd} where loginacct=#{loginacct}")
    void updateUserPassword(User user);
    @Update("update t_resetpassword set status='1' where loginacct=#{loginacct} and mkey=#{mkey}")
    void updateResetPasswordStatus(@Param("loginacct") String loginacct, @Param("mkey") String mkey);
    @Select("select * from t_resetpassword where loginacct=#{loginacct} and status='0'")
    ResetPassword queryResetPasswordByAccount(String loginacct) throws Exception;
    @Update("update t_resetpassword set mkey=#{mkey},failuretime=#{failuretime} where loginacct=#{loginacct} and status='0'")
    void updateResetPassword(ResetPassword resetPassword) throws Exception;
}
