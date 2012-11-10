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
        queueEvent(new Runnable() {
            @Override
            public void run() {
                renderer.setColor(1.0f, e.getX()/getWidth(), e.getY()/getHeight());
            }
        });

        requestRender();

        return true;
    }
}
