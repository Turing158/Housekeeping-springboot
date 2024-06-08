package com.housekeeping.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

public class OtherUtil {
    public static String getCode(int length){
        char[] num_letter = "abcdefghijklmnobqrstuvwxyz23456789".toCharArray();
        java.util.Random r = new java.util.Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(num_letter[r.nextInt(num_letter.length)]);
        }
        return code.toString();
    }
    public static String getRandomNumber(int length){
        char[] num_letter = "0123456789".toCharArray();
        java.util.Random r = new java.util.Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char num = num_letter[r.nextInt(num_letter.length)];
            while (i == 0 && num == '0') {
                num = num_letter[r.nextInt(num_letter.length)];
            }
            code.append(num);
        }
        return code.toString();
    }

    public static List<?> subList(List<?> list, int pageSize, int pageNum) {
        int count = list.size();
        int pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        int start = pageNum <= 0 ? 0 : (pageNum > pages ? (pages - 1) * pageSize : (pageNum - 1) * pageSize);
        int end = pageNum <= 0 ? (Math.min(pageSize, count)) : (Math.min(pageSize * pageNum, count));
        return list.subList(start, end);
    }


    public static String getImageSuffixByBase64(String base64){
        //获取base64的图片后缀
        String suffix = base64.split(",")[0].split("/")[1].split(";")[0];
        return suffix;
    }

    public static byte[] base64ToByte(String base64){
        byte[] bytes = Base64.getDecoder().decode(base64.split(",")[1]);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {
                bytes[i] += 256;
            }
        }
        return bytes;
    }

    public static int saveFile(byte[] bytes, String path,String fileName) {
        if(!new File(path).exists()){
            throw new RuntimeException("文件夹不存在");
        }
        try{
            OutputStream out = new FileOutputStream(path+"/"+fileName);
            out.write(bytes);
            out.flush();
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        if(new File(path+"/"+fileName).exists()){
            return 1;
        }
        return 0;
    }

}
