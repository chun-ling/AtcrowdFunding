package com.atguigu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YUChangcan
 * @date 2023/1/26 - 8:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailReturnVO {
    // 回报信息主键
    private Integer returnId;
    // 当前挡位需支持的金额
    private Integer supportMoney;
    // 单笔限购，取值为0时无限额，取值为正时为具体限额
    private Integer signalPurchase;
    private Integer purchase;
    private Integer supporterCount;
    // 运费，取值为零时运费为零
    private Integer freight;
    private Integer returnDate;
    private String content;

}
