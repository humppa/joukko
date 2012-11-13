package fi.starck.joukko;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Game {
    private int len;
    private FloatBuffer vertices;

    public Game(float form[]) {
        len = form.length;

        /* Float are 4 bytes wide.
         */
        ByteBuffer buffer = ByteBuffer.allocateDirect(len*4);
        buffer.order(ByteOrder.nativeOrder());

        /* Allocate byte buffers memory.
         */
        vertices = buffer.asFloatBuffer();
        vertices.put(form);
        vertices.position(0);
    }

    public void draw(GL10 gl, float rgb[]) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glColor4f(rgb[0], rgb[1], rgb[2], 0.5f);

        /* OpenGL should use the buffer to extract the vertices from.
         * First three parameters are size, type and offset.
         */
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);

        /* Parameters are: draw mode, offset and count.
         */
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, len/3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
