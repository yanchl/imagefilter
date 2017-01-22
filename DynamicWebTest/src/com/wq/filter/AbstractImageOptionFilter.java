package com.wq.filter;

import java.awt.image.BufferedImage;

public abstract class AbstractImageOptionFilter {
	
	public abstract BufferedImage process(BufferedImage image);
	
	protected int[] getRGB(BufferedImage image, int x, int y,int width,int height,int[] pixels){
		int type = image.getType();
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			return (int [])image.getRaster().getDataElements( x, y, width, height, pixels );
		return image.getRGB( x, y, width, height, pixels, 0, width );
	}
	
	protected void setRGB(BufferedImage image, int x, int y,int width,int height,int[] pixels){
		int type = image.getType();
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			image.getRaster().setDataElements( x, y, width, height, pixels );
		else
			image.setRGB( x, y, width, height, pixels, 0, width );
	}

}
