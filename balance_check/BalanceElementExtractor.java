package com.meituan.mobile.finance.service.balance_check;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangdongshen on 15/8/12.
 * 验证平衡条件，抽取配置文件的内容
 * 配置文件内容如下：
 * sell_maidan=mt_voucher-poi_voucher+mt_promotion=mt_discount+mt_fixed+poi_fixed
 * consume_maidan=mt_voucher+poi_voucher+mt_promotion=mt_discount+mt_fixed+poi_fixed
 * refund_maidan=mt_voucher+poi_voucher+mt_promotion=mt_discount+mt_fixed+poi_fixed
 */
public class BalanceElementExtractor{
    private static final Logger logger = LoggerFactory.getLogger(BalanceElementExtractor.class);

    private static final Multimap<String, BalanceElement> checkBound = HashMultimap.create();
    private static final String ONE_DIGIT_OR_MORE_REG_EX = "-?[a-z|_]+";
    private static final Pattern NUMBER_PATTERN = Pattern.compile(ONE_DIGIT_OR_MORE_REG_EX);

    private static final BalanceElementExtractor BALANCE_ELEMENT_EXTRACTOR = new BalanceElementExtractor();

    private BalanceElementExtractor(){
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "runtimecfg/balance_rules.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
                Set<Map.Entry<Object, Object>> set = prop.entrySet();
                for (Map.Entry<Object, Object> entry : set) {
                    String[] values = entry.getValue().toString().split(";");
                    for(String v : values){
                        extract(entry.getKey().toString().toUpperCase(), v);
                    }
                }
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void extract(String key, String strRule) {
        String[] eleRules = strRule.split("=");

        BalanceElement balanceElement = new BalanceElement();
        balanceElement.setEl1(extractPatternMatcher(eleRules[0]));
        balanceElement.setEl2(extractPatternMatcher(eleRules[1]));

        checkBound.put(key, balanceElement);

    }

    private Set<String> extractPatternMatcher(String string) {
        Matcher matcher = NUMBER_PATTERN.matcher(string);
        Set<String> set = new LinkedHashSet<>();
        while (matcher.find()) {
            String value = matcher.group();
            set.add(value);
        }
        return set;
    }

    public static Collection<BalanceElement> getBalanceElement(String key){
        return BALANCE_ELEMENT_EXTRACTOR.checkBound.get(key);
    }

    public static void main(String[] args) {
//        BalanceElementExtractor balanceElementExtractor = new BalanceElementExtractor();
//        BalanceElementExtractor balanceElementExtractor1 = new BalanceElementExtractor();
//        BalanceElementExtractor balanceElementExtractor2 = new BalanceElementExtractor();

//        for(Map.Entry<String, BalanceElement> entry : checkBound.entrySet()){
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }

        System.out.println(BalanceElementExtractor.getBalanceElement("consume_maidan"));


//        System.out.println(BalanceElementExtractor.getBalanceElement("refund_maidan"));

//        System.out.println(BalanceElementExtractor.getBalanceElement("refund_maidan"));

//        System.out.println(BalanceElementExtractor.getBalanceElement("refund_maidan"));
    }
}
