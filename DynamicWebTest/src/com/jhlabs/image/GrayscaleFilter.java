/*
Copyright 2006 Jerry Huxtable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.jhlabs.image;

import java.awt.*;
import java.awt.image.*;

/**
 * A filter which converts an image to grayscale using the NTSC brightness calculation.
 */
public class GrayscaleFilter extends PointFilter {
	
	private float rate = 0f;
	
	public void setRate(float rate){
		if(rate>1){
			this.rate = 1;	
		}else if(rate <0){
			this.rate =0;
		}else{
			this.rate = rate;
		}
	}

	public GrayscaleFilter() {
		canFilterIndexColorModel = true;
	}

	public int filterRGB(int x, int y, int rgb) {
		int a = rgb & 0xff000000;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
//		rgb = (r + g + b) / 3;	// simple average
		rgb = (r * 77 + g * 151 + b * 28) >> 8;	// NTSC luma
		
		r =(int) ((1-rate) * r +rate*rgb);
		g =(int) ((1-rate) * g +rate*rgb);
		b =(int) ((1-rate) * b +rate*rgb);
		
		
		return a | (r << 16) | (g << 8) | b;
	}

	public String toString() {
		return "Colors/Grayscale";
	}

}


