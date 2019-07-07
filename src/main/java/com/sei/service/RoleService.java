package com.sei.service;

import com.sei.bean.Role;

import java.util.List;

public interface RoleService {
    List<Role> queryRole(String queryText) throws Exception;

    List<Role> queryRoleById(Integer id) throws Exception;

    List<Integer> queryRoleIdById(Integer id) throws Exception;

    void addRole(String name) throws Exception;

    void deleteRole(Integer[] ids) throws Exception;

    void deleteRoleUserByRoleId(Integer[] ids) throws Exception;

    void deleteRolePermissionByRoleId(Integer[] ids) throws Exception;
}
