package fi.starck.joukko;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

public class JoukkoActivity extends Activity {
    private final String TAG = "ACT";

    private GLSurfaceView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "Creation of activity!");

        view = new GLSurface(this);

        /* Save battery by rendering only when specifically requested.
         */
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }
}
