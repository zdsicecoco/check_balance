package com.meituan.mobile.finance.service.balance_check;

import java.util.Set;

/**
 * Created by zhangdongsheng on 15/8/12.
 */
public class BalanceElement {
    private Set<String> el1;
    private Set<String> el2;


    public Set<String> getEl1() {
        return el1;
    }

    public Set<String> getEl2() {
        return el2;
    }

    public void setEl1(Set<String> el1) {
        this.el1 = el1;
    }

    public void setEl2(Set<String> el2) {
        this.el2 = el2;
    }

    @Override
    public String toString() {
        return "BalanceElement{" +
                "el1=" + el1 +
                ", el2=" + el2 +
                '}';
    }
}
