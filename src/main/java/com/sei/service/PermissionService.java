package com.sei.service;

import com.sei.bean.Icon;
import com.sei.bean.Permission;
import com.sei.util.Data;

import java.util.List;

public interface PermissionService {
    List<Permission> queryAllPermission() throws Exception;

    void addPermission(Permission permission) throws Exception;

    Permission queryPermissionById(Integer id) throws Exception;

    void updatePermission(Permission permission) throws Exception;

    List<Icon> queryIcon() throws Exception;

    void deletePermission(Integer id) throws Exception;

    List<Integer> queryPermissionIdByRoleId(Integer id) throws Exception;

    void deletePermissionIdByRoleId(Integer roleid) throws Exception;

    void addPermissionIdByRoleId(Integer roleid,Data data) throws Exception;

    void deleteRoleIdByPermissionId(Integer id) throws Exception;
}
