package fi.starck.joukko;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Game {
    private FloatBuffer vertices;

    private float floats[] = {
        -1, -1, 0,
        -1,  1, 0,
         1, -1, 0,
         1,  3, 0
    };

    public Game() {
        /* Float are 4 bytes wide.
         */
        ByteBuffer buffer = ByteBuffer.allocateDirect(floats.length*4);
        buffer.order(ByteOrder.nativeOrder());

        /* Allocate byte buffers memory.
         */
        vertices = buffer.asFloatBuffer();
        vertices.put(floats);
        vertices.position(0);
    }

    public void draw(GL10 gl, float r, float g, float b) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glColor4f(r, g, b, 0.5f);

        /* OpenGL should use the buffer to extract the vertices from.
         * First three parameters are size, type and offset.
         */
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);

        /* Parameters are: draw mode, offset and count.
         */
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, floats.length/3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
