package fi.starck.joukko;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import fi.starck.sakki.board.Chess;
import fi.starck.sakki.board.MoveException;
import fi.starck.sakki.board.Type;

class GLView extends GLSurfaceView {
    private final String TAG = "GLS";
    private final String KEY = "fen";

    private String move;
    private Chess game;
    private GLRenderer renderer;
    private SharedPreferences safe;
    private ArrayList<String> history;

    GLView(Context context) {
        super(context);

        Log.i(TAG, "@Constructor: new Chess and Renderer");

        move = null;
        safe = PreferenceManager.getDefaultSharedPreferences(context);
        game = new Chess(safe.getString(KEY, null));
        renderer = new GLRenderer(game.getState());
        history = new ArrayList<String>();

        setRenderer(renderer);
    }

    /**
     * Initiate a move.
     *
     * Check that user is selecting his/hers own piece (and not empty square
     * or opponents piece). Also prefix move with pieces name if needed.
     *
     * @param sqr Coordinate of the selected square.
     *
     * @return Beginning of the move, if move seems valid. Otherwise null.
     */
    protected String beginMove(String sqr) {
        Type type = game.typeAt(sqr);

        if (type == null) return null;

        if (type.getSide() == game.getTurn()) {
            renderer.toggleSelected(true);
            requestRender();
            return type.isPawn()? sqr: type.toString().toUpperCase() + sqr;
        }

        return null;
    }

    /**
     * Finish move.
     *
     * Make same kind of checks as beginMove() i.e. if the square
     * is empty or has opponents piece, and act accordingly.
     *
     * @param move Beginning of the move.
     * @param sqr Coordinate of the selected square.
     *
     * @return Complete move, if it seems valid, or null.
     */
    protected String finishMove(String move, String sqr) {
        Type type = game.typeAt(sqr);

        if (type == null) {
            return move + sqr;
        }
        else if (type.getSide() != game.getTurn()) {
            return move + "x" + sqr;
        }

        return null;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent e) {
        if (e.getAction() != MotionEvent.ACTION_DOWN) return true;

        queueEvent(new Runnable() {
            @Override
            public void run() {
                String tmp = "";
                String sqr = renderer.resolveSquare(e.getX(), e.getY());

                if (move == null) {
                    move = beginMove(sqr);
                }
                else {
                    move = finishMove(move, sqr);

                    if (move == null) return;

                    try {
                        tmp = game.toString();
                        renderer.toggleSelected(false);
                        Log.i(TAG, "<" + move + ">");

                        game.move(move);
                        history.add(0, tmp);
                    }
                    catch (MoveException me) {
                        Log.e(TAG, "Move error: " + me.toString());

                        if (me.isDirty()) {
                            game = new Chess(tmp);
                        }
                    }

                    move = null;

                    update();
                }
            }
        });

        return true;
    }

    private void saveState(String fen) {
        SharedPreferences.Editor editor = safe.edit();
        editor.putString(KEY, fen);
        editor.commit();
    }

    void undo() {
        if (!history.isEmpty()) {
            game = new Chess(history.remove(0));
            update();
        }
    }

    void update() {
        saveState(game.toString());
        renderer.setState(game.getState());
        requestRender();
    }
}
