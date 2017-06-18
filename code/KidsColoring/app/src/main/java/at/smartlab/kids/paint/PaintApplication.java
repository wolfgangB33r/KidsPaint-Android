package at.smartlab.kids.paint;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;


import java.io.File;
import java.io.IOException;

public class PaintApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Kids Coloring", "Application Start");
        loadClips();
    }

    private void loadClips() {
        AssetManager mgr = getAssets();
        Log.d("Kids Coloring", "Listing Clips");
        try {
            String list[] = mgr.list("clips");
            if (list != null) {
                for (int i = 0; i < list.length; ++i) {
                    Log.d("Assets:", "clips" + File.separatorChar + list[i]);
                    Settings.getInstance().addClip("clips" + File.separatorChar + list[i]);
                }
            }
        } catch (IOException e) {
            Log.d("Kids Coloring", e.getMessage());
        }
    }

}

