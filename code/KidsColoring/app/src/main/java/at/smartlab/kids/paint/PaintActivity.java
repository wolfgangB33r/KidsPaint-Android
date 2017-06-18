//  Copyright (C) 2012 Wolfgang Beer. All Rights Reserved.
//  email wolfgang@smartlab.at
//  This file is part of the Kids Paint software.
//
package at.smartlab.kids.paint;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;



public class PaintActivity extends Activity implements ToolCmdListener {

    public static final String TOOLS_TAG = "TOOLS";

	ToolsFragment tools;

    AsyncTask task;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,    
                                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        setContentView(R.layout.paint_activity);
        
        SurfaceView panel = (SurfaceView)findViewById(R.id.drawingPanel);


        ImageView toolsbutton = (ImageView)findViewById(R.id.toolbutton);
        toolsbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // hide tools
                ToolsFragment t = (ToolsFragment)getFragmentManager().findFragmentByTag(TOOLS_TAG);
                if (t != null) {
                    FragmentTransaction fT = getFragmentManager().beginTransaction();
                    fT.setCustomAnimations(R.animator.fadein, R.animator.fadeout, R.animator.fadein, R.animator.fadeout);

                    fT.remove(t);
                    fT.commitAllowingStateLoss();
                }
                else {
                    FragmentTransaction fT = getFragmentManager().beginTransaction();
                    fT.setCustomAnimations(R.animator.fadein, R.animator.fadeout, R.animator.fadein, R.animator.fadeout);

                    fT.add(R.id.tools_fragment_container, tools, TOOLS_TAG);
                    fT.commitAllowingStateLoss();
                }
            }
        });

        tools = (ToolsFragment) Fragment.instantiate(this, "at.smartlab.kids.paint.ToolsFragment");
        tools.setToolCmdListener(this);
    }

	@Override
	public void trigger(CMD c) {
		DrawingPanel panel = (DrawingPanel)findViewById(R.id.drawingPanel);
		if(panel == null) {
			return;
		}
		if(c == ToolCmdListener.CMD.Clear) {
			Settings.getInstance().setClip(null);
			panel.erase();
		}
		else if(c == ToolCmdListener.CMD.Save) {
			String fileName = "paint_" + Long.toString(GregorianCalendar.getInstance().getTimeInMillis()) + ".png";
			File file = new File(getExternalFilesDir(null), fileName);
			Bitmap bmp = panel.getScreenCopy();
			
			try {
			    OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			    bmp.compress(Bitmap.CompressFormat.PNG, 70, out);
			    out.close();
			} catch (IOException e) {
			   e.printStackTrace();
			}
			
			// open image
    		try {

    			Intent intent = new Intent();
	    		intent.setAction(android.content.Intent.ACTION_VIEW);
	    		intent.setDataAndType(Uri.fromFile(file), "image/png");
	    		startActivity(intent); 
			} catch(Exception e) {
				e.printStackTrace();
    		}
		}
        else if(c == ToolCmdListener.CMD.Share) {
            Bitmap icon = panel.getScreenCopy();
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "kids_coloring.jpg");
            Log.d("Kids Paint", Environment.getExternalStorageDirectory() + File.separator + "kids_coloring.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                fo.close();

                // and share it
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, "Share"));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}

    private class ToolsHideTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... n) {
            Fragment t = getFragmentManager().findFragmentByTag(TOOLS_TAG);
            if(t != null) {
                try {
                    Thread.sleep(5000);
                    FragmentTransaction fT = getFragmentManager().beginTransaction();
                    fT.setCustomAnimations(R.animator.fadein, R.animator.fadeout, R.animator.fadein, R.animator.fadeout);
                    Log.d("Paint", "Hide Tools");
                    fT.remove(t);
                    fT.commitAllowingStateLoss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }
}