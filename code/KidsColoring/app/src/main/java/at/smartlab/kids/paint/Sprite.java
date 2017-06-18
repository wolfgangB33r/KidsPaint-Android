//  Copyright (C) 2012 Wolfgang Beer. All Rights Reserved.
//  email wolfgang@smartlab.at
//  This file is part of the Kids Paint software.
//
package at.smartlab.kids.paint;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {

	private int rotation;
	
	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	private Bitmap image;
	
	private boolean visible;
	
	
	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public Sprite(Bitmap b) {
		image = b;
		this.x = 0;
		this.y = 0;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
	}
	

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public void drawTo(Canvas c) {
		if(visible) {
			c.drawBitmap(image, x, y, null);
		}
	}
	
}
