package com.example.images;

import java.io.File;

public class Beans {
    private String fileName;
    private int fileCount;
    private File file;
    private String path;

    public Beans(String fileName, int fileCount, File file, String path) {
        this.fileName = fileName;
        this.fileCount = fileCount;
        this.file = file;
        this.path = path;
    }

    public Beans() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Beans{" +
                "fileName='" + fileName + '\'' +
                ", fileCount=" + fileCount +
                ", file=" + file +
                ", path='" + path + '\'' +
                '}';
    }
}
