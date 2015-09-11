package com.meituan.mobile.finance.service.balance_check;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangdongsheng on 15/8/13.
 */
public interface Calculator {
    public BigDecimal sum(Set<String> set, Map<String, String> hashMap);
}

