package com.ikkong.imagepreview;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class Utils {

    /** 获得状态栏的高度 */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /** 根据屏幕宽度与密度计算GridView显示的列数， 最少为三列，并获取Item宽度 */
    public static int getImageItemWidth(Activity activity) {
        int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        int densityDpi = activity.getResources().getDisplayMetrics().densityDpi;
        int cols = screenWidth / densityDpi;
        cols = cols < 3 ? 3 : cols;
        int columnSpace = (int) (2 * activity.getResources().getDisplayMetrics().density);
        return (screenWidth - columnSpace * (cols - 1)) / cols;
    }

    /**
     * 判断SDCard是否可用
     */
    public static boolean existSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机大小（分辨率）
     */
    public static DisplayMetrics getScreenPix(Activity activity) {
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
        return displaysMetrics;
    }

    ///////////////////////复制文件//////////////////////////////
    /**
     * 复制单个文件 
     * @param old 
     * @param newPath String 复制后路径 如：f:/fqf.txt 
     * @return boolean
     */
    public static boolean copyFile(File old, String newPath) {
        boolean isok = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            if (old.exists()) { //文件存在时 
                InputStream inStream = new FileInputStream(old); //读入原文件 
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小 
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
            else
            {
                isok = false;
            }
        }
        catch (Exception e) {
            // System.out.println("复制单个文件操作出错"); 
            // e.printStackTrace(); 
            isok = false;
        }
        return isok;

    }

    /**
     * Create a File for saving an img
     */
    public static String getDownloadImgPath() {
        File mediaStorageDir = null;
        try {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ImagePreviewDownload");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果不存在 就创建文件夹
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        return mediaStorageDir.getPath() + File.separator + timeStamp + ".jpg";
    }
}
