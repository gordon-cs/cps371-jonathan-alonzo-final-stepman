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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Console;

public class MainTabbedActivity extends AppCompatActivity implements SensorEventListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    //main activity
    public static Activity mainActivity;

    public static String name;
    public static String color;
    public static String difficulty;

    private boolean firstTime = true;
    public static Activity profile;
    private SensorManager mSensorManager;
    private SharedPreferences mPrefs;
    private Intent intent;

    private static int level;
    private static int steps;
    private static int points;
    private static int spentPoints;
    private static int difficultyValue;
    public static int stepsAtLevelUp;

    private static int hp;
    private static int strength;
    private static int defense;
    private static int magic;
    private static int magicDef;
    private static int speed;


    private Sensor mStepCounterSensor;

    private Sensor mStepDetectorSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabbed);

        mainActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        profile = this;
        intent = new Intent(this, StepCounterService.class);

        mPrefs = getSharedPreferences("label", 0);
        name = mPrefs.getString("name", "");
        color = mPrefs.getString("color", "");
        steps = mPrefs.getInt("steps", 0);

        difficulty = mPrefs.getString("difficulty", "");
        stepsAtLevelUp = mPrefs.getInt("stepsAtLevelUp", 0);
        difficultyValue = mPrefs.getInt("difficultyValue", 10);

        level = mPrefs.getInt("level", 1);
        points = mPrefs.getInt("points", 5);
        spentPoints = mPrefs.getInt("spentPoints", 0);

        hp = mPrefs.getInt("hp", 10);
        strength = mPrefs.getInt("strength", 10);
        defense = mPrefs.getInt("defense", 10);
        magic = mPrefs.getInt("magic", 10);
        magicDef = mPrefs.getInt("magicDef", 10);
        speed = mPrefs.getInt("speed", 10);

        difficultyValue = getDifficultyValue();

        System.out.println("steps on create" + steps);
        System.out.println("difficulty value on create" + difficultyValue);
        System.out.println("color picked: " + color);

        try{
            LoginActivity.logIn.finish();
        }
        catch(NullPointerException e){

        }

        /*TextView myLevelView = (TextView)findViewById(R.id.mylevelview);

        myLevelView.setText("Level: " + level);

        TextView myStepView = (TextView) findViewById(R.id.mystepview);

        myStepView.setText("Steps: " + stepCounter + "\n");

        TextView myTextView = (TextView) findViewById(R.id.mytextview);

        myTextView.setText("Welcome, " + name + ".");*/

        // new Puller().execute("http://qz.com/298042/the-bizarre-multi-billion-dollar-industry-of-american-fantasy-sports/");

        //TextView internetTextView = (TextView) findViewById(R.id.internetTextView);

        //internetTextView.setText("First line of randow website using httpurlconnection is: " + firstLine + " .");



        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            System.out.println("section number is: " + getArguments().getInt(ARG_SECTION_NUMBER));

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

                TextView myNameView = (TextView)rootView.findViewById(R.id.mynameview);
                myNameView.setText(name);

                TextView myLevelView = (TextView)rootView.findViewById(R.id.mylevelview);
                myLevelView.setText("Level: " + level);

                TextView myStepView = (TextView)rootView.findViewById(R.id.mystepview);
                myStepView.setText("Steps: " + steps);

                System.out.println("difficulty value at create view: " + difficultyValue);
                System.out.println("steps at create view: " + steps);
                TextView myStepTillView = (TextView)rootView.findViewById(R.id.mysteptillview);
                myStepTillView.setText("Steps till level up: " + (difficultyValue - steps));

                TextView myDifficultyView = (TextView)rootView.findViewById(R.id.mydifficultyview);
                myDifficultyView.setText("Difficulty: " + difficulty);



                ImageView myImageView = (ImageView)rootView.findViewById(R.id.imageviewstepman);

                System.out.println("color is: " + color);
                if(color.contentEquals("Black")){
                    myImageView.setImageResource(R.drawable.stepmanrunning);
                }
                else if(color.contentEquals("Blue")) {
                    myImageView.setImageResource(R.drawable.stepmanrunning_blue);
                }
                else if(color.contentEquals("Green")) {
                    myImageView.setImageResource(R.drawable.stepmanrunning_green);
                }
                else if(color.contentEquals("Red")) {
                    myImageView.setImageResource(R.drawable.stepmanrunning_red);
                }

                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

                TextView myStatLevelView = (TextView)rootView.findViewById(R.id.mystatlevelview);
                myStatLevelView.setText("Level: " + level);

                TextView myPointsView = (TextView)rootView.findViewById(R.id.statPoints);
                myPointsView.setText("Points: " + points);

                if(points > 0){
                    Button buttonHP = (Button)rootView.findViewById(R.id.buttonHP);
                    buttonHP.setEnabled(true);
                    Button buttonStrength = (Button)rootView.findViewById(R.id.buttonStrength);
                    buttonStrength.setEnabled(true);
                    Button buttonDefense = (Button)rootView.findViewById(R.id.buttonDefense);
                    buttonDefense.setEnabled(true);
                    Button buttonMagic = (Button)rootView.findViewById(R.id.buttonMagic);
                    buttonMagic.setEnabled(true);
                    Button buttonMagicDef = (Button)rootView.findViewById(R.id.buttonMagicDef);
                    buttonMagicDef.setEnabled(true);
                    Button buttonSpeed = (Button)rootView.findViewById(R.id.buttonSpeed);
                    buttonSpeed.setEnabled(true);
                }

                TextView myStatHP = (TextView)rootView.findViewById(R.id.statHP);
                myStatHP.setText(Integer.toString(hp));
                TextView myStatStrength = (TextView)rootView.findViewById(R.id.statStrength);
                myStatStrength.setText(Integer.toString(strength));
                TextView myStatDefense = (TextView)rootView.findViewById(R.id.statDefense);
                myStatDefense.setText(Integer.toString(defense));
                TextView myStatMagic = (TextView)rootView.findViewById(R.id.statMagic);
                myStatMagic.setText(Integer.toString(magic));
                TextView myStatMagicDef = (TextView)rootView.findViewById(R.id.statMagicDef);
                myStatMagicDef.setText(Integer.toString(magicDef));
                TextView myStatSpeed = (TextView)rootView.findViewById(R.id.statSpeed);
                myStatSpeed.setText(Integer.toString(speed));

                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 4) {
                View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);
                return rootView;
            }
            else{
                View rootView = inflater.inflate(R.layout.fragment_main_tabbed, container, false);
                return rootView;
            }

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Profile";
                case 1:
                    return "Stats";
                case 2:
                    return "Steps";
                case 3:
                    return "Inventory";
            }
            return null;
        }
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

            steps++;
            difficultyValue = getDifficultyValue();

            SharedPreferences.Editor mEditor = mPrefs.edit();
            mEditor.putInt("steps", steps);
            mEditor.putInt("difficultyValue", difficultyValue);
            mEditor.apply();



            System.out.println("difficulty value is: " +difficultyValue);

            if(steps == difficultyValue) {
                stepsAtLevelUp = steps;
                level++;
                points += 5;
                difficultyValue = getDifficultyValue();
                mEditor.putInt("level", level);
                mEditor.putInt("points", points);
                mEditor.putInt("stepsAtLevelUp", stepsAtLevelUp);
                mEditor.putInt("difficultyValue", difficultyValue);
                mEditor.apply();

                enableAllStatButtons();

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.stepmanrunning);
                //Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                CharSequence title = "StepMan Level Up!";
                CharSequence contentText = "You just rose to Level " + level + "!";
                CharSequence contentSubText = "You have " + points + " points to spend!";

                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setSmallIcon(R.drawable.stepmanrunning)
                                .setLargeIcon(largeIcon)
                                .setContentTitle(title)
                                .setContentText(contentText)
                                .setSubText(contentSubText)
                                .setDefaults(-1)
                        ;
                // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(this, MainTabbedActivity.class);


                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainTabbedActivity.class);
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
                int mId = 1;
                mNotificationManager.notify(mId, mBuilder.build());


                //http://stackoverflow.com/questions/9631869/light-up-screen-when-notification-received-android

                /*
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
                */

            }

            TextView myStepView = (TextView) findViewById(R.id.mystepview);
            myStepView.setText("Steps: " + steps + "\n");

            TextView myStatLevelView = (TextView)findViewById(R.id.mystatlevelview);
            myStatLevelView.setText("Level: " + level);

            TextView myProfileLevelView = (TextView) findViewById(R.id.mylevelview);
            myProfileLevelView.setText("Level: " + level);

            TextView myStatPointView = (TextView)findViewById(R.id.statPoints);
            myStatPointView.setText("Points: " + points);

            TextView myStepTillView = (TextView)findViewById(R.id.mysteptillview);
            myStepTillView.setText("Steps till next level: " + (difficultyValue - steps));


            System.out.println("why bro?");
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
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        difficultyValue = getDifficultyValue();

        TextView myNameView = (TextView)findViewById(R.id.mynameview);
        myNameView.setText(name);

        TextView myLevelView = (TextView)findViewById(R.id.mylevelview);
        myLevelView.setText("Level: " + level);

        System.out.println("RESTARTdifficulty value at create view: " + difficultyValue);
        System.out.println("RESTARTsteps at create view: " + steps);
        TextView myStepTillView = (TextView)findViewById(R.id.mysteptillview);
        myStepTillView.setText("Steps till level up: " + (difficultyValue - steps));

        TextView myDifficultyView = (TextView)findViewById(R.id.mydifficultyview);
        myDifficultyView.setText("Difficulty: " + difficulty);

    }

    protected void onStop() {

        super.onStop();

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("difficultyValue", difficultyValue);
        mEditor.putInt("stepsAtLevelUp", stepsAtLevelUp);
        mEditor.apply();

        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);

        startService(intent);
    }

    public void onClickAddHP(View view){

        SharedPreferences.Editor mEditor = mPrefs.edit();

        points--;
        spentPoints++;
        hp++;

        mEditor.putInt("points", points);
        mEditor.putInt("spentPoints", spentPoints);
        mEditor.putInt("hp", hp);
        mEditor.apply();

        if(points == 0){
            disableAllStatButtons();
        }

        TextView myStatPointView = (TextView)findViewById(R.id.statPoints);
        myStatPointView.setText("Points: " + points);

        TextView myStatHP = (TextView)findViewById(R.id.statHP);
        myStatHP.setText(Integer.toString(hp));
    }

    public void onClickAddStrength(View view){

        SharedPreferences.Editor mEditor = mPrefs.edit();

        points--;
        spentPoints++;
        strength++;

        mEditor.putInt("points", points);
        mEditor.putInt("spentPoints", spentPoints);
        mEditor.putInt("strength", strength);
        mEditor.apply();

        if(points == 0){
            disableAllStatButtons();
        }

        TextView myStatPointView = (TextView)findViewById(R.id.statPoints);
        myStatPointView.setText("Points: " + points);

        TextView myStatStrength = (TextView)findViewById(R.id.statStrength);
        myStatStrength.setText(Integer.toString(strength));
    }

    public void onClickAddDefense(View view){

        SharedPreferences.Editor mEditor = mPrefs.edit();

        points--;
        spentPoints++;
        defense++;

        mEditor.putInt("points", points);
        mEditor.putInt("spentPoints", spentPoints);
        mEditor.putInt("defense", defense);
        mEditor.apply();

        if(points == 0){
            disableAllStatButtons();
        }

        TextView myStatPointView = (TextView)findViewById(R.id.statPoints);
        myStatPointView.setText("Points: " + points);

        TextView myStatDefense = (TextView)findViewById(R.id.statDefense);
        myStatDefense.setText(Integer.toString(defense));
    }

    public void onClickAddMagic(View view){

        SharedPreferences.Editor mEditor = mPrefs.edit();

        points--;
        spentPoints++;
        magic++;

        mEditor.putInt("points", points);
        mEditor.putInt("spentPoints", spentPoints);
        mEditor.putInt("magic", magic);
        mEditor.apply();

        if(points == 0){
            disableAllStatButtons();
        }

        TextView myStatPointView = (TextView)findViewById(R.id.statPoints);
        myStatPointView.setText("Points: " + points);

        TextView myStatMagic = (TextView)findViewById(R.id.statMagic);
        myStatMagic.setText(Integer.toString(magic));
    }

    public void onClickAddMagicDef(View view){

        SharedPreferences.Editor mEditor = mPrefs.edit();

        points--;
        spentPoints++;
        magicDef++;

        mEditor.putInt("points", points);
        mEditor.putInt("spentPoints", spentPoints);
        mEditor.putInt("magicDef", magicDef);
        mEditor.apply();

        if(points == 0){
            disableAllStatButtons();
        }

        TextView myStatPointView = (TextView)findViewById(R.id.statPoints);
        myStatPointView.setText("Points: " + points);

        TextView myStatMagicDef = (TextView)findViewById(R.id.statMagicDef);
        myStatMagicDef.setText(Integer.toString(magicDef));
    }

    public void onClickAddSpeed(View view){

        SharedPreferences.Editor mEditor = mPrefs.edit();

        points--;
        spentPoints++;
        speed++;

        mEditor.putInt("points", points);
        mEditor.putInt("spentPoints", spentPoints);
        mEditor.putInt("speed", speed);
        mEditor.apply();

        if(points == 0){
            disableAllStatButtons();
        }

        TextView myStatPointView = (TextView)findViewById(R.id.statPoints);
        myStatPointView.setText("Points: " + points);

        TextView myStatSpeed = (TextView)findViewById(R.id.statSpeed);
        myStatSpeed.setText(Integer.toString(speed));
    }

    public void resetStatPoints(View view){

        SharedPreferences.Editor mEditor = mPrefs.edit();
        points += spentPoints;
        spentPoints = 0;
        mEditor.putInt("points", points);
        mEditor.putInt("spentPoints", spentPoints);

        hp = 10;
        strength = 10;
        defense = 10;
        magic = 10;
        magicDef = 10;
        speed = 10;

        mEditor.putInt("hp", hp);
        mEditor.putInt("strength", strength);
        mEditor.putInt("defense", defense);
        mEditor.putInt("magic", magic);
        mEditor.putInt("magicDef", magicDef);
        mEditor.putInt("speed", speed);
        mEditor.apply();

        TextView myStatPointView = (TextView)findViewById(R.id.statPoints);
        myStatPointView.setText("Points: " + points);

        updateAllStats();

        enableAllStatButtons();

    }

    private void updateAllStats(){

        TextView myStatHP = (TextView)findViewById(R.id.statHP);
        myStatHP.setText(Integer.toString(hp));
        TextView myStatStrength = (TextView)findViewById(R.id.statStrength);
        myStatStrength.setText(Integer.toString(strength));
        TextView myStatDefense = (TextView)findViewById(R.id.statDefense);
        myStatDefense.setText(Integer.toString(defense));
        TextView myStatMagic = (TextView)findViewById(R.id.statMagic);
        myStatMagic.setText(Integer.toString(magic));
        TextView myStatMagicDef = (TextView)findViewById(R.id.statMagicDef);
        myStatMagicDef.setText(Integer.toString(magicDef));
        TextView myStatSpeed = (TextView)findViewById(R.id.statSpeed);
        myStatSpeed.setText(Integer.toString(speed));
    }

    private void enableAllStatButtons(){
        Button buttonHP = (Button)findViewById(R.id.buttonHP);
        buttonHP.setEnabled(true);
        Button buttonStrength = (Button)findViewById(R.id.buttonStrength);
        buttonStrength.setEnabled(true);
        Button buttonDefense = (Button)findViewById(R.id.buttonDefense);
        buttonDefense.setEnabled(true);
        Button buttonMagic = (Button)findViewById(R.id.buttonMagic);
        buttonMagic.setEnabled(true);
        Button buttonMagicDef = (Button)findViewById(R.id.buttonMagicDef);
        buttonMagicDef.setEnabled(true);
        Button buttonSpeed = (Button)findViewById(R.id.buttonSpeed);
        buttonSpeed.setEnabled(true);

    }

    private void disableAllStatButtons(){
        Button buttonHP = (Button)findViewById(R.id.buttonHP);
        buttonHP.setEnabled(false);
        Button buttonStrength = (Button)findViewById(R.id.buttonStrength);
        buttonStrength.setEnabled(false);
        Button buttonDefense = (Button)findViewById(R.id.buttonDefense);
        buttonDefense.setEnabled(false);
        Button buttonMagic = (Button)findViewById(R.id.buttonMagic);
        buttonMagic.setEnabled(false);
        Button buttonMagicDef = (Button)findViewById(R.id.buttonMagicDef);
        buttonMagicDef.setEnabled(false);
        Button buttonSpeed = (Button)findViewById(R.id.buttonSpeed);
        buttonSpeed.setEnabled(false);
    }

    public int getDifficultyValue(){

        System.out.println("calculating difficulty, stepsAtLevelUp was: " + stepsAtLevelUp);
        if(difficulty.contentEquals("Easy")){
            return stepsAtLevelUp + 10;
        }
        else if(difficulty.contentEquals("Normal")){
            return stepsAtLevelUp + (level * 2) * 10;
        }
        else if(difficulty.contentEquals("Hard")){
            return stepsAtLevelUp + (level * 2) * 50;
        }
        else if(difficulty.contentEquals("Impossible")){
            return stepsAtLevelUp + (level * 2) * 100;
        }
        return stepsAtLevelUp + 5;
    }
}
