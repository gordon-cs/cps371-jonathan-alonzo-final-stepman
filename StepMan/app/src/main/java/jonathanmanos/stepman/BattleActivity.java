package jonathanmanos.stepman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class BattleActivity extends AppCompatActivity {

    public static int level;

    private static String name;
    private static String color;
    private static String difficulty;

    private static int hp;
    private static int strength;
    private static int defense;
    private static int magic;
    private static int magicDef;
    private static int speed;

    private static int enemyHP;
    private static String enemyName;
    private static String enemyType;

    private static int currentHP;
    private static int currentEnemyHP;

    private MediaPlayer battlePlayer;
    private MediaPlayer victoryPlayer;
    private MediaPlayer failurePlayer;
    private MediaPlayer punchPlayer;
    private MediaPlayer magicPlayer;

    private SharedPreferences mPrefs;
    private Handler h;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        mPrefs = getSharedPreferences("label", 0);

        name = mPrefs.getString("name", "");
        color = mPrefs.getString("color", "");
        difficulty = mPrefs.getString("difficulty", "");

        hp = mPrefs.getInt("hp", 10);
        currentHP = hp;
        strength = mPrefs.getInt("strength", 10);
        defense = mPrefs.getInt("defense", 10);
        magic = mPrefs.getInt("magic", 10);
        magicDef = mPrefs.getInt("magicDef", 10);
        speed = mPrefs.getInt("speed", 10);

        //testingcode
        /*
        hp = 1000;
        currentHP = hp;
        strength = 1000;
        defense = 1000;
        magic = 1000;
        magicDef = 1000;
        speed = 1000;
        */

        battlePlayer = MediaPlayer.create(this, R.raw.battle);
        victoryPlayer = MediaPlayer.create(this, R.raw.victory);
        failurePlayer = MediaPlayer.create(this, R.raw.failure);
        punchPlayer = MediaPlayer.create(this, R.raw.punches);
        magicPlayer = MediaPlayer.create(this, R.raw.fireball);


        battlePlayer.start();

        TextView battlename = (TextView)findViewById(R.id.battlename);
        battlename.setText(name);

        TextView battlehp = (TextView)findViewById(R.id.battlehp);
        battlehp.setText(Integer.toString(hp) + "/" + Integer.toString(hp));

        ImageView battleimage = (ImageView) findViewById(R.id.battleimage);

        System.out.println("color is: " + color);
        if (color.contentEquals("Black")) {
            battleimage.setImageResource(R.drawable.stepmanrunning);
        } else if (color.contentEquals("Blue")) {
            battleimage.setImageResource(R.drawable.stepmanrunning_blue);
        } else if (color.contentEquals("Green")) {
            battleimage.setImageResource(R.drawable.stepmanrunning_green);
        } else if (color.contentEquals("Red")) {
            battleimage.setImageResource(R.drawable.stepmanrunning_red);
        } else if (color.contentEquals("Luigi")) {
            battleimage.setImageResource(R.drawable.luigi);
        }

        ImageView battleenemyimage = (ImageView) findViewById(R.id.battleenemyimage);

        if(level == 1)
        {
            enemyHP = 100;
            currentEnemyHP = enemyHP;
            enemyType = "normal";
            enemyName = "Chicken";
            battleenemyimage.setImageResource(R.drawable.chicken);
        }
        if(level == 2)
        {
            enemyHP = 200;
            currentEnemyHP = enemyHP;
            enemyType = "fire";
            enemyName = "Paratroopa";
            battleenemyimage.setImageResource(R.drawable.paratroopa);
        }
        if(level == 3)
        {
            enemyHP = 500;
            currentEnemyHP = enemyHP;
            enemyType = "ice";
            enemyName = "Freezard";
            battleenemyimage.setImageResource(R.drawable.freezard);
        }
        if(level == 4)
        {
            enemyHP = 1000;
            currentEnemyHP = enemyHP;
            enemyType = "normal";
            enemyName = "Infernal";
            battleenemyimage.setImageResource(R.drawable.infernal);
        }
        if(level == 5)
        {
            enemyHP = 1800;
            currentEnemyHP = enemyHP;
            enemyType = "ice";
            enemyName = "Star Wolf";
            battleenemyimage.setImageResource(R.drawable.starwolf);
        }
        if(level == 6)
        {
            enemyHP = 3000;
            currentEnemyHP = enemyHP;
            enemyType = "fire";
            enemyName = "Ridley";
            battleenemyimage.setImageResource(R.drawable.ridley);
        }
        if(level == 7)
        {
            enemyHP = 4500;
            currentEnemyHP = enemyHP;
            enemyType = "normal";
            enemyName = "Sephiroth";
            battleenemyimage.setImageResource(R.drawable.sephiroth);
        }
        if(level == 8)
        {
            enemyHP = 6000;
            currentEnemyHP = enemyHP;
            enemyType = "fire";
            enemyName = "Groudon";
            battleenemyimage.setImageResource(R.drawable.groudon);
        }
        if(level == 9)
        {
            enemyHP = 8000;
            currentEnemyHP = enemyHP;
            enemyType = "ice";
            enemyName = "Necron";
            battleenemyimage.setImageResource(R.drawable.necron);
        }
        if(level == 10)
        {
            enemyHP = 9999;
            currentEnemyHP = enemyHP;
            enemyType = "normal";
            enemyName = "Master Hand";
            battleenemyimage.setImageResource(R.drawable.masterhand);
        }

        TextView battleenemy = (TextView)findViewById(R.id.battleenemy);
        battleenemy.setText(enemyName);

        TextView battleenemyhp = (TextView)findViewById(R.id.battleenemyhp);
        battleenemyhp.setText(Integer.toString(enemyHP) + "/" + Integer.toString(enemyHP));

        h = new Handler();
    }

    public void enemyAttack() {
        if(currentEnemyHP <= 0)
            updatePage();
        else{
            punchPlayer.start();
            TextView battleenemyhp = (TextView)findViewById(R.id.battleenemyhp);
            battleenemyhp.setText(Integer.toString(currentEnemyHP) + "/" + Integer.toString(enemyHP));

            ImageView myanimation = (ImageView) findViewById(R.id.myanimation);
            myanimation.setImageResource(R.drawable.pow);
            myanimation.setAlpha(.6F);

            int attack = 0;
            Random rand = new Random();

            if (level == 1) {
                attack = 15;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(4);
            }
            if (level == 2) {
                attack = 20;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(6);
            }
            if (level == 3) {
                attack = 30;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(10);
            }
            if (level == 4) {
                attack = 40;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(14);
            }
            if (level == 5) {
                attack = 50;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(18);
            }
            if (level == 6) {
                attack = 60;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(20);
            }
            if (level == 7) {
                attack = 70;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(25);
            }
            if (level == 8) {
                attack = 80;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(30);
            }
            if (level == 9) {
                attack = 90;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(40);
            }
            if (level == 10) {
                attack = 100;
                attack -= (defense + magicDef)/2;
                if(attack < 1)
                    attack = 1;
                attack += rand.nextInt(50);
            }

            if(attack < 1)
                attack = 1;

            currentHP -= attack;

            TextView battlepopup = (TextView)findViewById(R.id.battlepopup);
            battlepopup.setText(Integer.toString(attack));

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    ImageView myanimation = (ImageView) findViewById(R.id.myanimation);
                    myanimation.setImageResource(android.R.color.transparent);

                    TextView battlepopup = (TextView)findViewById(R.id.battlepopup);
                    battlepopup.setText("");


                    updatePage();
                }
            };
            h.postDelayed(r, 1000);
        }
    }

    public void updatePage() {

        if(currentEnemyHP <= 0)
            currentEnemyHP = 0;
        if(currentHP <= 0)
            currentHP = 0;

        TextView battlehp = (TextView)findViewById(R.id.battlehp);
        battlehp.setText(Integer.toString(currentHP) + "/" + Integer.toString(hp));

        TextView battleenemyhp = (TextView)findViewById(R.id.battleenemyhp);
        battleenemyhp.setText(Integer.toString(currentEnemyHP) + "/" + Integer.toString(enemyHP));

        if(currentHP == 0)
        {
            ImageView myanimation = (ImageView) findViewById(R.id.myanimation);
            myanimation.setImageResource(R.drawable.skull);
            myanimation.setAlpha(.8F);

            Runnable skull = new Runnable() {
                @Override
                public void run(){
                    ImageView myanimation = (ImageView) findViewById(R.id.myanimation);
                    myanimation.setImageResource(android.R.color.transparent);
                    ImageView battleimage = (ImageView) findViewById(R.id.battleimage);
                    battleimage.setImageResource(android.R.color.transparent);
                }
            };
            Runnable youfailed = new Runnable() {
                @Override
                public void run(){
                    battlePlayer.stop();
                    failurePlayer.start();
                    ImageView battleimage = (ImageView) findViewById(R.id.battleimage);
                    battleimage.setImageResource(R.drawable.failure);
                }
            };
            Runnable backtoworld = new Runnable() {
                @Override
                public void run(){
                    finish();
                }
            };
            h.postDelayed(skull, 1000);
            h.postDelayed(youfailed, 2000);
            h.postDelayed(backtoworld, 4000);
        }

        if(currentEnemyHP == 0){
            battlePlayer.stop();
            victoryPlayer.start();
            ImageView enemyanimation = (ImageView) findViewById(R.id.enemyanimation);
            enemyanimation.setImageResource(R.drawable.skull);
            enemyanimation.setAlpha(.8F);

            Runnable skull = new Runnable() {
                @Override
                public void run(){
                    ImageView enemyanimation = (ImageView) findViewById(R.id.enemyanimation);
                    enemyanimation.setImageResource(android.R.color.transparent);
                    ImageView battleenemyimage = (ImageView) findViewById(R.id.battleenemyimage);
                    battleenemyimage.setImageResource(android.R.color.transparent);
                }
            };
            Runnable youdidit = new Runnable() {
                @Override
                public void run(){
                    ImageView battleenemyimage = (ImageView) findViewById(R.id.battleenemyimage);
                    battleenemyimage.setImageResource(R.drawable.youdidit);
                }
            };
            Runnable backtoworld = new Runnable() {
                @Override
                public void run(){
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    if(level == mPrefs.getInt("worldLevel", 1))
                    mEditor.putInt("worldLevel", level+1).apply();
                    WorldActivity.needToRecreate = true;
                    finish();
                }
            };
            h.postDelayed(skull, 1000);
            h.postDelayed(youdidit, 2000);
            h.postDelayed(backtoworld, 4000);
        }

        if(currentEnemyHP > 0 && currentHP > 0)
            enableButtons();
    }

    public void blazeKick(View view) {
        disableButtons();
        magicPlayer.start();
        ImageView enemyanimation = (ImageView) findViewById(R.id.enemyanimation);
        enemyanimation.setImageResource(R.drawable.fire);
        enemyanimation.setAlpha(.8F);

        int attack = 0;
        if (enemyType.equals("normal")) {
            attack = magic;
        }
        else if (enemyType.equals("ice")) {
            attack = magic + speed + strength;
        }
        else if (enemyType.equals("fire")) {
            attack = magic / 2;
        }
        if((int)(Math.random() * 10) == 0)
        {
            attack *= 2;
        }
        currentEnemyHP -= attack;

        TextView battlepopupenemy = (TextView)findViewById(R.id.battlepopupenemy);
        battlepopupenemy.setText(Integer.toString(attack));

        Runnable r = new Runnable() {
            @Override
            public void run() {
                ImageView enemyanimation = (ImageView) findViewById(R.id.enemyanimation);
                enemyanimation.setImageResource(android.R.color.transparent);

                TextView battlepopupenemy = (TextView)findViewById(R.id.battlepopupenemy);
                battlepopupenemy.setText("");

                enemyAttack();
            }
        };
        h.postDelayed(r, 1000);
    }
    public void iceStrike(View view) {
        magicPlayer.start();
        disableButtons();
        ImageView enemyanimation = (ImageView) findViewById(R.id.enemyanimation);
        enemyanimation.setImageResource(R.drawable.icecube);
        enemyanimation.setAlpha(.8F);

        int attack = 0;
        if(enemyType.equals("normal")) {
            attack = magic;
        }
        else if(enemyType.equals("ice")) {
            attack = magic/2;
        }
        else if(enemyType.equals("fire")) {
            attack = magic + speed + strength;
        }
        if((int)(Math.random() * 10) == 0)
        {
            attack *= 2;
        }

        currentEnemyHP -= attack;

        TextView battlepopupenemy = (TextView)findViewById(R.id.battlepopupenemy);
        battlepopupenemy.setText(Integer.toString(attack));

        Runnable r = new Runnable() {
            @Override
            public void run(){
                ImageView enemyanimation = (ImageView) findViewById(R.id.enemyanimation);
                enemyanimation.setImageResource(android.R.color.transparent);

                TextView battlepopupenemy = (TextView)findViewById(R.id.battlepopupenemy);
                battlepopupenemy.setText("");

                enemyAttack();
            }
        };
        h.postDelayed(r, 1000);
    }

    public void megaPunch(View view) {
        punchPlayer.start();
        disableButtons();
        ImageView enemyanimation = (ImageView) findViewById(R.id.enemyanimation);
        enemyanimation.setImageResource(R.drawable.punch);
        enemyanimation.setAlpha(.8F);

        int attack = 0;
        if (enemyType.equals("normal")) {
            attack = strength * 2;
        } else if (enemyType.equals("ice")) {
            attack = strength;
        } else if (enemyType.equals("fire")) {
            attack = strength;
        }
        if((int)(Math.random() * 10) == 0)
        {
            attack *= 2;
        }
        currentEnemyHP -= attack;

        TextView battlepopupenemy = (TextView)findViewById(R.id.battlepopupenemy);
        battlepopupenemy.setText(Integer.toString(attack));

        Runnable r = new Runnable() {
            @Override
            public void run() {
                ImageView enemyanimation = (ImageView) findViewById(R.id.enemyanimation);
                enemyanimation.setImageResource(android.R.color.transparent);

                TextView battlepopupenemy = (TextView)findViewById(R.id.battlepopupenemy);
                battlepopupenemy.setText("");

                enemyAttack();
            }
        };
        h.postDelayed(r,1000);
    }

    public void goToWorld(View view) {
        this.finish();
    }

    private void disableButtons(){
        float alphaValue = .65F;

        Button blazekickbutton = (Button) findViewById(R.id.blazekickbutton);
        blazekickbutton.setAlpha(alphaValue);
        blazekickbutton.setEnabled(false);
        Button icestrikebutton = (Button) findViewById(R.id.icestrikebutton);
        icestrikebutton.setAlpha(alphaValue);
        icestrikebutton.setEnabled(false);
        Button megapunchbutton = (Button) findViewById(R.id.megapunchbutton);
        megapunchbutton.setAlpha(alphaValue);
        megapunchbutton.setEnabled(false);
        Button fleebutton = (Button) findViewById(R.id.fleebutton);
        fleebutton.setAlpha(alphaValue);
        fleebutton.setEnabled(false);

    }

    private void enableButtons(){
        Button blazekickbutton = (Button) findViewById(R.id.blazekickbutton);
        blazekickbutton.setAlpha(1F);
        blazekickbutton.setEnabled(true);
        Button icestrikebutton = (Button) findViewById(R.id.icestrikebutton);
        icestrikebutton.setAlpha(1F);
        icestrikebutton.setEnabled(true);
        Button megapunchbutton = (Button) findViewById(R.id.megapunchbutton);
        megapunchbutton.setAlpha(1F);
        megapunchbutton.setEnabled(true);
        Button fleebutton = (Button) findViewById(R.id.fleebutton);
        fleebutton.setAlpha(1F);
        fleebutton.setEnabled(true);
    }

    protected void onStop() {
        super.onStop();
        battlePlayer.stop();
        victoryPlayer.stop();
        failurePlayer.stop();

        battlePlayer.reset();
        victoryPlayer.reset();
        failurePlayer.reset();
        punchPlayer.reset();
        magicPlayer.reset();

        battlePlayer.release();
        victoryPlayer.release();
        failurePlayer.release();
        punchPlayer.release();
        magicPlayer.release();
        finish();
    }
}
