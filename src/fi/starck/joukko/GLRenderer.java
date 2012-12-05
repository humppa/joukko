package fi.starck.joukko;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import fi.starck.sakki.board.Chess;
import fi.starck.sakki.board.Type;

class GLRenderer implements Renderer {
    private final String TAG = "GLR";

    private int width, height, unit;

    private Chess game;
    private Board board;
    private Piecemaker[][] pieces;

    public GLRenderer(Chess chess) {
        Log.i(TAG, "@Constructor: new Board and Pieces");

        game = chess;
        board = new Board();
        pieces = new Piecemaker[8][8];
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(TAG, "@SurfaceCreated: setting up GL.");

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        Log.i(TAG, "[width,height] :: [" + w + "," + h + "]");

        width = w;
        height = h;
        unit = w < h? w/8: h/8;

        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        /* Params: left, right, bottom, top, near, far */
        gl.glOrthof(-w/2, w/2, h/2, -h/2, -1.0f, 1.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        board.setResolution(unit);

        Type state[][] = game.getState();

        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                Type t = state[y][x];

                if (0 < t.getIndex() && t.getIndex() <= 6) {
                    pieces[y][x] = new Piecemaker(t, x, y, unit);
                }
                else {
                    pieces[y][x] = null;
                }
            }
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -1.0f);

        board.draw(gl);

        Log.i(TAG, "Board done. Drawing some pieces.");

        for (int x=0; x<8; x++) {
            for (int y=0; y<8; y++) {
                if (pieces[x][y] != null) {
                    pieces[x][y].draw(gl);
                }
            }
        }
    }

    /**
     * Resolve input coordinate to game board square.
     *
     * @return SAN square name as a string.
     */
    String resolveSquare(float x, float y) {
        int file, rank;
        String[] lookup = {"a", "b", "c", "d", "e", "f", "g", "h"};

        if (width < height) {
            file = (int)(8*x/width);
            rank = (int)((4*height+5*width-8*y)/width);
        }
        else {
            file = (int)(8*(x-(width-height)/2)/height);
            rank = (int)(9-8*y/height);
        }

        if (0 > file || file >= 8) return null;

        Log.i(TAG, "[ " + lookup[file] + rank + " ] @" + x + "," + y);

        return lookup[file] + rank;
    }
}
