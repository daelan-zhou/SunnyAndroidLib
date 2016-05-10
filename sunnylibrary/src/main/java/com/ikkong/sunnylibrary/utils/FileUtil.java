package com.ikkong.sunnylibrary.utils;

import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by sunny on 2015-10-19.
 */
public class FileUtil {
    /**
     * Create a File for saving an image or video
     * @param type 1 jpg  2 mp4
     * @param saveFolder 文件夹名称
     * @return 文件Uri
     */
    public static Uri getOutputMediaFileUri(int type,String saveFolder) {
        return Uri.fromFile(getOutputMediaFile(type,saveFolder));
    }

    /**
     * Create a File for saving an image or video
     * @param type 1 jpg  2 mp4
     * @param saveFolder 文件夹名称
     * @return 文件
     */
    public static File getOutputMediaFile(int type,String saveFolder) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = null;
        try {
            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), saveFolder);

            // Log.d(LOG_TAG, "Successfully created mediaStorageDir: "
            // + mediaStorageDir);

        } catch (Exception e) {
            e.printStackTrace();
            // Log.d(LOG_TAG, "Error in Creating mediaStorageDir: "
            // + mediaStorageDir);
        }

        // 如果不存在 就创建文件夹
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // 在SD卡上创建文件夹需要权限：
                // <uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
//                 Log.d("LOG_TAG",
//                         "failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            Log.d("LOG_TAG", "mediaFile.path---->" + mediaFile.getPath());
        } else if (type == 2) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        
        return mediaFile;
    }

    /**
     * Create a File for saving an apk
     * @param saveFolder 文件夹名称
     * @return 绝对路径
     */
    public static String getOutputApkPath(String saveFolder) {
        File mediaStorageDir = null;
        try {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), saveFolder);
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
        return mediaStorageDir.getPath() + File.separator + timeStamp + ".apk";
    }

    /**
     * 获取SD卡路径
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /***
     * 获取文件夹大小
     ***/
    public static long getFileSize(File f) {
        long size = 0L;
        if (f != null && f.exists()) {
            File flist[] = f.listFiles();
            for (File aFlist : flist) {
                size += aFlist.isDirectory() ? getFileSize(aFlist) : aFlist.length();
            }
        }
        return size;
    }

    /**
     * 生成时间 201604271613410 + 随机数 的文件名
     * @return
     */
    public static String buildFileName(){
        return TextUtils.concat(UnixTime.getStrCurrentTime(),new Random().nextInt(1000)+"").toString();
    }
}
