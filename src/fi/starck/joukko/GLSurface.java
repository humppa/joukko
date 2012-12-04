package fi.starck.joukko;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import fi.starck.sakki.board.Chess;

class GLSurface extends GLSurfaceView {
    private final String TAG = "GLS";

    private Chess game;
    private GLRenderer renderer;

    public GLSurface(Context context) {
        super(context);

        Log.i(TAG, "@Constructor: new Chess and Renderer");

        game = new Chess();
        renderer = new GLRenderer(game);
        setRenderer(renderer);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent e) {
        requestRender();
        return true;
    }
}
