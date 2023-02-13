package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author YUChangcan
 * @date 2022/12/21 - 21:20
 */
@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;
    @RequestMapping("admin/update.html")
    public String update(Admin admin,@RequestParam(value="keyword",defaultValue = "")String keyword,
                         @RequestParam(value="pageNum",defaultValue = "1")Integer pageNum){
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }
    @RequestMapping("admin/to/edit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("keyword") String keyword,
                             ModelMap modelMap){
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin",admin);
        return "admin-edit";
    }
    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("admin/save.html")
    public String save(Admin admin){

        adminService.savaAdmin(admin);
        return "redirect:/admin/get/page.html";

    }
    @RequestMapping("admin/remove/admin.html")
    public String remove(@RequestParam("adminId") Integer adminId,
                         @RequestParam(value="keyword",defaultValue = "")String keyword,
                         @RequestParam(value="pageNum",defaultValue = "1")Integer pageNum){
        adminService.remove(adminId);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&Keyword" + keyword;
    }
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                              ModelMap modelMap){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }
    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session){
        //强制session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
    @RequestMapping("admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session){
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);
        //将登录成功返回的admin对象存入Session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }
//    @PreFilter(value="filterObject%2==0")
//    @ResponseBody
//    @RequestMapping("admin/test/pre/filter")
//    public ResultEntity<List<Integer>> saveList(@RequestBody List<Integer> valueList){
//        return ResultEntity.successWithData(valueList);
//    }
}
