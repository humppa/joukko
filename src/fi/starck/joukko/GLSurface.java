package fi.starck.joukko;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

class GLSurface extends GLSurfaceView {
    GLRenderer renderer;

    public GLSurface(Context context) {
        super(context);
        renderer = new GLRenderer();
        setRenderer(renderer);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent e) {
        requestRender();
        return true;
    }
}
