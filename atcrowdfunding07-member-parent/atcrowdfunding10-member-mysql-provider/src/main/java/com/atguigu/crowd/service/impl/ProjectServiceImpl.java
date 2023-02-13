package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.po.MemberConfirmInfoPO;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.po.ProjectPO;
import com.atguigu.crowd.entity.po.ReturnPO;
import com.atguigu.crowd.entity.vo.*;
import com.atguigu.crowd.mapper.*;
import com.atguigu.crowd.service.api.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author YUChangcan
 * @date 2023/1/21 - 8:35
 */
@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired(required = false)
    private ProjectPOMapper projectPOMapper;
    @Autowired(required = false)
    private ProjectItemPicPOMapper projectItemPicPOMapper;
    @Autowired(required = false)
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
    @Autowired(required = false)
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
    @Autowired(required = false)
    private ReturnPOMapper returnPOMapper;
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        ProjectPO projectPO = new ProjectPO();
        BeanUtils.copyProperties(projectVO,projectPO);
        projectPO.setMemberid(memberId);
        String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createdate);
        projectPO.setStatus(0);
        projectPOMapper.insertSelective(projectPO);
        Integer projectId = projectPO.getId();
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList,projectId);
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList,projectId);
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(projectId,detailPicturePathList);
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO,memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO,returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList,projectId);

        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO,memberConfirmInfoPO);
        memberConfirmInfoPO.setId(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }

    @Override
    public List<PortalTypeVO> getPortalTypeVO() {
        return projectPOMapper.selectPortalTypeVOList();
    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectid) {
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectid);

        Integer status = detailProjectVO.getStatus();
        switch(status){
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("已关闭");
                break;
            default:
                break;
        }
        // 3.根据deployeDate计算lastDay
        String deployDate = detailProjectVO.getDeployDate();
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date deployDay = format.parse(deployDate);
            long currentTimeStamp = currentDate.getTime();
            long deployTimeStamp = deployDay.getTime();
            long pastDays = (currentTimeStamp - deployTimeStamp)/1000/60/60/24;
            // 使用总的众筹时间减去已经过去的天数
            Integer totalDays = detailProjectVO.getDay();
            Integer lastDay = (int)(totalDays - pastDays);
            detailProjectVO.setLastDay(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return detailProjectVO;
    }
}
