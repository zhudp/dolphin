package com.dolphin.web;

import com.core.utils.ImgUtils;


public class ImgUtilsThread extends ThreadAction{
    private String srcImgPath;
    private String targetImgPath;
    private int targetWidth;
    private int targetHeight;

    public ImgUtilsThread(String srcImgPath, String targetImgPath,int targetWidth,int targetHeight)
    {
        this.srcImgPath = srcImgPath;
        this.targetImgPath = targetImgPath;
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    protected void perform()
        throws Exception
    {
        (new ImgUtils()).reduceImg(srcImgPath, targetImgPath, targetWidth, targetHeight);
    }


}