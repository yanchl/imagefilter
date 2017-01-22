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

import java.awt.image.*;

/**
 * A filter which inverts the RGB channels of an image.
 */
public class InvertFilter extends PointFilter {
	
	private float invertness = 0f;
	
	public void setInvert(float invertness){
		if(invertness >1){
			this.invertness = 1;
		}else if(invertness <0){
			this.invertness =0;
		}else{
			this.invertness = invertness;
		}
	}

	public InvertFilter() {
		canFilterIndexColorModel = true;
	}

	public int filterRGB(int x, int y, int rgb) {
//		int a = rgb & 0xff000000;
//		return a | (~rgb & 0x00ffffff);
		
		int a = rgb & 0xff000000;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
		
		r = (int)((~r & 0xff ) * invertness + r*(1-invertness));
		g = (int)((~g & 0xff ) * invertness + g*(1-invertness));
		b = (int)((~b & 0xff ) * invertness + b*(1-invertness));
		
		return a | (r << 16) | (g << 8) | b;
	}

	public String toString() {
		return "Colors/Invert";
	}
}

