package fi.starck.joukko;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import fi.starck.sakki.board.Type;

/**
 * OpenGL rendering interface.
 *
 * This class sets up OpenGL subsystem and creates drawable objects.
 *
 * @author Tuomas Starck
 */
class GLRenderer implements Renderer {
    private final String TAG = "GLR";

    private int width, height, unit;
    private int file, rank;
    private Type[][] state;
    private Mark selected;
    private Backdrop backdrop;
    private Piecemaker[][] pieces;

    public GLRenderer(Type[][] status) {
        Log.i(TAG, "@Constructor: new Board and Pieces");

        state = status;
        selected = null;
        backdrop = new Backdrop();
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

        backdrop.setResolution(unit);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -1.0f);

        recreatePieces();

        backdrop.draw(gl);

        if (selected != null) {
            selected.draw(gl);
        }

        for (int x=0; x<8; x++) {
            for (int y=0; y<8; y++) {
                if (pieces[x][y] != null) {
                    pieces[x][y].draw(gl);
                }
            }
        }
    }

    /**
     * Iterate through game state and create pieces to be drawn.
     */
    private void recreatePieces() {
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

    /**
     * Create or remove selection mark.
     *
     * @param ok Indication if mark should be created or removed.
     */
    void toggleSelected(boolean ok) {
        selected = ok? new Mark(file, rank, unit): null;
    }

    /**
     * Update game state information.
     *
     * @param typearray Game state.
     * @param checked
     */
    void setState(Type[][] typearray) {
        state = typearray;
    }

    /**
     * Resolve input coordinate to game board square.
     *
     * This method takes screen coordinates (x and y) and translates
     * them to SAN coordinate. As a side effect, coordinate of the
     * last input event is saved (in variables file and rank).
     *
     * If input is not within board, null is returned.
     *
     * @return SAN square name as a string or null.
     */
    String resolveSquare(float x, float y) {
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

        return lookup[file] + rank;
    }
}
