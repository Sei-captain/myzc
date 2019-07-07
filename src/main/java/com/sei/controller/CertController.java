package com.sei.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sei.bean.Cert;
import com.sei.service.CertService;
import com.sei.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cert")
public class CertController {
    @Autowired
    CertService certService;

    @RequestMapping("/index")
    public String index(){
        return "cert/index";
    }

    @RequestMapping("/pageQuery")
    @ResponseBody
    public Object pageQuery(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                            String queryText){
        Result result = new Result();
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<Cert> certList = certService.queryCert(queryText);
            PageInfo pageInfo = new PageInfo(certList);
            result.setFlag(true);
            result.setPageInfo(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("查询失败！");
        }
        return result;
    }


}
