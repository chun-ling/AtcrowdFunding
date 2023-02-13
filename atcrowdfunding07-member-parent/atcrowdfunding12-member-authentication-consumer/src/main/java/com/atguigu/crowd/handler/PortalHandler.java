package com.atguigu.crowd.handler;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author YUChangcan
 * @date 2023/1/16 - 15:31
 */
@Controller
public class PortalHandler {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @RequestMapping("/")
    public String showPortalPage(Model model){
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();
        String result = resultEntity.getResult();
        if(ResultEntity.SUCCESS.equals(result)){
            List<PortalTypeVO> list = resultEntity.getData();
            model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA,list);
            System.out.println("成功");
        }
        // 实际开发中需要加载数据
        return "portal";
    }
}
