package com.sei.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sei.bean.Role;
import com.sei.bean.User;
import com.sei.service.RoleService;
import com.sei.service.UserService;
import com.sei.util.Data;
import com.sei.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
//@RequiresPermissions("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/index")
    public String index(){
        return "user/indexAjax";
    }
    //同步
   /* @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                              String queryText) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userService.queryUser(queryText);
        PageInfo pageInfo = new PageInfo(userList);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("queryText",queryText);
        mv.setViewName("user/index");
        return mv;
    }*/

    //异步
    @RequestMapping("/queryPage")
    @ResponseBody
    public Object queryPage(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                              String queryText) throws Exception {
        Result result = new Result();
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<User> userList = userService.queryUser(queryText);
            PageInfo pageInfo = new PageInfo(userList);
            result.setPageInfo(pageInfo);
            result.setFlag(true);

        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("查询失败！");
        }
        return result;
    }
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "user/add";
    }

    @RequestMapping("/assignRole")
    public ModelAndView assignRole(Integer id) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Role> roleList = roleService.queryRole(null);
        List<Integer> roleids = roleService.queryRoleIdById(id);
        List<Role> roleHave = new ArrayList<>();
        List<Role> roleNo = new ArrayList<>();
        for (Role role : roleList) {
            if (roleids.contains(role.getId())){
                roleHave.add(role);
            }else {
                roleNo.add(role);
            }
        }
        mv.addObject("roleHave",roleHave);
        mv.addObject("roleNo",roleNo);
        mv.setViewName("user/assignrole");
        return mv;
    }
    @RequestMapping("/doAssignRole")
    @ResponseBody
    public Object doAssignRole(Integer userid, Data data){
        Result result = new Result();
        try {
            userService.addUserRole(userid,data);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("分配失败");
        }
        return result;
    }
    @RequestMapping("/doUnAssignRole")
    @ResponseBody
    public Object doUnAssignRole(Integer userid,Data data){
        Result result = new Result();
        try {
            userService.deleteUserRole(userid,data);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("分配失败");
        }
        return result;
    }


    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(User user)throws Exception{
        Result result = new Result();
        int count = userService.addUser(user);
        if (count!=1){
            result.setFlag(false);
        }else {
            result.setFlag(true);
        }
        return result;
    }
    @RequestMapping("/toUpdateUser")
    public ModelAndView toUpdateUser(Integer id) throws Exception {
        ModelAndView mv = new ModelAndView();
        User user = userService.queryUserById(id);
        mv.addObject("user",user);
        mv.setViewName("user/update");
        return mv;
    }
    @RequestMapping("/doUpdateUser")
    @ResponseBody
    public Object doUpdateUser(User user) throws Exception {
        Result result = new Result();
        int count = userService.doUpdateUser(user);
        if (count!=1){
            result.setFlag(false);
        }else {
            result.setFlag(true);
        }
        return result;
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public Object deleteUser(Integer[] ids) throws Exception {
        Result result = new Result();
        try {
            userService.deleteRoleIdByUserId(ids);
            int count = userService.deleteUser(ids);
            result.setFlag(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("删除用户失败！");
        }
        return result;
    }
}
