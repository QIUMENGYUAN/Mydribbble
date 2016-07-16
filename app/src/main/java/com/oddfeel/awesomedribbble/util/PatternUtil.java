package com.oddfeel.awesomedribbble.util;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class PatternUtil {
    //正则表达式去掉html标签
    public static String Nohtml(String s){
        if (s == null){//不能isEmpty()
            return "he/she didn't write anything";
        }else {
            s = s.replaceAll("\\<p>|</p>","").replaceAll("<a[^>]*>([^<]*)</a>","").replaceAll("<strong[^>]*>([^<]*)</strong>","").replaceAll("\\<br .*>","") ;
            return s;
        }
    }
}
