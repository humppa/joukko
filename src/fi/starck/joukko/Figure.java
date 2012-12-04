package fi.starck.joukko;

enum Figure {
    PAWN(new float[]{
        0.22f, 0.91f, 0.3f,
        0.78f, 0.91f, 0.3f,

        0.25f, 0.87f, 0.3f,
        0.75f, 0.87f, 0.3f,

        0.39f, 0.80f, 0.3f,
        0.61f, 0.80f, 0.3f,

        0.42f, 0.61f, 0.3f,
        0.58f, 0.61f, 0.3f,

        0.47f, 0.47f, 0.3f,
        0.53f, 0.47f, 0.3f,

        0.40f, 0.40f, 0.3f,
        0.60f, 0.40f, 0.3f,

        0.37f, 0.30f, 0.3f,
        0.63f, 0.30f, 0.3f,

        0.46f, 0.22f, 0.3f,
        0.54f, 0.22f, 0.3f
    }),

    BISHOP(new float[]{
        0.33f, 0.66f, 0.3f,
        0.66f, 0.66f, 0.3f,
        0.33f, 0.33f, 0.3f,
        0.66f, 0.33f, 0.3f
    }),

    KNIGHT(new float[]{
        0.33f, 0.66f, 0.3f,
        0.66f, 0.66f, 0.3f,
        0.33f, 0.33f, 0.3f,
        0.66f, 0.33f, 0.3f
    }),

    ROOK(new float[]{
        0.33f, 0.66f, 0.3f,
        0.66f, 0.66f, 0.3f,
        0.33f, 0.33f, 0.3f,
        0.66f, 0.33f, 0.3f
    }),

    QUEEN(new float[]{
        0.33f, 0.66f, 0.3f,
        0.66f, 0.66f, 0.3f,
        0.33f, 0.33f, 0.3f,
        0.66f, 0.33f, 0.3f
    }),

    KING(new float[]{
        0.33f, 0.66f, 0.3f,
        0.66f, 0.66f, 0.3f,
        0.33f, 0.33f, 0.3f,
        0.66f, 0.33f, 0.3f
    });

    private final float[] coords;

    Figure(float[] co) {
        coords = co;
    }

    float[] getCoords() {
        return coords;
    }
}
