package com.meituan.mobile.finance.service.balance_check;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangdongsheng on 15/8/13.
 */
public interface Check {
    public boolean balanceCheck(String partner, String bizType,   Map<String, String> hashMap);
}
