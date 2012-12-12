package fi.starck.joukko;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Superclass for everything that gets draw to screen.
 *
 * @author Tuomas Starck
 */
abstract class Drawable {
    protected int count;
    protected FloatBuffer colours, vertices;

    /**
     * Draw the object to screen.
     *
     * @param gl OpenGL system object.
     */
    public void draw(GL10 gl) {
        /* Give colour- and vertexbuffer pointers to OpenGL.
         * Params: size, type, stride, ptr
         */
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colours);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);

        /* Let the actual drawing happen.
         * Params: mode, first, count
         */
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, count);
    }
}
