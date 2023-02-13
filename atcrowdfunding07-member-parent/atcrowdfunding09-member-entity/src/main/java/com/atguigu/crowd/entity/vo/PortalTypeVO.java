package com.atguigu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author YUChangcan
 * @date 2023/1/24 - 22:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalTypeVO {
    private Integer id;
    private String name;
    private String remark;
    private List<PortalProjectVO> portalProjectVOList;
}
