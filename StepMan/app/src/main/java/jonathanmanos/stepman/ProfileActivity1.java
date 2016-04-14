package jonathanmanos.stepman;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private Intent intent;

    private Sensor mStepCounterSensor;

    private Sensor mStepDetectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        profile = this;
        intent = new Intent(this, StepCounterService.class);

        mPrefs = getSharedPreferences("label", 0);
        name = mPrefs.getString("tag", "");
        stepCounter = mPrefs.getInt("steps", 0);
        levelCounter = mPrefs.getInt("level", 1);
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

        //TextView internetTextView = (TextView) findViewById(R.id.internetTextView);

        //internetTextView.setText("First line of randow website using httpurlconnection is: " + firstLine + " .");



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

            if(stepCounter % 20 == 0) {
                levelCounter++;
                mEditor.putInt("level", levelCounter).commit();

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.stepmanrunning);
                //Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                CharSequence title = "StepMan Level Up!";
                CharSequence contentText = "You just rose to Level " + levelCounter + "!";
                CharSequence contentSubText = "Upgrade Your Stats!";

                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setSmallIcon(R.drawable.stepmanrunning)
                                .setLargeIcon(largeIcon)
                                .setContentTitle(title)
                                .setContentText(contentText)
                                .setSubText(contentSubText)
                                .setDefaults(-1)
                                .setTicker(title)
                                ;
                // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(this, ProfileActivity1.class);

                int mId = 1;
                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(ProfileActivity1.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(mId, mBuilder.build());


                //http://stackoverflow.com/questions/9631869/light-up-screen-when-notification-received-android

                //Gets PowerManager Service
                PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);

                //boolean that finds if the screen is currently on or not
                boolean isScreenOn = pm.isScreenOn();

                Log.e("screen on.............", "" + isScreenOn);

                //if the screen is off, wakes the screen for the notification
                if(isScreenOn==false)
                {

                    PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");

                    wl.acquire(10000);
                    PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");

                    wl_cpu.acquire(10000);
                }


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

        stopService(intent);

        mSensorManager.registerListener(this, mStepCounterSensor,
                SensorManager.SENSOR_DELAY_FASTEST);

        mSensorManager.registerListener(this, mStepDetectorSensor,
                SensorManager.SENSOR_DELAY_FASTEST);

        //TextView internetTextView = (TextView) findViewById(R.id.internetTextView);

        //internetTextView.setText("First line of randow website using httpurlconnection is: " + firstLine + " .");

    }

    protected void onStop() {

        super.onStop();

        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);

        startService(intent);
    }

}
