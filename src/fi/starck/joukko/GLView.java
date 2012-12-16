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

    private Move move;
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
     * Check that user is selecting his/hers own piece and create the
     * start of the move.
     *
     * @param sqr Coordinate of the selected square.
     */
    protected void beginMove(String sqr) {
        Type type = game.typeAt(sqr);

        /* Ignore if user tapped air.
         */
        if (type == null) return;

        if (type.getSide() == game.getTurn()) {
            move = new Move(type, sqr);
            renderer.toggleSelected(true);
            requestRender();
        }
    }

    /**
     * Finish or withdraw a move.
     *
     * Check that the target square is empty or has opponents piece,
     * and act accordingly. If previously selected square is tapped
     * again, move is cancelled.
     *
     * @param sqr Coordinate of the selected square.
     *
     * @return True if move seems valid.
     */
    protected boolean finishMove(String sqr) {
        if (sqr.equals(move.getSource())) undo();

        Type type = game.typeAt(sqr);

        if (type == null) {
            move.setTarget(sqr);
        }
        else if (type.getSide() != game.getTurn()) {
            move.setCapture();
            move.setTarget(sqr);
        }
        else {
            return false;
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent e) {
        if (e.getAction() != MotionEvent.ACTION_DOWN) return true;

        queueEvent(new Runnable() {
            @Override
            public void run() {
                String last = game.toString();
                String sqr = renderer.resolveSquare(e.getX(), e.getY());

                if (move == null) {
                    beginMove(sqr);
                }
                else {
                    if (!finishMove(sqr)) return;

                    try {
                        renderer.toggleSelected(false);
                        game.move(move.toString());
                        history.add(0, last);
                    }
                    catch (MoveException me) {
                        Log.i(TAG, "Move error <" + move + ">: " + me.toString());

                        if (me.isDirty()) {
                            game = new Chess(last);
                        }
                    }
                    finally {
                        move = null;
                        update();
                    }
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
     * 1) Save game state to non-volatile memory.
     * 2) Delegate game state to renderer and redraw.
     *
     * This should be called whenever the state of the
     * game has changed.
     */
    void update() {
        SharedPreferences.Editor editor = safe.edit();
        editor.putString(KEY, game.toString());
        editor.commit();

        renderer.setState(game);
        requestRender();
    }
}
