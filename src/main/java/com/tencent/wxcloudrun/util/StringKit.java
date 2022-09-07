package com.tencent.wxcloudrun.util;


import java.util.Random;

public class StringKit {
	
	/**
     * UTF-8字符集 *
     */
    public static final String CHARSET_UTF8 = "UTF-8";

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    public static String genRandomString32(){
    	return getRandomStringByLength(32);
    }
    
 
}
