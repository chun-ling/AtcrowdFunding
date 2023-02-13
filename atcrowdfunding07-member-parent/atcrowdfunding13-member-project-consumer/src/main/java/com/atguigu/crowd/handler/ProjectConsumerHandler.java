package com.atguigu.crowd.handler;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.config.OSSProperties;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.vo.*;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YUChangcan
 * @date 2023/1/22 - 8:39
 */
@Controller
public class ProjectConsumerHandler {
    @Autowired
    private OSSProperties ossProperties;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId, Model model){
        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            DetailProjectVO detailProjectVO = resultEntity.getData();
            model.addAttribute("detailProjectVO",detailProjectVO);
        }
        return "project-show-detail";
    }
    @RequestMapping("/create/confirm")
    public String saveConfirm(ModelMap modelMap,HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO){

        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if(projectVO == null){
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        // 调用远程方法
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO,memberId);
        String result = saveResultEntity.getResult();
        if(ResultEntity.FAILED.equals(result)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,saveResultEntity.getMessage());
            return "project-confirm";
        }
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        return "redirect:http://localhost:5000/project/create/success";
    }
    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO,HttpSession session){
        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
            if(projectVO == null){
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            if(returnVOList == null || returnVOList.size() == 0){
                returnVOList = new ArrayList<>();
                projectVO.setReturnVOList(returnVOList);
            }
            returnVOList.add(returnVO);
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), returnPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), returnPicture.getOriginalFilename());
        return uploadReturnPicResultEntity;
    }
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictureList, HttpSession session, ModelMap modelMap) throws IOException {
        boolean headerPictureIsEmpty = headerPicture.isEmpty();
        if(headerPictureIsEmpty){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";
        }
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), headerPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), headerPicture.getOriginalFilename());
        String result = uploadHeaderPicResultEntity.getResult();
        if(ResultEntity.SUCCESS.equals(result)){
            String headerPicturePath = uploadHeaderPicResultEntity.getData();
            projectVO.setHeaderPicturePath(headerPicturePath);
        }else{
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        }
        List<String> detailPicturePathList = new ArrayList<>();
        if(detailPictureList == null || detailPictureList.size() == 0){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
            return "project-launch";
        }
        for (MultipartFile detailPicture :
                detailPictureList) {
            if(detailPicture.isEmpty()){
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
            ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), detailPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), detailPicture.getOriginalFilename());
            if(ResultEntity.SUCCESS.equals(detailUploadResultEntity.getResult())){
                String detailPicturePath = detailUploadResultEntity.getData();
                detailPicturePathList.add(detailPicturePath);
                }else {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
        }
        projectVO.setDetailPicturePathList(detailPicturePathList);
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
        return "redirect:http://localhost:5000/project/return/info/page";
    }
}
