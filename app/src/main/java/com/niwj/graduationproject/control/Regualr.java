package com.niwj.graduationproject.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by prince70 on 2017/8/22.
 */

public class Regualr {

    /**
     * 邮政编号
     * @param mail
     * @return
     */
    public static boolean isMail(String mail) {
        Pattern p = Pattern.compile("^[1-9][0-9]{5}$");
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    /**
     * 身份证
     * @param idcard
     * @return
     */
    public static boolean isIdcard(String idcard) {
        Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher m = p.matcher(idcard);
        return m.matches();
    }

    /**
     * 手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^1\\d{10}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 密码
     * @param pwd
     * @return
     */
    public static boolean isPwd(String pwd) {
        Pattern p = Pattern.compile("^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,16}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }
}
