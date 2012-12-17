package fi.starck.joukko;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Create a chequerboard background for the game.
 *
 * @author Tuomas Starck
 */
class Backdrop extends Drawable {
    private float unit;

    Backdrop() {
        /* Initializing and allocating memory for our vertex and
         * colour buffers. Floats are 4 bytes long, vertex needs
         * three and colour needs four values and 132 is just
         * ugly hardcoding.
         */
        ByteBuffer b1 = ByteBuffer.allocateDirect(132*4*4);
        ByteBuffer b2 = ByteBuffer.allocateDirect(132*3*4);

        b1.order(ByteOrder.nativeOrder());
        b2.order(ByteOrder.nativeOrder());

        colours  = b1.asFloatBuffer();
        vertices = b2.asFloatBuffer();
        count = 132;
    }

    void setResolution(float u) {
        unit = u;

        float quad = 4*u;

        colours.put(new float[] {
            1.000f, 0.564f, 0.755f, 1.0f,
            1.000f, 0.688f, 0.766f, 1.0f,
            1.000f, 0.600f, 0.777f, 1.0f,
            1.000f, 0.611f, 0.788f, 1.0f
        });
        vertices.put(new float[] {
            -quad, -quad, 0,
            -quad,  quad, 0,
             quad, -quad, 0,
             quad,  quad, 0
        });

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
        float rgba[] = new float[] {
            0.687f, 0.184f, 0.310f, 1.0f,
            0.692f, 0.180f, 0.321f, 1.0f,
            0.696f, 0.176f, 0.323f, 1.0f,
            0.699f, 0.172f, 0.334f, 1.0f
        };

        float sqr[] = new float[]{
            0,    0,    0.1f,
            0,    unit, 0.1f,
            unit, 0,    0.1f,
            unit, unit, 0.1f
        };

        for (int i=0; i<sqr.length; i++) {
            sqr[i++] += unit*x;
            sqr[i++] += unit*y;
        }

        colours.put(rgba);
        vertices.put(sqr);
    }
}
