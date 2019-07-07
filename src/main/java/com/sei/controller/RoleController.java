package com.sei.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sei.bean.Permission;
import com.sei.bean.Role;
import com.sei.service.PermissionService;
import com.sei.service.RoleService;
import com.sei.util.Data;
import com.sei.util.RealmCustom;
import com.sei.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
//@RequiresPermissions("role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RealmCustom realmCustom;

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              String queryText) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleService.queryRole(queryText);
        PageInfo pageInfo = new PageInfo(roleList);
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("queryText", queryText);
        mv.setViewName("role/index");
        return mv;
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "role/add";
    }

    @RequestMapping("/toAssignPermission")
    public String toAssignPermission( ) throws Exception {
        return "role/assignPermission";

    }

    @RequestMapping("/loadDataAsync")
    @ResponseBody
    public Object loadDataAsync(Integer roleid) throws Exception {
        List<Permission> permissions = permissionService.queryAllPermission();
        List<Integer> permissionIds = permissionService.queryPermissionIdByRoleId(roleid);
        List<Permission> root = new ArrayList<Permission>();
        Map<Integer, Permission> map = new HashMap<>();//100

        for (Permission permission : permissions) {
            map.put(permission.getId(), permission);
            if (permissionIds.contains(permission.getId())){
                permission.setChecked(true);
            }
        }
        for (Permission permission : permissions) { //100
            //通过子查找父，0为根节点
            if (permission.getPid() == 0) {
                root.add(permission);
            } else {
                //父节点
                Permission parent = map.get(permission.getPid());
                parent.getChildren().add(permission);
            }
        }
        return root;
    }
    @RequestMapping("/doAssignPermission")
    @ResponseBody
    public Object doAssignPermission(Integer roleid, Data data) throws Exception {
        Result result= new Result();
        try {
            permissionService.deletePermissionIdByRoleId(roleid);
            permissionService.addPermissionIdByRoleId(roleid,data);
            realmCustom.clearCache();
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("分配权限失败！");
        }
        return result;
    }
    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(String name) {
        Result result = new Result();
        try {
            roleService.addRole(name);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("添加角色失败！");
        }
        return result;
    }

    @RequestMapping("/deleteRole")
    @ResponseBody
    public Object deleteRole(Integer[] ids) {
        Result result = new Result();
        try {
            roleService.deleteRoleUserByRoleId(ids);
            roleService.deleteRolePermissionByRoleId(ids);
            roleService.deleteRole(ids);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("删除角色失败！");
        }
        return result;
    }
}
