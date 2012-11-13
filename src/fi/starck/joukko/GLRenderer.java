package fi.starck.joukko;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

class GLRenderer implements Renderer {
    private float rgb[];
    private Game sqr, dyn;

    private float sqr_co[] = {
        -1,  0, 0,
        -1,  2, 0,
         1,  0, 0,
         1,  2, 0
    };

    public GLRenderer() {
        rgb = new float[]{0.6f, 0.9f, 0.3f};
        sqr = new Game(sqr_co);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {}

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float)width/(float)height, 0.1f, 100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -10.0f);

        sqr.draw(gl, rgb);

        if (dyn != null) {
            dyn.draw(gl, new float[]{1.0f, 1.0f, 1.0f});
        }
    }

    public void setColor(float r, float g, float b) {
        rgb = new float[]{r, g, b};
    }

    public void moveTriangle(float x, float y) {
        x = x*4-2;
        y = -1*(y*8-4);

        dyn = new Game(new float[] {
            x,   y-1, 0,
            x-1,   y, 0,
            x+1,   y, 0
        });
    }
}
