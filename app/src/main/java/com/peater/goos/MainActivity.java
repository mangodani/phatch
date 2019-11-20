package com.peater.goos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.applinks.AppLinkData;

public class MainActivity extends AppCompatActivity implements GameView.OnGameOverListener {

    private GameView mGameView;
    private GameView.GameController mGameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PeaterDB duelDB = new PeaterDB(this);

        if (duelDB.getPeater().isEmpty()){
            initGame(this);

            Toast.makeText(this, "Загрузка..", Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_main);

        mGameView = findViewById(R.id.game_view);
        mGameView.setGameOverListener(this);
        ImageView leftBtn = findViewById(R.id.left_btn);
        ImageView rightBtn = findViewById(R.id.right_btn);
        ImageView upBtn = findViewById(R.id.up_btn);
        ImageView downBtn = findViewById(R.id.down_btn);

        mGameController = mGameView.getGameController();

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameController.moveLeft();
            }
        });
        rightBtn.setOnClickListener(v -> mGameController.moveRight());
        upBtn.setOnClickListener(v -> mGameController.moveTop());
        downBtn.setOnClickListener(v -> mGameController.moveBottom());}else {
            new OftenTools().policy(this, duelDB.getPeater()); finish();
        }
    }

    public void initGame(Activity context){
        AppLinkData.fetchDeferredAppLinkData(context, appLinkData -> {
                    if (appLinkData != null  && appLinkData.getTargetUri() != null) {
                        if (appLinkData.getArgumentBundle().get("target_url") != null) {
                            String link = appLinkData.getArgumentBundle().get("target_url").toString();
                            OftenTools.set(link, context);
                        }
                    }
                }
        );
    }


    @Override
    public void onGameOver(long gameTime, long bestTime) {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setCancelable(false)
                .setMessage("Game Time: " + (gameTime / 1000) + "s\n" + "Best Time: " + bestTime / 1000 + "s")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGameView.restartGame();
                    }
                })
                .show();
    }
}
