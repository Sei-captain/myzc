package com.sei.controller;

import com.sei.bean.ResetPassword;
import com.sei.bean.User;
import com.sei.service.UserService;
import com.sei.util.CheckCodeImage;
import com.sei.util.Const;
import com.sei.util.MailUtils;
import com.sei.util.Result;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
public class DispatcherController {
    @Autowired
    UserService userService;
    @Autowired
    Result result;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/reg")
    public String reg() {
        return "reg";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword() {
        return "forgetpassword";
    }

    @RequestMapping("/getCheckCodeImage")
    public void getCheckCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CheckCodeImage checkImage = new CheckCodeImage();
        BufferedImage image = checkImage.createImage();
        request.getSession().setAttribute(Const.YZM, checkImage.text);
        ServletOutputStream out = response.getOutputStream();
        CheckCodeImage.output(image, out);
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public Result doLogin(String loginacct, String userpswd, String type, String checkCode, String rememberme, HttpSession session) throws Exception {
        User user = userService.queryLogin(loginacct, userpswd);
        if (user == null) {
            result.setFlag(false);
            result.setMessage("用户名或密码错误！");
        } else {
            session.setAttribute(Const.LOGIN_USER, user);
            result.setFlag(true);
            result.setType(type);
        }

        return result;
    }

    /**
     * 发送重置密码邮件
     * */
    @RequestMapping("/resetPasswordEmail")
    @ResponseBody
    public Result resetPasswordEmail(String loginacct, String email, HttpServletRequest request) throws Exception {
        User user = userService.queryAccount(loginacct);
        if (user == null) {
            result.setFlag(false);
            result.setMessage("账号不存在，请重新输入！");
            return result;
        }
        try {
            String mkey = "";
            ResetPassword resetPassword = new ResetPassword();
            ResetPassword resetLoginacct = userService.queryResetPasswordByAccount(loginacct);//查找重置密码记录，存在记录更新失效时间和mkey，不存在则添加记录
            if (resetLoginacct != null) {
                String secretKey = UUID.randomUUID().toString();  //密钥
                Date outDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 设置过期时间：30分钟

                long failureTime = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql取出时间是忽略毫秒数的
                String key = user.getLoginacct() + "$" + failureTime + "$" + secretKey;
                mkey = new Md5Hash(key).toString();
                resetPassword.setMkey(mkey);
                resetPassword.setFailuretime(failureTime);
                resetPassword.setLoginacct(loginacct);
                userService.updateResetPassword(resetPassword);
            } else {
                String secretKey = UUID.randomUUID().toString();  //密钥
                Date outDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 设置过期时间：30分钟
                long failureTime = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql取出时间是忽略毫秒数的
                String key = user.getLoginacct() + "$" + failureTime + "$" + secretKey;
                mkey = new Md5Hash(key).toString();
                resetPassword.setFailuretime(failureTime);
                resetPassword.setLoginacct(user.getLoginacct());
                resetPassword.setMkey(mkey);
                resetPassword.setStatus("0");
                userService.addResetPassword(resetPassword);
            }

            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            String resetPassHref = basePath + "resetPassword.do?mkey=" + mkey + "&loginacct=" + user.getLoginacct();
            String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href=" + resetPassHref + " target='_BLANK'>点击我重新设置密码</a>" +
                    "<br/>tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'";

            MailUtils.sendMail(email, emailContent, "重置密码");
            result.setFlag(true);
            result.setMessage("邮件发送成功,请登录邮箱确认！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("邮件发送失败，请确认邮箱是否正确！");
        }


        return result;
    }
    /**
     * 验证重置密码url
     * */
    @RequestMapping("/resetPassword")
    public String resetPassword(String mkey, String loginacct, Model m) throws Exception {

        if (mkey.equals("") || loginacct.equals("")) {
            m.addAttribute("message", "链接不完整,请重新发送邮件");
            return "error";
        }
        User user = userService.queryAccount(loginacct);
        if (user == null) {
            m.addAttribute("message", "链接错误无法找到匹配账号，请重新发送邮件！");
            return "error";
        }
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setMkey(mkey);
        resetPassword.setLoginacct(loginacct);
        resetPassword.setStatus("0");
        ResetPassword rp = userService.queryResetPassword(resetPassword);
        if (rp == null) {
            m.addAttribute("message", "无效链接");
            return "error";
        }

        long failureTime = rp.getFailuretime();
        if (failureTime < System.currentTimeMillis() ) {
            m.addAttribute("message", "链接已过期，请重新找回密码！");
            return "error";
        }
        if (!mkey.equals(rp.getMkey())) {
            m.addAttribute("message", "链接失效，请重新找回密码！");
            return "error";
        }

        return "resetpassword";
    }

    @RequestMapping("/doResetPassword")
    @ResponseBody
    public Object doResetPassword(String loginacct, String mkey, String password, String repassword) {
        Result result = new Result();
        User user = new User();
        user.setLoginacct(loginacct);
        String md5 = new SimpleHash("MD5", password, ByteSource.Util.bytes(user.getLoginacct()), 3).toString();
        user.setUserpswd(md5);
        try {
            userService.updateUserPassword(user);
            userService.updateResetPasswordStatus(loginacct, mkey);//密码修改成功后，将记录的状态更改为1
            result.setFlag(true);
            result.setMessage("修改密码成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("修改密码失败！");
        }
        return result;
    }

}
