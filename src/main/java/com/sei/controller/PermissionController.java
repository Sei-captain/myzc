package com.sei.controller;

import com.sei.bean.Icon;
import com.sei.bean.Permission;
import com.sei.service.PermissionService;
import com.sei.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
//@RequiresPermissions("permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;


    @RequestMapping("/index")
    public String index() {
        return "permission/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "permission/add";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(Permission permission) {
        Result result = new Result();
        try {
            permissionService.addPermission(permission);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("添加许可树失败！");
        }
        return result;
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Map map) throws Exception {
        Permission permission = permissionService.queryPermissionById(id);
        map.put("permission", permission);
        return "permission/update";
    }

    @RequestMapping("/updatePermission")
    @ResponseBody
    public Object updatePermission(Permission permission) {
        Result result = new Result();
        try {
            permissionService.updatePermission(permission);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("修改许可树失败！");
        }
        return result;
    }
    @RequestMapping("/deletePermission")
    @ResponseBody
    public Object deletePermission(Integer id){
        Result result = new Result();
        try {
            permissionService.deleteRoleIdByPermissionId(id);
            permissionService.deletePermission(id);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        Result result = new Result();
        try {
            List<Permission> root = new ArrayList<Permission>();
            List<Permission> permissions = permissionService.queryAllPermission();
            Map<Integer, Permission> map = new HashMap<>();//100

            for (Permission permission : permissions) {
                map.put(permission.getId(), permission);
            }
            for (Permission permission : permissions) { //100
                //通过子查找父
                if (permission.getPid() == 0) {
                    root.add(permission);
                } else {
                    //父节点
                    Permission parent = map.get(permission.getPid());
                    parent.getChildren().add(permission);
                }
            }
            result.setFlag(true);
            result.setData(root);

        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage("加载许可树数据失败!");
        }
        return result;
    }

    @RequestMapping("/icon")
    @ResponseBody
    public Object icod() {
        Result result = new Result();
        try {
            List<Icon> iconList = permissionService.queryIcon();
            result.setFlag(true);
            result.setData(iconList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("获取图标失败！");
        }
        return result;
    }

}
