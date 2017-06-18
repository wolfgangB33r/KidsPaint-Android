//  Copyright (C) 2012 Wolfgang Beer. All Rights Reserved.
//  email wolfgang@smartlab.at
//  This file is part of the Kids Paint software.
//
package at.smartlab.kids.paint;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import at.smartlab.kids.paint.ToolCmdListener.CMD;

public class ToolsFragment extends Fragment {
	
	public int PINK = -36427;
	
	private int strokeWidth = 3;
	
	private View[] brushes = new View[9];
	
	private View[] thickness = new View[3];

    private View clear;

    private View save;

	private ToolCmdListener cmdListener;

    private AsyncTask task;

    private void switchColor(int color) {

        switch(color) {
            case Color.WHITE:
                Settings.getInstance().setColor(Color.WHITE);
                brushes[0].setBackgroundColor(Color.LTGRAY);
                break;
            case Color.GRAY:
                Settings.getInstance().setColor(Color.GRAY);
                brushes[1].setBackgroundColor(Color.LTGRAY);
                break;
            case Color.BLACK:
                Settings.getInstance().setColor(Color.BLACK);
                brushes[2].setBackgroundColor(Color.LTGRAY);
                break;
            case Color.RED:
                Settings.getInstance().setColor(Color.RED);
                brushes[3].setBackgroundColor(Color.LTGRAY);
                break;
            case Color.GREEN:
                Settings.getInstance().setColor(Color.GREEN);
                brushes[4].setBackgroundColor(Color.LTGRAY);
                break;
            case Color.YELLOW:
                Settings.getInstance().setColor(Color.YELLOW);
                brushes[5].setBackgroundColor(Color.LTGRAY);
                break;
            case Color.BLUE:
                Settings.getInstance().setColor(Color.BLUE);
                brushes[6].setBackgroundColor(Color.LTGRAY);
                break;
            case -36427:
                Settings.getInstance().setColor(Color.rgb(255, 113, 181));
                brushes[7].setBackgroundColor(Color.LTGRAY);
                break;
            case Color.MAGENTA:
                Settings.getInstance().setColor(Color.MAGENTA);
                brushes[8].setBackgroundColor(Color.LTGRAY);
                break;
        }
    }

	private void changeBrush(int color) {
		
		for(View v : brushes) {
			v.setBackgroundColor(Color.WHITE);
		}
		Settings.getInstance().setColor(color);

        switchColor(color);
	}

    private void switchThickness(int size) {

        switch(size) {
            case 3:
                thickness[0].setBackgroundColor(Color.LTGRAY);
                break;
            case 7:
                thickness[1].setBackgroundColor(Color.LTGRAY);
                break;
            case 20:
                thickness[2].setBackgroundColor(Color.LTGRAY);
                break;
        }
    }
	
	private void changeThickness(int size) {
		strokeWidth = size;
		for(View v : thickness) {
			v.setBackgroundColor(Color.WHITE);
		}
		
		Settings.getInstance().setStrokeWidth(strokeWidth);

        switchThickness(size);
	}
	
	private void triggerCmd(CMD c) {
		if(cmdListener != null) {
			cmdListener.trigger(c);
		}
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	final View view = inflater.inflate(R.layout.tools_fragment, container, false);
    	
    	clear = view.findViewById(R.id.canvas);
    	clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

				triggerCmd(ToolCmdListener.CMD.Clear);
			}
        });
    	
    	save = view.findViewById(R.id.save);
    	save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

				triggerCmd(ToolCmdListener.CMD.Save);
			}
        });
    	
    	
    	thickness[0] = (ImageView) view.findViewById(R.id.thin);
    	thickness[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeThickness(3);
			}
        	
        });
    	
    	thickness[1] = (ImageView) view.findViewById(R.id.medium);
    	thickness[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeThickness(7);
			}
        	
        });
    	
    	thickness[2] = (ImageView) view.findViewById(R.id.thick);
    	thickness[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeThickness(20);
			}
        	
        });
    	
    	
    	brushes[0] = (ImageView) view.findViewById(R.id.eraseButton);
    	brushes[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.WHITE);
			}
        	
        });
      
    	brushes[1] = (ImageView) view.findViewById(R.id.greyBrushButton);
    	brushes[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.GRAY);
			}
        	
        });
        
    	brushes[2] = (ImageView) view.findViewById(R.id.blackBrushButton);
    	brushes[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.BLACK);
			}
        	
        });
        
    	brushes[3] = (ImageView) view.findViewById(R.id.redBrushButton);
    	brushes[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.RED);
			}
        	
        });
        
    	brushes[4] = (ImageView) view.findViewById(R.id.greenBrushButton);
    	brushes[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.GREEN);
			}
        	
        });
        
    	brushes[5] = (ImageView) view.findViewById(R.id.yellowBrushButton);
    	brushes[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.YELLOW);
			}
        	
        });
        
    	brushes[6] = (ImageView) view.findViewById(R.id.blueBrushButton);
    	brushes[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.BLUE);
			}
        	
        });
        
    	brushes[7] = (ImageView) view.findViewById(R.id.pinkBrushButton);
    	brushes[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.rgb(255, 113, 181));
			}
        	
        });
        
    	brushes[8] = (ImageView) view.findViewById(R.id.purpleBrushButton);
    	brushes[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				changeBrush(Color.MAGENTA);
			}
        	
        });

        ImageView share = (ImageView) view.findViewById(R.id.share);
        share.setBackgroundColor(Color.WHITE);
        share.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                triggerCmd(ToolCmdListener.CMD.Share);
                return true;
            }
        });

        ImageView clipbutton = (ImageView)view.findViewById(R.id.selectclip);
        clipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // first clear canvas
                triggerCmd(ToolCmdListener.CMD.Clear);
                // now select a clip
                AssetManager mgr = view.getContext().getAssets();
                try {
                    AssetManager assetManager = view.getContext().getAssets();
                    Random r = new Random();
                    int rN = r.nextInt(Settings.getInstance().getClips().size());
                    InputStream istr = assetManager.open(Settings.getInstance().getClips().get(rN));
                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    Settings.getInstance().setClip(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        ImageView mandalaButton = (ImageView)view.findViewById(R.id.mandala);
        mandalaButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

                Display display = view.getDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                task = new DownloadFilesTask().execute("http://www.pocketvnc.com/mandala/mandala.php?w=" + Integer.toString(width) + "&h=" + Integer.toString(height));
            }
        });
/*
        final ImageView fillButton = (ImageView)view.findViewById(R.id.fill);
        fillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.getInstance().setFillMode(!Settings.getInstance().isFillMode());
                updateFillButtonIcon(fillButton);
            }
        });
*/
        return view;
    }

    private void updateFillButtonIcon(ImageView fillButton) {


    }

    @Override
    public void onResume() {
        for(View v : brushes) {
            v.setBackgroundColor(Color.WHITE);
        }
        for(View v : thickness) {
            v.setBackgroundColor(Color.WHITE);
        }
        clear.setBackgroundColor(Color.WHITE);
        save.setBackgroundColor(Color.WHITE);
        switchColor(Settings.getInstance().getPaint().getColor());
        switchThickness(Settings.getInstance().getStrokeWidth());
        super.onResume();
    }

    public void setToolCmdListener(ToolCmdListener l) {
    	this.cmdListener = l;
    }


    private class DownloadFilesTask extends AsyncTask<String, Void, Void> {


        protected Void doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap mandala = BitmapFactory.decodeStream(input);
                Settings.getInstance().setClip(mandala);
            } catch (IOException e) {
                // Log exception
                return null;
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }


}

