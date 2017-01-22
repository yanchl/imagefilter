package com.wq.filter;


public class IntIntegralImage {
	// sum index tables
	private int[] sum;
	private int[] squaresum;
	// image
	private byte[] image;
	private int width;
	private int height;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public int getBlockSum(int x, int y, int m, int n) {
		int swx = x + n/2;
		int swy = y + m/2;
		int nex = x-n/2-1;
		int ney = y-m/2-1;
		int sum1, sum2, sum3, sum4;
		if(swx >= width) {
			swx = width - 1;
		}
		if(swy >= height) {
			swy = height - 1;
		}
		if(nex < 0) {
			nex = 0;
		}
		if(ney < 0) {
			ney = 0;
		}
		sum1 = sum[ney*width+nex];
		sum4 = sum[swy*width+swx];
		sum2 = sum[swy*width+nex];
		sum3 = sum[ney*width+swx];
		return ((sum1 + sum4) - sum2 - sum3);
	}
	
	public int getBlockSquareSum(int x, int y, int m, int n) {		
		int swx = x + n/2;
		int swy = y + m/2;
		int nex = x-n/2-1;
		int ney = y-m/2-1;
		int sum1, sum2, sum3, sum4;
		if(swx >= width) {
			swx = width - 1;
		}
		if(swy >= height) {
			swy = height - 1;
		}
		if(nex < 0) {
			nex = 0;
		}
		if(ney < 0) {
			ney = 0;
		}
		sum1 = squaresum[ney*width+nex];
		sum4 = squaresum[swy*width+swx];
		sum2 = squaresum[swy*width+nex];
		sum3 = squaresum[ney*width+swx];
		return ((sum1 + sum4) - sum2 - sum3);
	}

//	@Override
	public void process(int width, int height) {
		this.width = width;
		this.height = height;
		sum = new int[width*height];
		squaresum = new int[width*height];
		// rows
		int p1=0, p2=0, p3=0, p4;
		int offset = 0, uprow=0, leftcol=0;
		int s=0;
		for(int row=0; row<height; row++ ) {
			offset = row*width;
			uprow = row-1;
			for(int col=0; col<width; col++) {
				leftcol=col-1;
				p1=image[offset]&0xff;// p(x, y)
				p2=(leftcol<0) ? 0:sum[offset-1]; // p(x-1, y)
				p3=(uprow<0) ? 0:sum[offset-width]; // p(x, y-1);
				p4=(uprow<0||leftcol<0) ? 0:sum[offset-width-1]; // p(x-1, y-1);
				s = sum[offset]= p1+p2+p3-p4;
				squaresum[offset]=s*s;
				// System.out.print("\t[" + offset+"]=" + s);
				offset++;
			}
			// System.out.println();
		}
	}
	
	public static void main(String[] args) {
		byte[] data = new byte[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		IntIntegralImage ii = new IntIntegralImage();
		ii.setImage(data);
		ii.process(7, 3);
		
		int sum = ii.getBlockSum(3, 2, 3, 3);
		System.out.println("sum = " + sum);
	}
}

