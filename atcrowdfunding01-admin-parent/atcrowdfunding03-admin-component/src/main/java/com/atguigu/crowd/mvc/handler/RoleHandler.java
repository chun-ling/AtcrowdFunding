package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author YUChangcan
 * @date 2022/12/23 - 19:22
 */
@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;
    @ResponseBody
    @RequestMapping("/role/delete.json")
    public ResultEntity<PageInfo<Role>> removeRole(@RequestBody List<Integer> roleIdList){
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("role/update.json")
    public ResultEntity<String> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("role/save.json")
    public ResultEntity<String> saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }
    @PreAuthorize("hasRole('部长')")
    @ResponseBody
    @RequestMapping("role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                                    @RequestParam(value = "keyword",defaultValue = "")String keyword){
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);

    }
}
