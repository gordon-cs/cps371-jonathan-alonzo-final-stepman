package jonathanmanos.stepman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private Spinner spinnerColor;
    private Spinner spinnerDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mPrefs = getSharedPreferences("label", 0);
        String name = mPrefs.getString("name","");
        String color = mPrefs.getString("color","");
        String difficulty = mPrefs.getString("difficulty","");

        AutoCompleteTextView newNameView = (AutoCompleteTextView) findViewById(R.id.name);
        newNameView.setText(name);

        spinnerColor = (Spinner)findViewById(R.id.spinnerColor);
        String[] colors = new String[]{"Black", "Red", "Green", "Blue"};
        ArrayAdapter<String> adapterColor = new ArrayAdapter<String>(SettingsActivity.this,
                android.R.layout.simple_spinner_item,colors);

        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapterColor);
        if(color.contentEquals("Black"))
            spinnerColor.setSelection(0);
        else if(color.contentEquals("Red"))
            spinnerColor.setSelection(1);
        else if(color.contentEquals("Green"))
            spinnerColor.setSelection(2);
        else if(color.contentEquals("Blue"))
            spinnerColor.setSelection(3);

        spinnerDifficulty = (Spinner)findViewById(R.id.spinnerDifficulty);
        String[] difficulties = new String[]{"Easy", "Normal", "Hard", "Impossible"};
        ArrayAdapter<String>adapterDifficulty = new ArrayAdapter<String>(SettingsActivity.this,
                android.R.layout.simple_spinner_item,difficulties);

        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
        spinnerDifficulty.setSelection(0);
        if(difficulty.contentEquals("Easy"))
            spinnerDifficulty.setSelection(0);
        else if(difficulty.contentEquals("Normal"))
            spinnerDifficulty.setSelection(1);
        else if(difficulty.contentEquals("Hard"))
            spinnerDifficulty.setSelection(2);
        else if(difficulty.contentEquals("Impossible"))
            spinnerDifficulty.setSelection(3);
    }


    public void saveSettings(View view) {

        SharedPreferences.Editor mEditor = mPrefs.edit();

        AutoCompleteTextView newNameView = (AutoCompleteTextView) findViewById(R.id.name);
        String newName = newNameView.getText().toString();
        String color = spinnerColor.getSelectedItem().toString();
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        if (!mPrefs.getString("difficulty", "").contentEquals(difficulty))
        {
            System.out.println("changing steps at level up to: " + mPrefs.getInt("steps", 0));
            MainTabbedActivity.stepsAtLevelUp = mPrefs.getInt("steps", 0);
            mEditor.putInt("stepsAtLevelUp", MainTabbedActivity.stepsAtLevelUp);
            mEditor.apply();
        }

        MainTabbedActivity.name = newName;
        MainTabbedActivity.color = color;
        MainTabbedActivity.difficulty = difficulty;
        MainTabbedActivity.stepsAtLevelUp = mPrefs.getInt("steps", 0);

        mEditor.putString("name", newName);
        mEditor.putString("color", color);
        mEditor.putString("difficulty", difficulty);
        mEditor.commit();

        MainTabbedActivity.mainActivity.finish();
        Intent intent = new Intent(getApplicationContext(), MainTabbedActivity.class);
        startActivity(intent);
        finish();
    }

    public void deleteAccount(View view){

        mPrefs.edit().clear().commit();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
