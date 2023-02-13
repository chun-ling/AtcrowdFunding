package com.atguigu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YUChangcan
 * @date 2023/1/18 - 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private String loginacct;
    private String userpswd;
    private String username;
    private String email;
    private String phoneNum;
    private String code;
}
