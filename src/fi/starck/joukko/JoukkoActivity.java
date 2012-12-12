package fi.starck.joukko;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

/**
 * Chess game board in OpenGL ES.
 *
 * @author Tuomas Starck
 */
public class JoukkoActivity extends Activity {
    private final String TAG = "ACT";

    private GLSurfaceView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new GLView(this);

        /* Save battery by rendering only when specifically requested.
         */
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        setContentView(view);
    }

    @Override
    public void onBackPressed() {
        ((GLView) view).undo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
        Log.i(TAG, "Activity did pausing!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
        Log.i(TAG, "Activity did resuming!");
    }
}
