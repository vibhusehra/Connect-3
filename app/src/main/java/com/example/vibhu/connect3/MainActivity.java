package com.example.vibhu.connect3;

import android.hardware.input.InputManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer=0; //0 = yellow    1 = red

    int[] gameState = {2,2,2,2,2,2,2,2,2};  //2 means that the place is empty

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    boolean gameIsActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view){


        ImageView image =(ImageView) view;

        int imageTag = Integer.parseInt(image.getTag().toString());


        if(gameState[imageTag] == 2 && gameIsActive) {

            gameState[imageTag] = activePlayer;
            image.setTranslationY(-1000f);

            if (activePlayer == 0) {
                image.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                image.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            image.animate().translationYBy(1000f).setDuration(500);
        }

        for(int[] winningPosition: winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2
                    ){

                //if someone has won

                gameIsActive = false;
                TextView winnerMessage = findViewById(R.id.winnerMessage);

                LinearLayout playAgainLayout = findViewById(R.id.playAgainLayout);
                playAgainLayout.setVisibility(View.VISIBLE);
                if(gameState[winningPosition[0]] == 0){
                    winnerMessage.setText("Yellow won!");
                    playAgainLayout.setBackgroundResource(R.color.playAgainLayout0);
                }
                else{
                    winnerMessage.setText("Red won!");
                    playAgainLayout.setBackgroundResource(R.color.playAgainLayout1);
                }
                break;
            }
            else{
                boolean gameIsOver = true;
                for(int counter: gameState){
                    if(counter == 2) gameIsOver = false;
                }

                if(gameIsOver){
                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText("It's a draw!");
                    LinearLayout playAgainLayout = findViewById(R.id.playAgainLayout);
                    playAgainLayout.setBackgroundResource(R.color.playAgainLayout2);
                    playAgainLayout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view){

        gameIsActive = true;
        LinearLayout playAgainLayout = findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for(int i=0;i<gameState.length;i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i=0;i<gridLayout.getChildCount();i++){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}
