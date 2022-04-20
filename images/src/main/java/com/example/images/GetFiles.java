package com.example.images;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class GetFiles {
    private static String path;

    public GetFiles(String path) {
        GetFiles.path = path;
    }

    public ArrayList<Beans> getFiles(){
        ArrayList<Beans> fileList = new ArrayList<>();
        File file = null;
        try {
            file = new File(path);//按路径取得文件
        }catch (Exception e){
            e.printStackTrace();
        }
        if(file.isDirectory()){//判断文件目录是否存在
            File[] tempList = file.listFiles();
            for (File temp : tempList){
                Beans bean = new Beans();
                if (temp.isDirectory()){
                    if (!temp.getName().contains(".")){
                        bean.setFileName(temp.getName());
                        bean.setFileCount(getCount(path + "/" + temp.getName()));
                        System.out.println(path + "/" + temp.getName());
                        bean.setFile(getFile(path + "/" + temp.getName()));
                        bean.setPath(temp.getPath());
                        if(bean.getFileCount() != 0){
                            fileList.add(bean);
                        }
                    }
                }
            }
        }
        return fileList;
    }

    public File getFile(String directoryName){
        File file = new File(directoryName);
        if (file.isDirectory()){
            File[] tempList = file.listFiles();
            for (File temp : tempList){
                if (temp.isFile()){
                    if (temp.getName().endsWith(".jpg" )|| temp.getName().endsWith(".png")){
                        file = new File(directoryName + "/" + temp.getName());
                    }else if (temp.getName().endsWith(".mp4")){
                        file = null;
                    }
                }
            }
        }
        return file;
    }

    public int getCount(String directoryName){
        int count = 0;
        File file = new File(directoryName);//按路径取得文件
        if (file.isDirectory()){
            File[] tempList = file.listFiles();
            System.out.println(tempList.length);
            Log.i("directoryName",directoryName);
            for (File temp : tempList){
                if (temp.isFile()){
                    if (temp.getName().endsWith(".jpg") || temp.getName().endsWith(".mp4") || temp.getName().endsWith(".png")){
                        count ++;
                    }
                }
            }
        }
        return count;
    }

    public static ArrayList<Beans> getImage(String path){
        ArrayList<Beans> imageList = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()){
            File[] tempList = file.listFiles();
            Log.i("lengthList","have" + tempList.length);
            for (File temp : tempList){
                if (temp.isFile()){
                    if (temp.getName().endsWith(".jpg") || temp.getName().endsWith(".png")){
                        Beans bean = new Beans();
                        bean.setFile(temp);
                        imageList.add(bean);
                    }else if (temp.getName().endsWith(".mp4")){

                    }
                }
            }
        }
        return imageList;
    }

    public static ArrayList<Bitmap> getMovie(String filePath){
        ArrayList<Bitmap> movieList = new ArrayList<>();
        File file = new File(filePath);
        if (file.isDirectory()){
            File[] tempList = file.listFiles();
            for (File temp : tempList){
                if (temp.isFile()){
                    if (temp.getName().endsWith(".mp4")){
                        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath + "/" + temp.getName(), MediaStore.Video.Thumbnails.MINI_KIND);
                        movieList.add(bitmap);
                    }
                }
            }
        }
        return movieList;
    }

    public static ArrayList<String> getPath(String filePath){
        ArrayList<String> nameList = new ArrayList<>();
        File file = new File(filePath);
        if (file.isDirectory()){
            File[] tempList = file.listFiles();
            for (File temp : tempList){
                if (temp.isFile() && temp.getName().endsWith(".mp4")){
                    nameList.add(temp.getPath());
                }
            }
        }
        return nameList;
    }
}
