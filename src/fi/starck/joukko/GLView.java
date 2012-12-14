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

/**
 * Main board view.
 *
 * This class is mostly responsible for input handling, UI logic and
 * interaction with Sakki (the game logic).
 *
 * @author Tuomas Starck
 */
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

        move = null;
        safe = PreferenceManager.getDefaultSharedPreferences(context);
        game = new Chess(safe.getString(KEY, null));
        renderer = new GLRenderer(game.getState(), game.getTurn());
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
                        renderer.toggleSelected(false);
                        tmp = game.toString();
                        game.move(move);
                        history.add(0, tmp);
                    }
                    catch (MoveException me) {
                        Log.i(TAG, "Move error <" + move + ">: " + me.toString());

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

    /**
     * Undo.
     *
     * If a move has been initiated, cancel it. Otherwise,
     * go back in time, if there is history.
     */
    void undo() {
        if (move != null) {
            move = null;
            renderer.toggleSelected(false);
            update();
        }
        else if (!history.isEmpty()) {
            game = new Chess(history.remove(0));
            update();
        }
    }

    /**
     * Save game state and rerender screen.
     *
     * This should be called whenever the state of the
     * game has changed.
     */
    void update() {
        SharedPreferences.Editor editor = safe.edit();
        editor.putString(KEY, game.toString());
        editor.commit();

        renderer.setState(game.getState(), game.getTurn());
        requestRender();
    }
}
