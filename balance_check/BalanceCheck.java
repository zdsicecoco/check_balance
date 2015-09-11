package com.meituan.mobile.finance.service.balance_check;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * Created by zhangdongsheng on 15/8/12.
 * 平衡条件校验
 */
@Service
public class BalanceCheck implements Check {
    private static final Logger logger = LoggerFactory.getLogger(BalanceCheck.class);

    @Resource(name="sumCalculator")
    private Calculator sumCalculator;

    /**
     * 校验数据平衡：
     * @param partner 业务方名称:maidan
     * @param bizType 业务名称:consume,pay
     * @param hashMap 业务名称:金额 如 selling_price:1.12
     * @return 如果得不到平衡条件返回true，其他根据平衡判断返回结果
     */
    @Override
    public boolean balanceCheck(String partner, String bizType,  Map<String, String> hashMap) {
        String key = bizType + "_" + partner;
        Collection<BalanceElement> balanceElementCollection = BalanceElementExtractor.getBalanceElement(key);

        for(BalanceElement balanceElement : balanceElementCollection){
            BigDecimal d1 = sumCalculator.sum(balanceElement.getEl1(), hashMap);
            BigDecimal d2 = sumCalculator.sum(balanceElement.getEl2(), hashMap);
            if(d1.compareTo(d2) != 0) return false;
        }

        return true;
    }

    public static void main(String[] args) {
    }


}