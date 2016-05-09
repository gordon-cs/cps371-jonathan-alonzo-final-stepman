package jonathanmanos.stepman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorldActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private static int worldLevel;
    public static boolean needToRecreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        needToRecreate = false;

        mPrefs = getSharedPreferences("label", 0);
        worldLevel = mPrefs.getInt("worldLevel", 1);

        disableButtons();
    }

    public void goToBattle1(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 1;
    }
    public void goToBattle2(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 2;
    }
    public void goToBattle3(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 3;
    }

    public void goToBattle4(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 4;
    }

    public void goToBattle5(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 5;
    }

    public void goToBattle6(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 6;
    }

    public void goToBattle7(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 7;
    }

    public void goToBattle8(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 8;
    }

    public void goToBattle9(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 9;
    }

    public void goToBattle10(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        BattleActivity.level = 10;
    }

    private void disableButtons(){

        float alphaValue = .65F;

        if(worldLevel <= 9) {
            Button buttonten = (Button) findViewById(R.id.buttonten);
            buttonten.setAlpha(alphaValue);
            buttonten.setEnabled(false);
        }
        if(worldLevel <= 8) {
            Button buttonnine = (Button) findViewById(R.id.buttonnine);
            buttonnine.setAlpha(alphaValue);
            buttonnine.setEnabled(false);
        }
        if(worldLevel <= 7) {
            Button buttoneight = (Button) findViewById(R.id.buttoneight);
            buttoneight.setAlpha(alphaValue);
            buttoneight.setEnabled(false);
        }
        if(worldLevel <= 6) {
            Button buttonseven = (Button) findViewById(R.id.buttonseven);
            buttonseven.setAlpha(alphaValue);
            buttonseven.setEnabled(false);
        }
        if(worldLevel <= 5) {
            Button buttonsix = (Button) findViewById(R.id.buttonsix);
            buttonsix.setAlpha(alphaValue);
            buttonsix.setEnabled(false);
        }
        if(worldLevel <= 4) {
            Button buttonfive = (Button) findViewById(R.id.buttonfive);
            buttonfive.setAlpha(alphaValue);
            buttonfive.setEnabled(false);
        }
        if(worldLevel <= 3) {
            Button buttonfour = (Button) findViewById(R.id.buttonfour);
            buttonfour.setAlpha(alphaValue);
            buttonfour.setEnabled(false);
        }
        if(worldLevel <= 2) {
            Button buttonthree = (Button) findViewById(R.id.buttonthree);
            buttonthree.setAlpha(alphaValue);
            buttonthree.setEnabled(false);
        }
        if(worldLevel <= 1) {
            Button buttontwo = (Button) findViewById(R.id.buttontwo);
            buttontwo.setAlpha(alphaValue);
            buttontwo.setEnabled(false);
        }
    }

    protected void onResume(){
        super.onResume();

        if(needToRecreate)
            this.recreate();
    }

    protected void onStop() {
        super.onStop();
    }

}
