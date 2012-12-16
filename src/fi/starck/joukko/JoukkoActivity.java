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
import android.util.Log;

/**
 * Chess game board in OpenGL ES.
 *
 * @author Tuomas Starck
 */
public class JoukkoActivity extends Activity {
    private final String TAG = "JACT";
    private final float OVER9000 = 12.0f;

    private GLSurfaceView view;
    private SensorManager sensor;
    private float mAccel;         // acceleration apart from gravity
    private float mAccelCurrent;  // current acceleration including gravity
    private float mAccelLast;     // last acceleration including gravity

    private final SensorEventListener sensorlistener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = FloatMath.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > OVER9000) {
                Log.i(TAG, "Ragequit with force of " + mAccel + " bhp");
                ((GLView) view).reset();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new GLView(this);
        /* Save battery by rendering only when specifically requested.
         */
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(view);

        sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor.registerListener(sensorlistener, sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    public void onBackPressed() {
        ((GLView) view).undo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
        sensor.registerListener(sensorlistener, sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        sensor.unregisterListener(sensorlistener);
        super.onStop();
    }
}
