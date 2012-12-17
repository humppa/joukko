package fi.starck.joukko;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.FloatMath;

/**
 * Chess game board in OpenGL ES.
 *
 * @author Tuomas Starck
 */
public class JoukkoActivity extends Activity implements SensorEventListener {
    private final float OVER_9000 = 12.0f;

    private GLSurfaceView view;
    private SensorManager sensor;
    private float acceleration, accelNow, accelLast;

    private float sqrsum(float x, float y, float z) {
        return FloatMath.sqrt(x*x + y*y + z*z);
    }

    private void startSensor() {
        sensor.registerListener(
            this,
            sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    /**
     * On activity creation a new View is created, sensor values are
     * initiated and accelerometer is activated.
     *
     * To save battery, rendering is done only when specifically requested.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new GLView(this);
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(view);

        acceleration = 0.0f;
        accelNow = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;

        sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        startSensor();
    }

    /**
     * Back button goes back in history.
     *
     * @todo Should this do finish() if there is nothing
     *       else to do? Many apps seems to do that.
     */
    @Override
    public void onBackPressed() {
        ((GLView) view).undo();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * Calculate acceleration and trigger reset.
     */
    @Override
    public void onSensorChanged(SensorEvent se) {
        accelLast = accelNow;
        accelNow = sqrsum(se.values[0], se.values[1], se.values[2]);
        acceleration = acceleration * 0.9f + (accelNow - accelLast);

        if (acceleration > OVER_9000) {
            ((GLView) view).reset();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
        startSensor();
    }

    @Override
    protected void onPause() {
        sensor.unregisterListener(this);
        view.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        sensor.unregisterListener(this);
        super.onStop();
    }
}
