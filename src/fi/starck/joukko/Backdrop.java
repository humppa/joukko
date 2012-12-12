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
            0.9375f, 0.0345f, 0.2906f, 1.0f,
            0.9575f, 0.1345f, 0.2406f, 1.0f,
            0.9775f, 0.2345f, 0.1906f, 1.0f,
            0.9975f, 0.2345f, 0.0906f, 1.0f
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
        float clr[] = new float[] {
            0.22f, 0.21f, 0.11f, 1.0f,
            0.21f, 0.11f, 0.11f, 1.0f,
            0.11f, 0.11f, 0.10f, 1.0f,
            0.11f, 0.10f, 0.01f, 1.0f,
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

        colours.put(clr);
        vertices.put(sqr);
    }
}
