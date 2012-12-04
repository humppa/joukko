package fi.starck.joukko;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Piece {
    private final String TAG = "PIECE";

    private int count;
    private FloatBuffer colours, vertices;

    public Piece(int x, int y, int width, int height) {
        Log.i(TAG, "Piecing at [" + x + "," + y + "]");

        int unit = width < height? width/8: height/8;

        float coord[] = new float[Graphic.p.length];

        for (int i=0; i<coord.length; i++) {
            coord[i] = Graphic.p[i];
        }

        count = coord.length/3;

        for (int i=0; i<coord.length; i++) {
            /* Scale to size.
             */
            coord[i++] *= unit;
            coord[i++] *= unit;
        }

        for (int i=0; i<coord.length; i++) {
            /* Shift pieces to its position.
             */
            coord[i++] += unit*(x-4);
            coord[i++] += unit*(-y+3);
        }

        float[] rgb = new float[count*4];

        for (int i=0; i<rgb.length; i++) {
            rgb[i] = 1.0f;
        }

        /* Initializing and allocating memory for our vertex
         * and colour buffers. Floats are 4 bytes long.
         */
        ByteBuffer b1 = ByteBuffer.allocateDirect(rgb.length*4);
        ByteBuffer b2 = ByteBuffer.allocateDirect(coord.length*4);

        b1.order(ByteOrder.nativeOrder());
        b2.order(ByteOrder.nativeOrder());

        colours  = b1.asFloatBuffer();
        vertices = b2.asFloatBuffer();

        colours.put(rgb);
        vertices.put(coord);

        colours.position(0);
        vertices.position(0);
    }

    public void draw(GL10 gl) {
        // Log.i(TAG, "Trying to draw a piece.");

        /* params for both: size, type, stride, ptr
         */
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colours);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);

        /* params: mode, first, count
         */
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, count);
    }
}
