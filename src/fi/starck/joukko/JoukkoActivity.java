package fi.starck.joukko;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class JoukkoActivity extends Activity {
    private GLSurfaceView surface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surface = new GLSurface(this);

        /* Save battery by rendering only when specifically requested.
         */
        surface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(surface);
    }

    @Override
    protected void onResume() {
        super.onResume();
        surface.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        surface.onPause();
    }
}
