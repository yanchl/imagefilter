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
 * A Filter to invert the alpha channel of an image. This is really only useful for inverting selections, where we only use the alpha channel.
 */
public class InvertAlphaFilter extends PointFilter {

	private float alpharate = 1f;
	public void setAlpha(float alf){
		if(alf > 1){
			this.alpharate = 1.0f;
		}else if(alf <0){
			this.alpharate = 0f;
		}else{
			this.alpharate = alf;
		}
	}
	public InvertAlphaFilter() {
		canFilterIndexColorModel = true;
	}

	public int filterRGB(int x, int y, int rgb) {
//		return rgb ^ 0xff000000;
		int ta = (rgb>>24) & 0xff;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
		
		ta = (int )((~ta & 0xff) * alpharate + ta * (1-alpharate));
		
		return (ta<<24) | (r << 16) | (g << 8) | b;
	}

	public String toString() {
		return "Alpha/Invert";
	}
}
