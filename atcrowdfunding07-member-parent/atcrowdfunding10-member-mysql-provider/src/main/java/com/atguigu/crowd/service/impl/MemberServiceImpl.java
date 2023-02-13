package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.po.MemberPOExample;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YUChangcan
 * @date 2023/1/14 - 15:08
 */
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired(required = false)
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        MemberPOExample example = new MemberPOExample();
        MemberPOExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(loginacct);
        List<MemberPO> list = memberPOMapper.selectByExample(example);
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
