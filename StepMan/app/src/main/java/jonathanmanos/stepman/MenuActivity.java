package jonathanmanos.stepman;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class MenuActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private Intent stepCounterIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mPrefs = getSharedPreferences("label", 0);
        stepCounterIntent = new Intent(this, StepCounterService.class);
        startService(stepCounterIntent);
    }


    public void goToGame(View view) {
        Intent intent = new Intent(getApplicationContext(), WorldActivity.class);
        startActivity(intent);
    }

    public void goToProfile(View view){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("firstTab", 0).apply();
        Intent intent = new Intent(getApplicationContext(), MainTabbedActivity.class);
        startActivity(intent);
    }

    public void goToStats(View view){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("firstTab", 1).apply();
        Intent intent = new Intent(getApplicationContext(), MainTabbedActivity.class);
        startActivity(intent);
    }

    public void goToStepStats(View view){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("firstTab", 2).apply();
        Intent intent = new Intent(getApplicationContext(), MainTabbedActivity.class);
        startActivity(intent);
    }
    public void goToSettings(View view){

        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    protected void onStop() {
        super.onStop();

    }

}
