//  Copyright (C) 2012 Wolfgang Beer. All Rights Reserved.
//  email wolfgang@smartlab.at
//  This file is part of the Kids Paint software.
//
package at.smartlab.kids.paint;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;

import java.util.ArrayList;

public class Settings {

    private static final ArrayList<String> clips = new ArrayList<>();
	
	private final static Settings instance = new Settings();

    private Bitmap bgclip;

    public static Settings getInstance() { 
    	return instance; 
    }
    
    private int strokeColor;
    
    private int strokeWidth;

    private boolean fillMode = false;

    public ArrayList<ColorPath> _graphics = new ArrayList<ColorPath>();


    
    private Settings() {
    	strokeWidth = 20;
    	strokeColor = Color.GREEN;
    }
	
	public Paint getPaint() {
		Paint strokePaint = new Paint();  
    	strokePaint.setDither(true);  
    	strokePaint.setColor(strokeColor);  
    	strokePaint.setStyle(Paint.Style.STROKE);  
    	strokePaint.setStrokeJoin(Paint.Join.ROUND);  
    	strokePaint.setStrokeCap(Paint.Cap.ROUND);  
    	strokePaint.setStrokeWidth(strokeWidth);  
		return strokePaint;
	}
	
	public void setColor(int color) {
		this.strokeColor = color;
	}
	
	public void setStrokeWidth(int width) {
		this.strokeWidth = width;
	}

	public int getStrokeWidth() {
		return this.strokeWidth;
	}

    public ArrayList<String> getClips() {
        return clips;
    }

    public void addClip(String clip) {
        clips.add(clip);
    }

    public Bitmap getClip() {
        return bgclip;
    }

    public void setClip(Bitmap c) {
        this.bgclip = c;
    }

    public boolean isFillMode() {
        return fillMode;
    }

    public void setFillMode(boolean fillMode) {
        this.fillMode = fillMode;
    }
}
