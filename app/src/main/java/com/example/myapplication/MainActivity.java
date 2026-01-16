package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView userMoveImg, computerMoveImg;
    private ImageView rockBtn, paperBtn, scissorsBtn;
    private TextView winnerTv, playerScoreTv, computerScoreTv;
    private Button restartBtn;

    private int playerScore = 0;
    private int computerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // If you're using EdgeToEdge, keep this
        setContentView(R.layout.activity_main);

        // For EdgeToEdge layouts, attach window insets if 'main' is the root LinearLayout id
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userMoveImg = findViewById(R.id.user_move_img);
        computerMoveImg = findViewById(R.id.computer_move_img);
        rockBtn = findViewById(R.id.rock_btn);
        paperBtn = findViewById(R.id.paper_btn);
        scissorsBtn = findViewById(R.id.scissors_btn);
        winnerTv = findViewById(R.id.winner_tv);
        playerScoreTv = findViewById(R.id.player_score);
        computerScoreTv = findViewById(R.id.computer_score);
        restartBtn = findViewById(R.id.restart_btn);

        rockBtn.setOnClickListener(v -> {
            animateButton(v);
            playTurn("rock");
        });
        paperBtn.setOnClickListener(v -> {
            animateButton(v);
            playTurn("paper");
        });
        scissorsBtn.setOnClickListener(v -> {
            animateButton(v);
            playTurn("scissors");
        });
        restartBtn.setOnClickListener(v -> resetGame());

        resetGame();
    }

    // Animation helper method for button presses
    private void animateButton(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f);
        scaleX.setDuration(300);
        scaleY.setDuration(300);
        scaleX.start();
        scaleY.start();
    }

    private void playTurn(String userChoice) {
        userMoveImg.setImageResource(getImageResource(userChoice));
        String computerChoice = getComputerChoice();
        computerMoveImg.setImageResource(getImageResource(computerChoice));
        String result = getResult(userChoice, computerChoice);
        winnerTv.setText(result);

        if (result.equals("Player has won")) {
            playerScore++;
        } else if (result.equals("Computer has won")) {
            computerScore++;
        }
        playerScoreTv.setText(String.valueOf(playerScore));
        computerScoreTv.setText(String.valueOf(computerScore));
    }

    private String getComputerChoice() {
        String[] choices = {"rock", "paper", "scissors"};
        int index = new Random().nextInt(choices.length);
        return choices[index];
    }

    private int getImageResource(String choice) {
        switch (choice) {
            case "rock":
                return R.drawable.rock;
            case "paper":
                return R.drawable.paper;
            case "scissors":
                return R.drawable.download;  // Make sure this drawable exists or rename appropriately
            default:
                return R.drawable.question_mark;
        }
    }

    private String getResult(String user, String computer) {
        if (user.equals(computer)) {
            return "Draw";
        } else if ((user.equals("rock") && computer.equals("scissors")) ||
                (user.equals("paper") && computer.equals("rock")) ||
                (user.equals("scissors") && computer.equals("paper"))) {
            return "Player has won";
        } else {
            return "Computer has won";
        }
    }

    private void resetGame() {
        playerScore = 0;
        computerScore = 0;
        playerScoreTv.setText("0");
        computerScoreTv.setText("0");
        winnerTv.setText("");
        userMoveImg.setImageResource(R.drawable.question_mark);
        computerMoveImg.setImageResource(R.drawable.question_mark);
    }
}
