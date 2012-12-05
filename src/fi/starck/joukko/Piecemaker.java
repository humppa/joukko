package fi.starck.joukko;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
import fi.starck.sakki.board.Type;

public class Piecemaker {
    private final String TAG = "PIECE";

    private int count;
    private FloatBuffer colours, vertices;

    public Piecemaker(Type type, int x, int y, int unit) {
        Log.i(TAG, "Piecing " + type.toString() + " at [" + x + "," + y + "]");

        Figure f = selectFigure(type);

        if (f == null) return;

        float[] vanilla = f.getCoords();

        int len = vanilla.length;
        count = len/3;

        float[] coords = new float[len];

        /* Copy the original figure,
         * scale it appropriately and
         * shift it to place.
         */
        for (int i=0; i<len; i++) {
            /* Copy   = from original * scale + shift
             */
            coords[i] = vanilla[i++]  * unit  + unit*(x-4);
            coords[i] = vanilla[i++]  * unit  + unit*(-y+3);
        }

        float[] rgb = new float[count*4];

        for (int i=0; i<rgb.length; i++) {
            rgb[i] = 1.0f;
        }

        /* Initializing and allocating memory for our vertex
         * and colour buffers. Floats are 4 bytes long.
         */
        ByteBuffer b1 = ByteBuffer.allocateDirect(rgb.length*4);
        ByteBuffer b2 = ByteBuffer.allocateDirect(coords.length*4);

        b1.order(ByteOrder.nativeOrder());
        b2.order(ByteOrder.nativeOrder());

        colours  = b1.asFloatBuffer();
        vertices = b2.asFloatBuffer();

        colours.put(rgb);
        vertices.put(coords);

        colours.position(0);
        vertices.position(0);
    }

    private Figure selectFigure(Type type) {
        if (type == Type.p || type == Type.P) {
            return Figure.PAWN;
        }
        else if (type == Type.b || type == Type.B) {
            return Figure.BISHOP;
        }
        else if (type == Type.n || type == Type.N) {
            return Figure.KNIGHT;
        }
        else if (type == Type.r || type == Type.R) {
            return Figure.ROOK;
        }
        else if (type == Type.q || type == Type.Q) {
            return Figure.QUEEN;
        }
        else if (type == Type.k || type == Type.K) {
            return Figure.KING;
        }

        return null;
    }

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
