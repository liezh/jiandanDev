package com.liezh.utils;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/21.
 *  处理MarkDown.
 */
public class MarkDownUtil {

    private static Pattern _regexLink = Pattern.compile("!\\[(.*?)\\]\\(.*?\\)");  //[()]+

    private static Pattern _regexImgUrl = Pattern.compile("(?<=\\()(.+?)(?=\\))");

    private static Pattern _regexAbstract = Pattern.compile("[^a-zA-Z\u4e00-\u9fa5 ;,.!'\"?\\[\\]\\(\\)-_，。‘”；！？]");

    private static Pattern _regexChar = Pattern.compile("^!.,?！，。？");

    private static Matcher _matcher = null;

    public static String getFirstImg(String md, String def) {
        _matcher = _regexLink.matcher(md);
        _matcher.find();
        try {
            String cover = _matcher.group();
            return cover;
        } catch (Exception e) {
            return def != null ? def : "";
        }
    }

    public static List<String> getMdImgs(String md) {
        _matcher = _regexLink.matcher(md);
        List<String> imgs = new ArrayList<>();
        while(_matcher.find()) {
            imgs.add(_matcher.group());
        }
        return imgs;
    }


    public static String getUrlByMdImg(String md, String def) {
        _matcher = _regexImgUrl.matcher(md);
        _matcher.find();
        try {
            String url = _matcher.group();
            url = url.substring(0, url.lastIndexOf(" "));
            return url;
        } catch (Exception e) {
            return def != null ? def : "";
        }
    }

    /**
     *  获取MarkDown 图片标签中的 url
     * @param imgs
     * @return
     */
    public static List<String> getUrlByMdImgList(List<String> imgs) {
        List<String> urls = new ArrayList<>();
        Iterator<String> iterator = imgs.iterator();
        while (iterator.hasNext()) {
            String url = getUrlByMdImg(iterator.next(), "");
            urls.add(url);
        }
        return urls;
    }

    public static String getAbstract(String md, int length) {

        _matcher  = _regexLink.matcher(md);
        String abst = _matcher.replaceAll("");
        _matcher = _regexAbstract.matcher(abst);
        abst = _matcher.replaceAll("");
        if (abst.length() > length) {
            return abst.substring(0, length-4) + "……";
        }
        return abst;
    }


    public static void main(String[] args) {
        String md = "# 传感器### " +
                "1.什么是传感器- 传感器是一种感应\\检测装置, 目前已经广泛应用于智能手机上### " +
                "2.传感器的作用- 用于感应\\检测设备[周边]的信息- 不同类型的传感器, 检测的信息也不一样##### iPhone中 321321321312";
        String img = getFirstImg(md, "");
        String url = getUrlByMdImg(img, "");
        List<String> imgs = getMdImgs(md);
        List<String> urls = getUrlByMdImgList(imgs);
        System.out.println(img);
        System.out.println(url);
        System.out.println(urls.toString());
        System.out.println(getAbstract(md, 255));
    }




}
