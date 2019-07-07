package com.sei.dao;

import com.sei.bean.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {
    List<Role> queryRole(@Param("queryText") String queryText) throws Exception;

    List<Role> queryRoleById(Integer id) throws Exception;

    List<Integer> queryRoleIdById(Integer id) throws Exception;

    void addRole(String name) throws Exception;

    void deleteRoleById(Integer id) throws Exception;

    void deleteRoleUserByRoleId(Integer roleid) throws Exception;

    void deleteRolePermissionByRoleId(Integer roleid) throws Exception;
}
