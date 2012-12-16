package fi.starck.joukko;

import fi.starck.sakki.board.Type;

class Move {
    private Type what;
    private String from;
    private String x;
    private String to;

    Move(Type type, String sqr) {
        what = type;
        from = sqr;
        x = "";
    }

    void setCapture() {
        x = "x";
    }

    void setTarget(String sqr) {
        to = sqr;
    }

    String getSource() {
        return from;
    }

    @Override
    public String toString() {
        if (to == null) return null;

        String piece = what.isPawn()? "": what.toString().toUpperCase();

        return piece + from + x + to;
    }
}
