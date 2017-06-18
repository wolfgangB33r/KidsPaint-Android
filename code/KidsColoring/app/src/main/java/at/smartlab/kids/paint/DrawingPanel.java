//  Copyright (C) 2012 Wolfgang Beer. All Rights Reserved.
//  email wolfgang@smartlab.at
//  This file is part of the Kids Paint software.
//

package at.smartlab.kids.paint;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingPanel extends SurfaceView implements SurfaceHolder.Callback {
	int[] colors = new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.WHITE };
	
	int bgColor = Color.WHITE;
	Paint bgPaint;

	private DrawingThread thread;  
	ColorPath[] a_paths;
    private ArrayList<FillPoint> fillPoints = new ArrayList<FillPoint>();
	
	Sprite pinsel1;
	
	
	public DrawingPanel(Context context) {
		super(context);
	}
	
	public DrawingPanel(Context context, AttributeSet as) {
		super(context, as);
		bgPaint = new Paint();
		bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		bgPaint.setStrokeWidth(3);
		bgPaint.setAntiAlias(true);
		bgPaint.setColor(bgColor);
		
		pinsel1 = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.brush_grey));
		pinsel1.setVisible(false);
		
		a_paths = new ColorPath[10];
		
		SurfaceHolder surfaceholder = getHolder();
		
		surfaceholder.addCallback(this);

		setFocusable(true);
	}
	
	public void erase() {
		Log.d("Kritzi", "erase");
		synchronized (getHolder()) {  
			Settings.getInstance()._graphics.clear();
			this.invalidate();
		}
	}

	
	@Override  
	public boolean onTouchEvent(MotionEvent event) {  
		synchronized (getHolder()) {  
			//Log.d("Kritzi", "id" + event.getPointerId(0));
            if(Settings.getInstance().isFillMode()) { // fill mode
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    fillPoints.add(new FillPoint(new Point(x, y), Settings.getInstance().getPaint()));
                    Log.d("Kritzi", "fill point: " + x + ", " + y);
                }
            }
            else { // draw path
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pinsel1.setVisible(true);
                    pinsel1.setX((int) event.getX());
                    pinsel1.setY((int) event.getY() - pinsel1.getHeight());
                    Path path = new Path();
                    path.moveTo(event.getX(), event.getY());

                    a_paths[0] = new ColorPath(path, Settings.getInstance().getPaint());
                    Settings.getInstance()._graphics.add(a_paths[0]);
                    a_paths[0].path.lineTo(event.getX() + 1, event.getY() + 1);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    a_paths[0].path.lineTo(event.getX(), event.getY());
                    pinsel1.setVisible(true);
                    pinsel1.setX((int) event.getX());
                    pinsel1.setY((int) event.getY() - pinsel1.getHeight());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pinsel1.setVisible(false);
                }
            }
		}
	    return true;  
	}  
	/*
	@Override  
	public void onDraw(Canvas canvas) {  
		if(canvas != null) {
			canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), bgPaint);
		}
	} 
	*/ 

	@Override
	public void surfaceChanged(SurfaceHolder tholder, int format, int width, int height) {
	    thread.setSurfaceSize(width, height);
	    thread.holder = tholder;
	}
	
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message m) {
			// Do some handle thing
		}
	};

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new DrawingThread(holder, getContext(), handler);
	    thread.running = true;
	    thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	    boolean retry = true;
	    thread.running = false;
	    while(retry) {
	        try {
	            thread.join();
	            retry = false;
	        } catch (InterruptedException e) {
	        }
	    }
	}

	public Thread getThread() {
	    return thread;
	}

	
	public class DrawingThread extends Thread {
	    private SurfaceHolder holder;
	 
	    public boolean running;
	    public boolean created;

	    public int canvasheight;
	    public int canvaswidth;

        Bitmap lookup;

        public DrawingThread(SurfaceHolder tholder, Context tcontext, Handler thandler) {
	        holder = tholder;
	        canvaswidth = 1;
	        canvasheight = 1;
	        running = false;
	        created = false;
	    }

	    @Override
	    public void run() {
	        Log.d("SurfaceView Test", "Drawing thread run");
	        while(running) {
	            Canvas canvas = null;
	            try {
	                canvas = holder.lockCanvas();
	                synchronized(holder) {
	                    // update object states
	                    // get user input gestures
	                    drawing(canvas);
	                }
	            } finally {
	                if(canvas != null) {
	                    holder.unlockCanvasAndPost(canvas);
	                }
	            }
	        }
	    }

	    private void drawing(Canvas canvas) {
	    	if(canvas != null) {
		    	canvas.drawColor(Color.WHITE);
                if(Settings.getInstance().getClip() != null) {
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setFilterBitmap(true);
                    paint.setDither(true);
                    Bitmap b = Settings.getInstance().getClip();
                    canvas.drawBitmap(b, new Rect(0, 0, b.getWidth(), b.getHeight()), canvas.getClipBounds(), paint);
                }
		    	
		        // draw paths
		        for (ColorPath path : Settings.getInstance()._graphics) {
		    	    //canvas.drawPoint(graphic.x, graphic.y, mPaint);  
		        	canvas.drawPath(path.path, path.paint);  
		    	}

                // draw pens
		        pinsel1.drawTo(canvas);
	    	}
	    }

        public void setSurfaceSize(int width, int height) {
	        synchronized(holder) {
	            canvaswidth = width;
	            canvasheight = height;
	        }
	    }
	    
	    
	}

	 public final Bitmap getScreenCopy() {
		 Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
	     Canvas tCanvas = new Canvas(bitmap);
	     tCanvas.drawColor(Color.WHITE);
	    	
	     // draw paths
	     for (ColorPath path : Settings.getInstance()._graphics) {
	    	 //canvas.drawPoint(graphic.x, graphic.y, mPaint);  
	    	 tCanvas.drawPath(path.path, path.paint);  
	    }  
	        
	     return bitmap;
	}
	
}
