package com.meituan.mobile.finance.service.balance_check;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangdongsheng on 15/8/13.
 * 根据set中的string得到hashmap中的double值，相加起来
 */
@Service
public class SumCalculator implements Calculator {
    private static final Logger logger = LoggerFactory.getLogger(SumCalculator.class);

    @Override
    public BigDecimal sum(Set<String> set, Map<String, String> hashMap) {
        BigDecimal sum = new BigDecimal("0");
        for (String s : set) {
            if (StringUtils.isEmpty(s)) continue;
            if (s.startsWith("-")) {
                String value = hashMap.get(s.substring(1));
                if (!StringUtils.isEmpty(value)) {
                    sum = sum.subtract(new BigDecimal(value));
                }
            } else {
                String value = hashMap.get(s);
                if (!StringUtils.isEmpty(value)) {
                    sum = sum.add(new BigDecimal(value));
                }
            }

        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(Double.valueOf("8.6") + Double.valueOf("0.2"));
        double a = 8.6;
        double b = 0.2;
        System.out.println(a+b);
    }
}
