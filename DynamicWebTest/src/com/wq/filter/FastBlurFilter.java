package com.wq.filter;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

import com.jhlabs.image.AbstractBufferedImageOp;

public class FastBlurFilter extends AbstractImageOptionFilter implements BufferedImageOp{
	// 窗口半径大小
		private int xr;
		private int yr;

		public FastBlurFilter() {
			xr = 1;
			yr = 1;
		}
		
		public void setRadio(int r){
			xr = r;
			yr = r;
		}

		public int getXr() {
			return xr;
		}

		public void setXr(int xr) {
			this.xr = xr;
		}

		public int getYr() {
			return yr;
		}

		public void setYr(int yr) {
			this.yr = yr;
		}

		@Override
		public BufferedImage process(BufferedImage image) {
			long time = System.currentTimeMillis();
			int width = image.getWidth();
			int height = image.getHeight();
			// get image data
			int[] pixels = new int[width * height];
			int[] outPixels = new int[width * height];
			getRGB(image, 0, 0, width, height, pixels);
			int size = (xr * 2 + 1) * (yr * 2 + 1);
			int r = 0, g = 0, b = 0;
			
			// per-calculate integral image
			byte[] R = new byte[width*height];
			byte[] G = new byte[width*height];
			byte[] B = new byte[width*height];
			getRGB(width, height, pixels, R, G, B);
			IntIntegralImage rii = new IntIntegralImage();
			rii.setImage(R);
			rii.process(width, height);
			IntIntegralImage gii = new IntIntegralImage();
			gii.setImage(G);
			gii.process(width, height);
			IntIntegralImage bii = new IntIntegralImage();
			bii.setImage(B);
			bii.process(width, height);

			for (int row = yr; row < height - yr; row++) {
				for (int col = xr; col < width - xr; col++) {
					int sr = rii.getBlockSum(col, row, (yr * 2 + 1), (xr * 2 + 1));
					int sg = gii.getBlockSum(col, row, (yr * 2 + 1), (xr * 2 + 1));
					int sb = bii.getBlockSum(col, row, (yr * 2 + 1), (xr * 2 + 1));
					r = sr / size;
					g = sg / size;
					b = sb / size;
					outPixels[row * width + col] = (0xff << 24) | (r << 16) | (g << 8) | b;
				}
			}
			System.out.println("FastBlurFilter ->> time duration : " + (System.currentTimeMillis() - time));
			BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			setRGB(dest, 0, 0, width, height, outPixels);
			return dest;
		}
		
		/** Returns the red, green and blue planes as 3 byte arrays. */
		public void getRGB(int width, int height, int[] pixels, byte[] R, byte[] G, byte[] B) {
			int c, r, g, b;
			for (int i=0; i < width*height; i++) {
				c = pixels[i];
				r = (c&0xff0000)>>16;
				g = (c&0xff00)>>8;
				b = c&0xff;
				R[i] = (byte)r;
				G[i] = (byte)g;
				B[i] = (byte)b;
			}
		}

		@Override
		public BufferedImage filter(BufferedImage src, BufferedImage dest) {
			// TODO Auto-generated method stub
			long time = System.currentTimeMillis();
			int width = src.getWidth();
			int height = src.getHeight();
			// get image data
			int[] pixels = new int[width * height];
			int[] outPixels = new int[width * height];
			getRGB(src, 0, 0, width, height, pixels);
			int size = (xr * 2 + 1) * (yr * 2 + 1);
			int r = 0, g = 0, b = 0;
			
			// per-calculate integral image
			byte[] R = new byte[width*height];
			byte[] G = new byte[width*height];
			byte[] B = new byte[width*height];
			getRGB(width, height, pixels, R, G, B);
			IntIntegralImage rii = new IntIntegralImage();
			rii.setImage(R);
			rii.process(width, height);
			IntIntegralImage gii = new IntIntegralImage();
			gii.setImage(G);
			gii.process(width, height);
			IntIntegralImage bii = new IntIntegralImage();
			bii.setImage(B);
			bii.process(width, height);

			for (int row = yr; row < height - yr; row++) {
				for (int col = xr; col < width - xr; col++) {
					int sr = rii.getBlockSum(col, row, (yr * 2 + 1), (xr * 2 + 1));
					int sg = gii.getBlockSum(col, row, (yr * 2 + 1), (xr * 2 + 1));
					int sb = bii.getBlockSum(col, row, (yr * 2 + 1), (xr * 2 + 1));
					r = sr / size;
					g = sg / size;
					b = sb / size;
					outPixels[row * width + col] = (0xff << 24) | (r << 16) | (g << 8) | b;
				}
			}
			System.out.println("FastBlurFilter ->> time duration : " + (System.currentTimeMillis() - time));
			if ( dest == null )
	            dest = createCompatibleDestImage( src, null );
			setRGB(dest, 0, 0, width, height, outPixels);
			return dest;
		}

		@Override
		public Rectangle2D getBounds2D(BufferedImage src) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RenderingHints getRenderingHints() {
			// TODO Auto-generated method stub
			return null;
		}
}
