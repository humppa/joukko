package fi.starck.joukko;

/**
 * Stauntons in 2D.
 *
 * A.k.a. chess figures in vector graphics represented as Java
 * float arrays. All coordinates [x,y] are between [0,0] – [1,1].
 * The z coordinate is just some suitable constant.
 *
 * @author Tuomas Starck
 */
enum Figure {
    PAWN(new float[] {
        0.22f, 0.91f, 0.5f,
        0.78f, 0.91f, 0.5f,

        0.25f, 0.87f, 0.5f,
        0.75f, 0.87f, 0.5f,

        0.39f, 0.80f, 0.5f,
        0.61f, 0.80f, 0.5f,

        0.42f, 0.61f, 0.5f,
        0.58f, 0.61f, 0.5f,

        0.47f, 0.47f, 0.5f,
        0.53f, 0.47f, 0.5f,

        0.40f, 0.40f, 0.5f,
        0.60f, 0.40f, 0.5f,

        0.37f, 0.30f, 0.5f,
        0.63f, 0.30f, 0.5f,

        0.46f, 0.22f, 0.5f,
        0.54f, 0.22f, 0.5f
    }),

    BISHOP(new float[] {
        0.33f, 0.66f, 0.5f,
        0.66f, 0.66f, 0.5f,
        0.33f, 0.33f, 0.5f,
        0.66f, 0.33f, 0.5f
    }),

    KNIGHT(new float[] {
        0.33f, 0.66f, 0.5f,
        0.66f, 0.66f, 0.5f,
        0.33f, 0.33f, 0.5f,
        0.66f, 0.33f, 0.5f
    }),

    ROOK(new float[] {
        0.33f, 0.66f, 0.5f,
        0.66f, 0.66f, 0.5f,
        0.33f, 0.33f, 0.5f,
        0.66f, 0.33f, 0.5f
    }),

    QUEEN(new float[] {
        0.33f, 0.66f, 0.5f,
        0.66f, 0.66f, 0.5f,
        0.33f, 0.33f, 0.5f,
        0.66f, 0.33f, 0.5f
    }),

    KING(new float[] {
        0.33f, 0.66f, 0.5f,
        0.66f, 0.66f, 0.5f,
        0.33f, 0.33f, 0.5f,
        0.66f, 0.33f, 0.5f
    });

    private final float[] coords;

    Figure(float[] co) {
        coords = co;
    }

    float[] getCoords() {
        return coords;
    }
}
