package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author YUChangcan
 * @date 2023/1/7 - 17:14
 * 考虑到User对象中仅仅包含账号和密码，为了能够获取到原始的Admin对象，专门创建这个类对User进行扩展
 */
public class SecurityAdmin extends User {
    private static final long serialVersionUID = 1L;
    //原始的Admin对象，包含Admin对象的全部属性
    private Admin originalAdmin;
    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities){
        super(originalAdmin.getLoginAcct(),originalAdmin.getUserPswd(),authorities);
        this.originalAdmin = originalAdmin;
        this.originalAdmin.setUserPswd(null);
    }
    public Admin getOriginalAdmin(){
        return originalAdmin;
    }
}
