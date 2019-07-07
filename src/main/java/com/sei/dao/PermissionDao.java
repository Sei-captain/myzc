package com.sei.dao;

import com.sei.bean.Icon;
import com.sei.bean.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    List<Permission> queryAllPermission() throws Exception;

    void addPermission(Permission permission) throws Exception;

    Permission queryPermissionById(Integer id) throws Exception;

    void updatePermission(Permission permission) throws Exception;

    List<Icon> queryIcon() throws Exception;

    void deletePermission(Integer id) throws Exception;

    List<Integer> queryPermissionIdByRoleId(Integer id) throws Exception;

    void deletePermissionIdByRoleId(Integer roleid) throws Exception;

    void addPermissionIdByRoleId(@Param("roleid") Integer roleid, @Param("permissionid") Integer permissionid);

    void deleteRoleIdByPermissionId(Integer id) throws Exception;
}
