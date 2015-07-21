package com.core.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.dolphin.web.ImgUtilsThread;
import com.dolphin.web.ThreadManager;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ***********************************************
 * 
 * @file: ImgUtils.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 *             ***********************************************
 * @package: com.hs.core.utils
 * @class: ImgUtils
 * @description:
 * @author: huangrh
 * @since: Nov 20, 2008-1:15:57 PM
 * @history:
 */
public class ImgUtils {

	public ImgUtils() {
	}

	public void reduce(String srcImgPath, String targetImgPath,
			int targetWidth, int targetHeight) {
		ThreadManager.getInstance().executeInBackground(
				new ImgUtilsThread(srcImgPath, targetImgPath, targetWidth, targetHeight));
	}

	/**
	 * 图片格式转换
	 * 
	 * @param srcImgPath  图片名（不含后缀）
	 * @param imgType 目标图片格式（GIF,JPG,PNG）
	 */
	public synchronized void convertType(String srcImgPath, String imgType)
			throws Exception {
		if (!imgType.equals("GIF") && !imgType.equals("JPG") && !imgType.equals("PNG"))
			return;
		File inputFile = new File(srcImgPath);
		BufferedImage input = ImageIO.read(inputFile);
		srcImgPath = srcImgPath.substring(0, srcImgPath.lastIndexOf("."));
		ImageIO.write(input, imgType, new File(srcImgPath + "." + imgType));
	}

