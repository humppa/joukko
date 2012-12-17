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
        0.32f, 0.80f, 0.5f,
        0.68f, 0.80f, 0.5f,
        0.34f, 0.67f, 0.5f,
        0.66f, 0.67f, 0.5f,

        0.43f, 0.48f, 0.5f,
        0.57f, 0.48f, 0.5f,

        0.36f, 0.40f, 0.5f,
        0.64f, 0.40f, 0.5f,
        0.36f, 0.31f, 0.5f,
        0.64f, 0.31f, 0.5f,
        0.44f, 0.26f, 0.5f,
        0.56f, 0.26f, 0.5f
    }),

    BISHOP(new float[] {
        0.29f, 0.87f, 0.5f,
        0.71f, 0.87f, 0.5f,
        0.42f, 0.52f, 0.5f,
        0.58f, 0.52f, 0.5f,
        0.34f, 0.45f, 0.5f,
        0.66f, 0.45f, 0.5f,

        0.45f, 0.45f, 0.5f,
        0.55f, 0.45f, 0.5f,
        0.40f, 0.41f, 0.5f,
        0.60f, 0.41f, 0.5f,
        0.43f, 0.30f, 0.5f,
        0.57f, 0.30f, 0.5f,
        0.50f, 0.23f, 0.5f
    }),

    KNIGHT(new float[] {
        0.31f, 0.87f, 0.5f,
        0.67f, 0.87f, 0.5f,
        0.21f, 0.75f, 0.5f,
        0.67f, 0.75f, 0.5f,
        0.38f, 0.47f, 0.5f,
        0.67f, 0.47f, 0.5f,
        0.40f, 0.41f, 0.5f,
        0.55f, 0.41f, 0.5f,

        0.32f, 0.27f, 0.5f,
        0.55f, 0.41f, 0.5f,
        0.41f, 0.15f, 0.5f,
        0.55f, 0.41f, 0.5f,
        0.56f, 0.15f, 0.5f,
        0.67f, 0.39f, 0.5f,
        0.79f, 0.29f, 0.5f,
        0.72f, 0.43f, 0.5f,
        0.79f, 0.40f, 0.5f
    }),

    ROOK(new float[] { // FIXME
        0.21f, 0.88f, 0.5f,
        0.79f, 0.88f, 0.5f,
        0.21f, 0.76f, 0.5f,
        0.79f, 0.76f, 0.5f,

        0.29f, 0.76f, 0.5f,
        0.71f, 0.76f, 0.5f,
        0.29f, 0.36f, 0.5f,
        0.71f, 0.36f, 0.5f,

        0.20f, 0.20f, 0.5f,
        0.80f, 0.20f, 0.5f
    }),

    QUEEN(new float[] {
        0.21f, 0.91f, 0.5f,
        0.79f, 0.91f, 0.5f,
        0.36f, 0.30f, 0.5f,
        0.64f, 0.30f, 0.5f,

        0.39f, 0.21f, 0.5f,
        0.25f, 0.16f, 0.5f,
        0.36f, 0.30f, 0.5f,

        0.64f, 0.30f, 0.5f,
        0.75f, 0.16f, 0.5f,
        0.61f, 0.21f, 0.5f,

        0.39f, 0.30f, 0.5f,
        0.61f, 0.30f, 0.5f,
        0.39f, 0.15f, 0.5f,
        0.61f, 0.15f, 0.5f,

        0.45f, 0.15f, 0.5f,
        0.55f, 0.15f, 0.5f,
        0.50f, 0.09f, 0.5f
    }),

    KING(new float[] { // FIXME
        0.28f, 0.94f, 0.5f,
        0.72f, 0.94f, 0.5f,
        0.32f, 0.80f, 0.5f,
        0.68f, 0.80f, 0.5f,

        0.43f, 0.60f, 0.5f,
        0.57f, 0.60f, 0.5f,

        0.33f, 0.51f, 0.5f,
        0.67f, 0.51f, 0.5f,
        0.33f, 0.40f, 0.5f,
        0.67f, 0.40f, 0.5f,
        0.42f, 0.33f, 0.5f,
        0.58f, 0.33f, 0.5f,

        0.48f, 0.33f, 0.5f,
        0.52f, 0.33f, 0.5f,
        0.48f, 0.21f, 0.5f,
        0.52f, 0.21f, 0.5f,

        0.39f, 0.21f, 0.5f,
        0.61f, 0.21f, 0.5f,
        0.39f, 0.17f, 0.5f,
        0.61f, 0.17f, 0.5f,

        0.48f, 0.17f, 0.5f,
        0.52f, 0.17f, 0.5f,
        0.48f, 0.07f, 0.5f,
        0.52f, 0.07f, 0.5f
    });

    private final float[] coords;

    Figure(float[] co) {
        coords = co;
    }

    float[] getCoords() {
        return coords;
    }
}
