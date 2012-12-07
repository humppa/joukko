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

class GLView extends GLSurfaceView {
    private final String TAG = "GLS";
    private final String KEY = "fen";

    private String move;
    private Chess game;
    private GLRenderer renderer;
    private SharedPreferences safe;
    private ArrayList<String> history;

    public GLView(Context context) {
        super(context);

        Log.i(TAG, "@Constructor: new Chess and Renderer");

        move = null;
        safe = PreferenceManager.getDefaultSharedPreferences(context);
        game = new Chess(safe.getString(KEY, null));
        renderer = new GLRenderer(game.getState());
        history = new ArrayList<String>();

        setRenderer(renderer);
    }

    private void saveState(String fen) {
        SharedPreferences.Editor editor = safe.edit();
        editor.putString(KEY, fen);
        editor.commit();
    }

    public void goBack() {
        if (!history.isEmpty()) {
            game = new Chess(history.remove(0));
            renderer.setState(game.getState());
            requestRender();
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent e) {
        if (e.getAction() != MotionEvent.ACTION_DOWN) return true;

        queueEvent(new Runnable() {
            @Override
            public void run() {
                String current;
                String sqr = renderer.resolveSquare(e.getX(), e.getY());

                if (move == null) {
                    move = sqr;
                    // FIXME: Tässä ois hyvä visuaalisesti merkata siirron alku
                }
                else {
                    current = game.toString();

                    try {
                        Log.i(TAG, "@" + game.toString());
                        Log.i(TAG, "Moving <" + move + sqr + ">");

                        game.move(move + sqr);
                        history.add(0, current);
                    }
                    catch (MoveException me) {
                        Log.e(TAG, "Move error: " + me.toString());

                        if (me.isDirty()) {
                            game = new Chess(current);
                        }

                        Log.i(TAG, "Game is now: " + game.toString());
                    }

                    renderer.setState(game.getState());
                    requestRender();

                    Log.i(TAG, "Rendered: " + game.toString());

                    saveState(game.toString());

                    move = null;
                }
            }
        });

        return true;
    }
}
