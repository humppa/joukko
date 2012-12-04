package fi.starck.joukko;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import fi.starck.sakki.board.Chess;
import fi.starck.sakki.board.Type;

class GLRenderer implements Renderer {
    private final String TAG = "GLR";

    private Chess game;
    private Board board;
    private Piece[][] pieces;

    public GLRenderer(Chess chess) {
        Log.i(TAG, "@Constructor: new Board and Pieces");

        game = chess;
        board = new Board();
        pieces = new Piece[8][8];
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
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i(TAG, "[w,h] :: [" + width + "," + height + "]");

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        /* Params: left, right, bottom, top, near, far */
        gl.glOrthof(-width/2, width/2, height/2, -height/2, -1.0f, 1.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        board.setResolution(width, height);

        Type state[][] = game.getState();

        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                if (state[y][x] == Type.p) {
                    pieces[y][x] = new Piece(x, y, width, height);
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
}
