package fi.starck.joukko;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Board {
    private final String TAG = "BOARD";

    private float tile;
    private int drawCount;
    private FloatBuffer colours, vertices;

    public Board() {
        /* Initializing and allocating memory for our vertex
         * and colour buffers. Floats are 4 bytes long.
         */
        ByteBuffer b1 = ByteBuffer.allocateDirect(528*4);
        ByteBuffer b2 = ByteBuffer.allocateDirect(528*4);

        b1.order(ByteOrder.nativeOrder());
        b2.order(ByteOrder.nativeOrder());

        colours  = b1.asFloatBuffer();
        vertices = b2.asFloatBuffer();
        drawCount = 0;
    }

    public void setResolution(int width, int height) {
        int quad = width < height? width/2: height/2;

        tile = (float)quad/4;

        colours.put(new float[]{
            0.9375f, 0.0345f, 0.2906f, 1.0f,
            0.9575f, 0.1345f, 0.2406f, 1.0f,
            0.9775f, 0.2345f, 0.1906f, 1.0f,
            0.9975f, 0.2345f, 0.0906f, 1.0f
        });
        vertices.put(new float[]{
            -quad, -quad, 0,
            -quad,  quad, 0,
             quad, -quad, 0,
             quad,  quad, 0
        });

        drawCount += 4;

        for (int x=-4; x<4; x++) {
            for (int y=-4; y<4; y++) {
                if (Math.abs(x%2) == Math.abs(y%2)) continue;
                makeASquare(x, y);
            }
        }

        colours.position(0);
        vertices.position(0);
    }

    private void makeASquare(int x, int y) {
        float clr[] = new float[] {
            0.22f, 0.21f, 0.11f, 1.0f,
            0.21f, 0.11f, 0.11f, 1.0f,
            0.11f, 0.11f, 0.10f, 1.0f,
            0.11f, 0.10f, 0.01f, 1.0f,
        };

        float sqr[] = new float[]{
            0,    0,    0.1f,
            0,    tile, 0.1f,
            tile, 0,    0.1f,
            tile, tile, 0.1f
        };

        for (int i=0; i<sqr.length; i++) {
            sqr[i++] += tile*x;
            sqr[i++] += tile*y;
        }

        colours.put(clr);
        vertices.put(sqr);

        drawCount += 4;
    }

    public void draw(GL10 gl) {
        Log.i(TAG, "Drawing " + drawCount + " primitives");

        /* params for both: size, type, stride, ptr
         */
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colours);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);

        /* params: mode, first, count
         */
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, drawCount);
    }
}