	/**
	 * 图片剪切：局部剪切
	 * 
	 * @param srcImgPath 图片路径
	 * @param targetImgPath 将目标图片存入路径
	 * @param targetWidth 目标宽度
	 * @param targetHeight 目标高度
	 */
	public synchronized void cutOutImg(String srcImgPath, String targetImgPath,
			int targetWidth, int targetHeight) throws Exception {

		Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) readers.next();
		InputStream source = null;
		ImageInputStream iis = null;
		try {
			source = new FileInputStream(srcImgPath);
			iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			// 局部剪切，从坐标(0,0) 剪切width:targetWidth,height:targetHeight
			Rectangle rect = new Rectangle(0, 0, targetWidth, targetHeight);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "jpg", new File(targetImgPath));
		} finally {
			// added by yanghb20090116
			try {
				if (source != null) {
					source.close();
				}
			} catch (Exception e) {
				throw new Exception("ImgUtils:cutOutImg() 源图片文件关闭失败");
			}
			try {
				if (iis != null) {
					iis.close();
				}
			} catch (Exception e) {
				throw new Exception("ImgUtils:cutOutImg() 源图片流关闭失败 ");
			}
		}
	}

	/**
	 * 按比例缩放
	 * 
	 * @param srcImgPath 源图片路径
	 * @param targetImgPath 目标存储路径
	 * @param targetWidth 目标宽度
	 * @param targetHeight 目标高度
	 */
	public synchronized void reduceImg(String srcImgPath, String targetImgPath,
			int targetWidth, int targetHeight) throws Exception {

		File srcfile = new File(srcImgPath);
		if (!srcfile.exists())
			return;
		Image src = ImageIO.read(srcfile);
		int width = src.getWidth(null);
		int height = src.getHeight(null);

		BufferedImage tag = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics gra = tag.getGraphics();
		gra.setColor(Color.white);
		gra.fillRect(0, 0, targetWidth, targetHeight);
		if (width <= targetWidth && height <= targetHeight) {
			gra.drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 
					(targetWidth - width) / 2, (targetHeight - height) / 2, null);
		} else {
			float wh = (float) width / (float) height;
			if (wh > 1) {
				float tmp_heigth = (float) targetWidth / wh;
				float y = ((float) targetHeight - tmp_heigth) / 2;
				// tag = new BufferedImage(targetWidth,(int) tmp_heigth,
				// BufferedImage.TYPE_INT_RGB);
				gra.drawImage(src.getScaledInstance(targetWidth,
						(int) tmp_heigth, Image.SCALE_SMOOTH), 0, (int) y, null);
			} else {
				float tmp_width = (float) targetHeight * wh;
				float x = ((float) targetWidth - tmp_width) / 2;
				// tag = new BufferedImage((int) tmp_width,targetHeight,
				// BufferedImage.TYPE_INT_RGB);
				gra.drawImage(src.getScaledInstance((int) tmp_width, targetHeight, Image.SCALE_SMOOTH), (int) x, 0, null);
			}
		}

		FileOutputStream out = new FileOutputStream(targetImgPath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		out.close();
	}
	
	/**
	 * 按比例缩放
	 * 
	 * @param srcImgPath 源图片路径
	 * @param targetImgPath 目标存储路径
	 * @param targetWidth 目标宽度
	 * @param targetHeight 目标高度
	 */
	public static synchronized byte[] reduceImg(String srcImgPath, int targetWidth, int targetHeight) throws Exception {
		
		File srcfile = new File(srcImgPath);
		if (!srcfile.exists())
			return null;
		
		byte[] m = null;  
        ImageOutputStream imOut =null;  
        InputStream is=null;  
        
		Image src = ImageIO.read(srcfile);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		
		BufferedImage tag = null;
		float wh = (float) width / (float) height;
		
		if (width <= targetWidth && height <= targetHeight) {
			tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics gra = tag.getGraphics();
			gra.setColor(Color.white);
			gra.fillRect(0, 0, width, height);
			gra.drawImage(src.getScaledInstance(width,height, Image.SCALE_SMOOTH), 0, 0, null);
		} else {
			tag = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			Graphics gra = tag.getGraphics();
			gra.setColor(Color.white);
			gra.fillRect(0, 0, targetWidth, targetHeight);
			
			if (wh > 1) {
				float tmp_heigth = (float) targetWidth / wh;
				float y = ((float) targetHeight - tmp_heigth) / 2;
				// tag = new BufferedImage(targetWidth,(int) tmp_heigth,
				// BufferedImage.TYPE_INT_RGB);
				gra.drawImage(src.getScaledInstance(targetWidth,
						(int) tmp_heigth, Image.SCALE_SMOOTH), 0, (int) y, null);
			} else {
				float tmp_width = (float) targetHeight * wh;
				float x = ((float) targetWidth - tmp_width) / 2;
				// tag = new BufferedImage((int) tmp_width,targetHeight,
				// BufferedImage.TYPE_INT_RGB);
				gra.drawImage(src.getScaledInstance((int) tmp_width, targetHeight, Image.SCALE_SMOOTH), (int) x, 0, null);
			}
		}
		
		 ByteArrayOutputStream bs = new ByteArrayOutputStream();  
         imOut = ImageIO.createImageOutputStream(bs);  
         ImageIO.write(tag, "JPEG", imOut);  
         is = new ByteArrayInputStream(bs.toByteArray());  
         m = new byte[is.available()];  
         is.read(m);

         return m;
	}
	
	
	/**
	 * 
	 * @param image
	 * @param degree
	 * @param bgcolor
	 * @return
	 * @throws Exception
	 */
    public static void rotateImg(String srcImgPath, int degree) throws Exception {  
    	
    	File srcfile = new File(srcImgPath);
    	if (!srcfile.exists())
			return;
    	
    	BufferedImage bufferedimage = ImageIO.read(srcfile);
        
        int w = bufferedimage.getWidth();  
        int h = bufferedimage.getHeight();  
        int type = bufferedimage.getColorModel().getTransparency();  
        BufferedImage img;  
        Graphics2D graphics2d;  
        (graphics2d = (img = new BufferedImage(h, w, type)).createGraphics())  
                .setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);  
        graphics2d.drawImage(bufferedimage, 0, 0, null);  
        graphics2d.dispose();  
        
        ImageIO.write(img, "JPEG", srcfile);
    }  

	/**
	 * 鍘嬬缉鍥剧墖
	 * 
	 * @param srcImgPath
	 *            鍘熸枃浠跺叏璺緞 targetImgPath 鍘嬬缉鍚庣殑鏂囦欢淇濆瓨鍏ㄨ矾寰� targetWidth
	 *            鍘嬬缉鍚庣殑瀹藉害 targetHeight 鍘嬬缉鍚庣殑楂樺害
	 */
	public synchronized void reduceImg(File srcfile, String targetImgPath,
			int targetWidth, int targetHeight) throws Exception {

		if (!srcfile.exists())
			return;
		Image src = ImageIO.read(srcfile);
		int width = src.getWidth(null);
		int height = src.getHeight(null);

		BufferedImage tag = new BufferedImage(targetWidth, targetHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics gra = tag.getGraphics();
		// 璁剧疆鑳屾櫙鑹�
		gra.setColor(Color.white);
		gra.fillRect(0, 0, targetWidth, targetHeight);
		if (width <= targetWidth && height <= targetHeight) {
			gra.drawImage(
					src.getScaledInstance(width, height, Image.SCALE_SMOOTH),
					(targetWidth - width) / 2, (targetHeight - height) / 2,
					null);
		} else {
			float wh = (float) width / (float) height;
			if (wh > 1) {
				float tmp_heigth = (float) targetWidth / wh;
				float y = ((float) targetHeight - tmp_heigth) / 2;
				// tag = new BufferedImage(targetWidth,(int) tmp_heigth,
				// BufferedImage.TYPE_INT_RGB);
				gra.drawImage(src.getScaledInstance(targetWidth,
						(int) tmp_heigth, Image.SCALE_SMOOTH), 0, (int) y, null);
			} else {
				float tmp_width = (float) targetHeight * wh;
				float x = ((float) targetWidth - tmp_width) / 2;
				// tag = new BufferedImage((int) tmp_width,targetHeight,
				// BufferedImage.TYPE_INT_RGB);
				gra.drawImage(src.getScaledInstance((int) tmp_width,
						targetHeight, Image.SCALE_SMOOTH), (int) x, 0, null);
			}
		}

		FileOutputStream out = new FileOutputStream(targetImgPath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		out.close();
	}

	
	public static void main(String[] args) throws Exception {
		
		ImgUtils imgUtils = new ImgUtils();
		
		//imgUtils.reduceImg("F:\\test\\20120802194308890.jpg", "F:\\test\\test.jpg", 500, 300);
		byte[] m = imgUtils.reduceImg("e:\\zhili_pic\\face\\20121203103526033.jpg", 360, 270);
		
		System.out.println(m.length);
		//ImgUtils.rotateImg("F:\\test\\201207192001454.jpg", -90);
	}
	/**
	 * 娣诲姞鍥剧墖姘村嵃
	 * 
	 * @param markImgPath
	 *            姘村嵃鏂囦欢鍏ㄨ矾寰� targetImgPath 鐩爣鏂囦欢鍏ㄨ矾寰�
	 */
	public synchronized void imgMark(String markImgPath, String targetImgPath)
			throws Exception {

		File srcfile = new File(targetImgPath);
		if (!srcfile.exists())
			return;
		Image targetImg = ImageIO.read(srcfile);
		int wideth = targetImg.getWidth(null);
		int height = targetImg.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		g.drawImage(targetImg, 0, 0, wideth, height, null);
		File _filebiao = new File(markImgPath);
		Image markImg = ImageIO.read(_filebiao);
		int markImgWidth = markImg.getWidth(null);
		int markImgHeight = markImg.getHeight(null);
		Double pointWidth = DoubleUtil.mul(wideth, 0.05);
		Double pointHeight = DoubleUtil.mul(height, 0.05);
		g.drawImage(markImg, wideth - markImgWidth - pointWidth.intValue(),
				height - markImgHeight - pointHeight.intValue(), markImgWidth,
				markImgHeight, null);
		g.dispose();
		FileOutputStream out = new FileOutputStream(targetImgPath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}

	/**
	 * 娣诲姞鏂囧瓧姘村嵃
	 * 
	 * @param markText
	 *            姘村嵃鏂囧瓧 targetImgPath 鐩爣鏂囦欢鍏ㄨ矾寰�
	 */
	public synchronized void textMark(String markText, String targetImgPath)
			throws Exception {
		File _file = new File(targetImgPath);
		Image targetImg = ImageIO.read(_file);
		int wideth = targetImg.getWidth(null);
		int height = targetImg.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		Double pointWidth = DoubleUtil.mul(wideth, 0.05);
		Double pointHeight = DoubleUtil.mul(height, 0.95);
		g.drawImage(targetImg, 0, 0, wideth, height, null);
		g.setColor(Color.GREEN);
		g.setFont(new Font("瀹嬩綋", Font.BOLD, 20));
		g.drawString(markText, pointWidth.intValue(), pointHeight.intValue());
		g.dispose();
		FileOutputStream out = new FileOutputStream(targetImgPath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}

}
