package com.atguigu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author YUChangcan
 * @date 2023/1/19 - 9:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO implements Serializable {
    private Integer id;
    private String username;
    private String email;
    private static final long serialVersionUID = -1;
}
