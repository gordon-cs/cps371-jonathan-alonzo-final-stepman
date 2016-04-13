package jonathanmanos.stepman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ProfileActivity1 extends AppCompatActivity implements SensorEventListener {

    private String name;
    public String firstLine;
    private boolean firstTime = true;
    public static Activity profile;
    private SensorManager mSensorManager;
    private SharedPreferences mPrefs;
    private int stepCounter;
    private int levelCounter;

    private Sensor mStepCounterSensor;

    private Sensor mStepDetectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        profile = this;

        mPrefs = getSharedPreferences("label", 0);
        name = mPrefs.getString("tag", "");
        stepCounter = mPrefs.getInt("steps", 0);
        levelCounter = mPrefs.getInt("level", 1);
        firstLine = mPrefs.getString("http", ":(");
        System.out.println(stepCounter);

        try{
            LoginActivity.logIn.finish();
        }
        catch(NullPointerException e){

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        TextView myLevelView = (TextView) findViewById(R.id.mylevelview);

        myLevelView.setText("Level: " + levelCounter);

        TextView myStepView = (TextView) findViewById(R.id.mystepview);

        myStepView.setText("Steps: " + stepCounter + "\n");

        TextView myTextView = (TextView) findViewById(R.id.mytextview);

        myTextView.setText("Welcome, " + name + ".");

        new Puller().execute("http://qz.com/298042/the-bizarre-multi-billion-dollar-industry-of-american-fantasy-sports/");

        TextView internetTextView = (TextView) findViewById(R.id.internetTextView);

        internetTextView.setText("First line of randow website using httpurlconnection is: " + firstLine + " .");



        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    }

    public void setFirstLine(String value){
        this.firstLine = value;
    }

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken

            stepCounter++;
            TextView myStepView = (TextView) findViewById(R.id.mystepview);

            myStepView.setText("Steps: " + stepCounter + "\n");

            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putInt("steps", stepCounter).commit();

            if(stepCounter == 20){
                mEditor.putInt("level", 2).commit();
                levelCounter = 2;
            }
            if(stepCounter == 50){
                mEditor.putInt("level", 3).commit();
                levelCounter = 3;
            }
            if(stepCounter == 100){
                mEditor.putInt("level", 4).commit();
                levelCounter = 4;
            }

            TextView myLevelView = (TextView) findViewById(R.id.mylevelview);

            myLevelView.setText("Level: " + levelCounter);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    protected void onResume() {

        super.onResume();

        mSensorManager.registerListener(this, mStepCounterSensor,

                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor,

                SensorManager.SENSOR_DELAY_FASTEST);

        TextView internetTextView = (TextView) findViewById(R.id.internetTextView);

        internetTextView.setText("First line of randow website using httpurlconnection is: " + firstLine + " .");
    }

    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

}
