package fi.starck.joukko;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

abstract class Drawable {
    protected int count;
    protected FloatBuffer colours, vertices;

    /**
     * Draw the object to screen.
     *
     * @param gl OpenGL system object.
     */
    public void draw(GL10 gl) {
        /* params for both: size, type, stride, ptr
         */
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colours);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);

        /* params: mode, first, count
         */
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, count);
    }
}
