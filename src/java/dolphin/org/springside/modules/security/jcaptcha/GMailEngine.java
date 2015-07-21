/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: GMailEngine.java 453 2009-09-12 17:57:34Z calvinxiu $
 */
package org.springside.modules.security.jcaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * JCaptcha验证码图片生成引擎,仿照JCaptcha2.0编写类似GMail验证码的样式.
 * 
 * @author calvin
 */
public class GMailEngine extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		int minWordLength = 4;
		int maxWordLength = 4;
		int fontSize = 50;
		int imageWidth = 80;
		int imageHeight = 30;

		//word generator
		//WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
		WordGenerator wgen =new RandomWordGenerator("0123456789");
		RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(new int[] { 0, 100 }, new int[] { 0, 100 },new int[] { 0, 100 });
		// 文字显示的个数
		TextPaster textPaster = new RandomTextPaster(minWordLength,maxWordLength, cgen, true);
	    // 图片的大小
		BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(imageWidth,imageHeight,Color.white,new Color(130, 240, 250));
		// 字体格式
		Font[] fontsList = new Font[] { new Font("Arial", 0, 10),new Font("Tahoma", 0, 10), new Font("Verdana", 0, 10), };
		// 文字的大小
		FontGenerator fontGenerator = new RandomFontGenerator(new Integer(20),new Integer(20), fontsList);

		WordToImage wordToImage = new ComposedWordToImage(fontGenerator,backgroundGenerator, textPaster);
		this.addFactory(new GimpyFactory(wgen, wordToImage));



//		//word2image components
//		TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength,
//				new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11),
//						new Color(23, 67, 172) }), new TextDecorator[] {});
//		BackgroundGenerator background = new UniColorBackgroundGenerator(imageWidth, imageHeight, Color.white);
//		FontGenerator font = new RandomFontGenerator(fontSize, fontSize, new Font[] {
//				new Font("nyala", Font.BOLD, fontSize), new Font("Bell MT", Font.PLAIN, fontSize),
//				new Font("Credit valley", Font.BOLD, fontSize) });
//
//		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
//		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
//		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});
//
//		WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef,
//				postDef);
//		addFactory(new GimpyFactory(dictionnaryWords, word2image));
	}

}
