package fi.starck.joukko;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

class GLRenderer implements Renderer {
    private float rgb[];
    private Game sqr, dyn;
    private int width, height;

    private float sqr_co[] = {
        -400,   0, 0,
        -400,  42, 0,
         400,   0, 0,
         400,  42, 0
    };

    public GLRenderer() {
        rgb = new float[]{0.2f, 0.4f, 0.88f};
        sqr = new Game(sqr_co);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {}

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        width = w;
        height = h;

        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        /* Params: left, right, bottom, top, near, far */
        gl.glOrthof(-w/2, w/2, h/2, -h/2, 1, -1);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, 1.0f);

        sqr.draw(gl, rgb);

        if (dyn != null) {
            dyn.draw(gl, new float[]{1.0f, 1.0f, 1.0f});
        }
    }

    public void setColor(float r, float g, float b) {
        rgb = new float[]{r, g, b};
    }

    public void moveTriangle(float x, float y) {
        x = x*width-width/2;
        y = y*height-height/2;

        dyn = new Game(new float[] {
            x,    y-24, 0,
            x-24,    y, 0,
            x+24,    y, 0
        });
    }
}
