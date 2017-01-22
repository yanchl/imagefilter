package com.wq.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jhlabs.image.BoxBlurFilter;
import com.jhlabs.image.ContrastFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.LensBlurFilter;
import com.jhlabs.image.OpacityFilter;
import com.jhlabs.image.SaturationFilter;
import com.jhlabs.image.VariableBlurFilter;
import com.wq.filter.FastBlurFilter;
import com.wq.filter.SepiaToneFilter;

public class MyServlet extends HttpServlet {
	public static final String urlPrx = "http://10.1.1.135:8080/WebContent/";
//	private static final String inpath ="/Users/shuuseiyang/wxspace/album/filter/start0.png";
	private static final String inpath ="/Users/shuuseiyang/wxspace/album/filter/image.jpg";
	private static final String outprx = "../webapps/WebContent/";
	
	private static int index = 0;
	private static boolean iswxtest = true;
	public void doGet(HttpServletRequest request,
			 HttpServletResponse response){
		if(iswxtest){
			try {
				System.out.println("read img");
				BufferedImage bin = ImageIO.read(new File(inpath));
				System.out.println("response img");
				ImageIO.write(filterImage(bin, request), "png", response.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public BufferedImage filterImage(BufferedImage bin, HttpServletRequest request){
		System.out.println("filting img");
		BufferedImage oimg = null;
		float grayscale = getParams(request,"grayscale");
		float sepia = getParams(request,"sepia");
		float brightness =getParams(request,"brightness");
		float contrast = getParams(request,"contrast");
		float invert = getParams(request,"invert");
		float opacity = getParams(request,"opacity");
		float saturation = getParams(request, "saturation");
		float blur =getParams(request, "blur");
		// 灰度
		GrayscaleFilter grayscaleFilter = new GrayscaleFilter();
		grayscaleFilter.setRate(grayscale);
		
		// 复古
		SepiaToneFilter sepiaFilter = new SepiaToneFilter();
		sepiaFilter.setNoise(sepia);
		
		// 亮度 和 对比度
		ContrastFilter contrastFilter = new ContrastFilter();
		contrastFilter.setBrightness(brightness);
		contrastFilter.setContrast(contrast);
		
		// 反色
		InvertFilter invertFilter = new InvertFilter();
		invertFilter.setInvert(invert);
		
		// 透明度
		OpacityFilter opacityFilter = new OpacityFilter();
		opacityFilter.setOpacity((int)(opacity*255));
		
		// 饱和度
		SaturationFilter saturationFilter = new SaturationFilter();
		saturationFilter.setAmount(saturation);
		
		// 模糊
		BoxBlurFilter boxblurFilter = new BoxBlurFilter();
		boxblurFilter.setIterations(blur>1?2:1);
		boxblurFilter.setRadius(blur);
		
		FastBlurFilter fastblurFilter = new FastBlurFilter();
		fastblurFilter.setRadio((int)blur);
		
		VariableBlurFilter variableblurFilter = new VariableBlurFilter ();
		variableblurFilter.setRadius((int)blur);
		
		LensBlurFilter lensBlurFilter = new LensBlurFilter ();
		lensBlurFilter.setRadius((int) blur);

		try{
			System.out.println("filting grayscale");
			oimg = grayscaleFilter.filter(bin, null);
			
			System.out.println("filting sepia");
			oimg = sepiaFilter.filter(oimg, null);
			
			System.out.println("filting saturation");
			oimg = saturationFilter.filter(oimg, null);
			
			System.out.println("filting invert");
			oimg = invertFilter.filter(oimg, null);
			
			
			System.out.println("filting opacity");
			oimg = opacityFilter.filter(oimg, null);
			
			System.out.println("filting contrast");
			oimg = contrastFilter.filter(oimg,null);
			
			System.out.println("filting boxblur");
			oimg = boxblurFilter.filter(oimg,null);

		}catch(Exception e){
			System.out.println("failed");
			oimg = bin;
		}
				
		return oimg;
	}
	
	
	private float getParams(HttpServletRequest request, String param){
		float dftrst = 0;
		switch(param){
			case "brightness":
			case "contrast":
			case "saturation":
				dftrst = 1;
			break;
		}
		float pf = parseFloat(request.getParameter(param));
		return pf ==-1? dftrst: pf;
	}
	
	private float parseFloat(String sfloat){
		return sfloat ==null ? -1: Float.parseFloat(sfloat);
	}
	
	private void sendResult(HttpServletResponse response, String url){
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8"); 
		String jsonStr = "{\"data\":\""+url+"\"}"; 
		PrintWriter out = null; 
		try { 
			out = response.getWriter();
			out.write(jsonStr); 
		} catch (IOException e) {
			e.printStackTrace(); 
		} finally {
			if (out != null) { 
				out.close(); 
			}
		}
	}
}
