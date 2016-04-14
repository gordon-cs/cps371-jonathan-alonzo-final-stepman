package jonathanmanos.stepman;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Jonny on 4/14/2016.
 */
public class StepCounterService extends Service implements SensorEventListener {

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
    public void onCreate(){
        super.onCreate();
        mPrefs = getSharedPreferences("label", 0);
        name = mPrefs.getString("tag", "");
        stepCounter = mPrefs.getInt("steps", 0);
        levelCounter = mPrefs.getInt("level", 1);
        System.out.println(stepCounter);

        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }

    @Override
    public void onDestroy(){

        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        mSensorManager.registerListener(this, mStepCounterSensor,
                SensorManager.SENSOR_DELAY_FASTEST);

        mSensorManager.registerListener(this, mStepDetectorSensor,
                SensorManager.SENSOR_DELAY_FASTEST);

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent arg0){
        return null;
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

        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }


}
