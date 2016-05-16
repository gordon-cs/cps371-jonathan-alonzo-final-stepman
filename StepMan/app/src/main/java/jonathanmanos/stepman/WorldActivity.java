package jonathanmanos.stepman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WorldActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private static int worldLevel;
    public static boolean needToRecreate;
    private static final float alphaValue = .5F;

    public static MediaPlayer battlePlayer;
    public static MediaPlayer victoryPlayer;
    public static MediaPlayer failurePlayer;
    public static MediaPlayer punchPlayer;
    public static MediaPlayer magicPlayer;
    public static MediaPlayer booPlayer;
    public static MediaPlayer enemyPunchPlayer;

    public static int currentHP;
    public static int currentEnemyHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        needToRecreate = false;
        battlePlayer = MediaPlayer.create(this, R.raw.battle);
        victoryPlayer = MediaPlayer.create(this, R.raw.victory);
        failurePlayer = MediaPlayer.create(this, R.raw.failure);
        punchPlayer = MediaPlayer.create(this, R.raw.punches);
        magicPlayer = MediaPlayer.create(this, R.raw.fireball);
        booPlayer = MediaPlayer.create(this, R.raw.boo);
        enemyPunchPlayer = MediaPlayer.create(this, R.raw.punches);

        mPrefs = getSharedPreferences("label", 0);
        worldLevel = mPrefs.getInt("worldLevel", 1);

        disableButtons();
        showBeatenWorlds();
    }

    public void goToBattle1(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(1);
        setNewLevel();
    }
    public void goToBattle2(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(2);
        setNewLevel();
    }
    public void goToBattle3(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(3);
        setNewLevel();
    }

    public void goToBattle4(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(4);
        setNewLevel();
    }

    public void goToBattle5(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(5);
        setNewLevel();
    }

    public void goToBattle6(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(6);
        setNewLevel();
    }

    public void goToBattle7(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(7);
        setNewLevel();
    }

    public void goToBattle8(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(8);
        setNewLevel();
    }

    public void goToBattle9(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(9);
        setNewLevel();
    }

    public void goToBattle10(View view) {
        Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
        startActivity(intent);
        setLevel(10);
        setNewLevel();
    }

    private void setLevel(int i){
        BattleActivity.level = i;
    }

    private void setNewLevel(){
        BattleActivity.newLevel = true;
    }

    private void disableButtons(){

        if(worldLevel <= 9)
            hideButton((Button) findViewById(R.id.world1button10));
        if(worldLevel <= 8)
            hideButton((Button) findViewById(R.id.world1button9));
        if(worldLevel <= 7)
            hideButton((Button) findViewById(R.id.world1button8));
        if(worldLevel <= 6)
            hideButton((Button) findViewById(R.id.world1button7));
        if(worldLevel <= 5)
            hideButton((Button) findViewById(R.id.world1button6));
        if(worldLevel <= 4)
            hideButton((Button) findViewById(R.id.world1button5));
        if(worldLevel <= 3)
            hideButton((Button) findViewById(R.id.world1button4));
        if(worldLevel <= 2)
            hideButton((Button) findViewById(R.id.world1button3));
        if(worldLevel <= 1)
            hideButton((Button) findViewById(R.id.world1button2));

    }

    private void showBeatenWorlds(){

        if(worldLevel > 1) {
            ((ImageView) findViewById(R.id.world1button1image)).setImageResource(R.drawable.w11chicken);
            ((Button) findViewById(R.id.world1button1)).setText("");
        }
        if(worldLevel > 2){
            ((ImageView) findViewById(R.id.world1button2image)).setImageResource(R.drawable.w12paratroopa);
            ((Button) findViewById(R.id.world1button2)).setText("");
        }
        if(worldLevel > 3){
            ((ImageView) findViewById(R.id.world1button3image)).setImageResource(R.drawable.w13freezard);
            ((Button) findViewById(R.id.world1button3)).setText("");
        }
        if(worldLevel > 4){
            ((ImageView) findViewById(R.id.world1button4image)).setImageResource(R.drawable.w14infernal);
            ((Button) findViewById(R.id.world1button4)).setText("");
        }
        if(worldLevel > 5){
            ((ImageView) findViewById(R.id.world1button5image)).setImageResource(R.drawable.w15starwolf);
            ((Button) findViewById(R.id.world1button5)).setText("");
        }
        if(worldLevel > 6){
            ((ImageView) findViewById(R.id.world1button6image)).setImageResource(R.drawable.w16ridley);
            ((Button) findViewById(R.id.world1button6)).setText("");
        }
        if(worldLevel > 7){
            ((ImageView) findViewById(R.id.world1button7image)).setImageResource(R.drawable.w17sephiroth);
            ((Button) findViewById(R.id.world1button7)).setText("");
        }
        if(worldLevel > 8){
            ((ImageView) findViewById(R.id.world1button8image)).setImageResource(R.drawable.w18groudon);
            ((Button) findViewById(R.id.world1button8)).setText("");
        }
        if(worldLevel > 9){
            ((ImageView) findViewById(R.id.world1button9image)).setImageResource(R.drawable.w19necron);
            ((Button) findViewById(R.id.world1button9)).setText("");
        }
        if(worldLevel > 10){
            ((ImageView) findViewById(R.id.world1button10image)).setImageResource(R.drawable.w110masterhand);
            ((Button) findViewById(R.id.world1button10)).setText("");
        }

    }

    private void hideButton(Button button){
        button.setAlpha(alphaValue);
        button.setEnabled(false);
        button.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            // do stuff
        } else {
            //It's an orientation change.
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
