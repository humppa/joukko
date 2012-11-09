package fi.starck.joukko;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class JoukkoActivity extends Activity {
    private GLSurfaceView surfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surfaceView = new GLSurface(this);
        setContentView(surfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
    }
}
