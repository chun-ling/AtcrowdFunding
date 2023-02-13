package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.po.MemberPO;

/**
 * @author YUChangcan
 * @date 2023/1/14 - 15:08
 */
public interface MemberService {
    MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);
}
