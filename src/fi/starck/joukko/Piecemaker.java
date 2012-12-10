package fi.starck.joukko;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import fi.starck.sakki.board.Type;

class Piecemaker extends Drawable {
    public Piecemaker(Type type, int x, int y, int unit) {
        Figure f = selectFigure(type);

        if (f == null) return;

        float[] vanilla = f.getCoords();
        float color = type.getSide()? 1.0f: 0.42f;
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
            coords[i] = vanilla[i++]  * unit  + unit*(y-4);
        }

        float[] rgb = new float[count*4];

        for (int i=0; i<rgb.length; i++) {
            rgb[i] = color;
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
}
