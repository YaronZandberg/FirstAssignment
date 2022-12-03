package com.example.firstassignment;

import static com.example.firstassignment.CellPosition.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final Integer MAX_MOVES = 9;
    private final Integer O_PLAYER = 0;
    private final Integer X_PLAYER = 1;
    private Integer movesCounter;
    private boolean isGameActive = false;
    private Integer activePlayer = X_PLAYER;
    private CellPosition[] gameState = new CellPosition[this.MAX_MOVES];
    private final Integer[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    private Button startGameBtn;
    private ImageView playerMessage;
    private ImageView winStrike;
    //private List<ImageView> boardCells = new ArrayList();
    private ImageView cell1;
    private ImageView cell2;
    private ImageView cell3;
    private ImageView cell4;
    private ImageView cell5;
    private ImageView cell6;
    private ImageView cell7;
    private ImageView cell8;
    private ImageView cell9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.playerMessage = findViewById(R.id.board_player_message);
        this.playerMessage.setVisibility(View.INVISIBLE);
        this.winStrike = findViewById(R.id.board_win_strike);
        this.winStrike.setVisibility(View.INVISIBLE);
        this.startGameBtn = findViewById(R.id.board_play_again_button);
        this.startGameBtn.setText("Start Game");
        this.cell1 = findViewById(R.id.board_cell1);
        this.cell2 = findViewById(R.id.board_cell2);
        this.cell3 = findViewById(R.id.board_cell3);
        this.cell4 = findViewById(R.id.board_cell4);
        this.cell5 = findViewById(R.id.board_cell5);
        this.cell6 = findViewById(R.id.board_cell6);
        this.cell7 = findViewById(R.id.board_cell7);
        this.cell8 = findViewById(R.id.board_cell8);
        this.cell9 = findViewById(R.id.board_cell9);
        this.startGameBtn.setOnClickListener((view) -> startGame());
        this.cell1.setOnClickListener(this::playerTap);
        this.cell2.setOnClickListener(this::playerTap);
        this.cell3.setOnClickListener(this::playerTap);
        this.cell4.setOnClickListener(this::playerTap);
        this.cell5.setOnClickListener(this::playerTap);
        this.cell6.setOnClickListener(this::playerTap);
        this.cell7.setOnClickListener(this::playerTap);
        this.cell8.setOnClickListener(this::playerTap);
        this.cell9.setOnClickListener(this::playerTap);
    }

    public void startGame() {
        resetGame();
        this.isGameActive = true;
        this.startGameBtn.setVisibility(View.INVISIBLE);
        this.winStrike.setVisibility(View.INVISIBLE);
        this.playerMessage.setImageResource(R.drawable.xplay);
        this.playerMessage.setVisibility(View.VISIBLE);
    }

    public void resetGame() {
        this.activePlayer = X_PLAYER;
        this.movesCounter = 0;
        Arrays.fill(this.gameState, EMPTY);
        this.cell1.setImageResource(R.drawable.empty);
        this.cell1.setTag(1);
        this.cell2.setImageResource(R.drawable.empty);
        this.cell2.setTag(2);
        this.cell3.setImageResource(R.drawable.empty);
        this.cell3.setTag(3);
        this.cell4.setImageResource(R.drawable.empty);
        this.cell4.setTag(4);
        this.cell5.setImageResource(R.drawable.empty);
        this.cell5.setTag(5);
        this.cell6.setImageResource(R.drawable.empty);
        this.cell6.setTag(6);
        this.cell7.setImageResource(R.drawable.empty);
        this.cell7.setTag(7);
        this.cell8.setImageResource(R.drawable.empty);
        this.cell8.setTag(8);
        this.cell9.setImageResource(R.drawable.empty);
        this.cell9.setTag(9);
    }

    public void endGame() {
        this.isGameActive = false;
        this.startGameBtn.setText("Play Again");
        this.startGameBtn.setVisibility(View.VISIBLE);
    }

    public void playerTap(View view) {
        if (this.isGameActive) {
            ImageView currentBoardCellImage = (ImageView) view;
            Integer tappedCellNumber = Integer.parseInt(currentBoardCellImage.getTag().toString());

            if (this.gameState[tappedCellNumber] == EMPTY) {
                this.movesCounter++;

                if (Objects.equals(this.activePlayer, O_PLAYER)) {
                    currentBoardCellImage.setImageResource(R.drawable.o);
                    this.gameState[tappedCellNumber] = BLUE;
                    this.activePlayer = X_PLAYER;
                    this.playerMessage.setImageResource(R.drawable.xplay);
                } else {
                    currentBoardCellImage.setImageResource(R.drawable.x);
                    this.gameState[tappedCellNumber] = RED;
                    this.activePlayer = O_PLAYER;
                    this.playerMessage.setImageResource(R.drawable.oplay);
                }

                checkWinSituation();

                if (this.isGameActive && Objects.equals(this.movesCounter, this.MAX_MOVES)) {
                    this.playerMessage.setImageResource(R.drawable.nowin);
                    endGame();
                }
            }
        }
    }

    public void checkWinSituation() {
        for (int i = 0; i < this.winPositions.length; i++) {
            if (this.gameState[this.winPositions[i][0]] == this.gameState[this.winPositions[i][1]] &&
                    this.gameState[this.winPositions[i][1]] == this.gameState[this.winPositions[i][2]] &&
                    this.gameState[this.winPositions[i][0]] != EMPTY) {
                switch (i) {
                    case 0:
                        this.winStrike.setImageResource(R.drawable.high);
                        break;
                    case 1:
                        this.winStrike.setImageResource(R.drawable.horizontal);
                        break;
                    case 2:
                        this.winStrike.setImageResource(R.drawable.low);
                        break;
                    case 3:
                        this.winStrike.setImageResource(R.drawable.left);
                        break;
                    case 4:
                        this.winStrike.setImageResource(R.drawable.vertical);
                        break;
                    case 5:
                        this.winStrike.setImageResource(R.drawable.right);
                        break;
                    case 6:
                        this.winStrike.setImageResource(R.drawable.slashwin);
                        break;
                    case 7:
                        this.winStrike.setImageResource(R.drawable.backslashwin);
                        break;
                    default:
                        break;
                }

                this.winStrike.setVisibility(View.VISIBLE);

                if (this.gameState[this.winPositions[i][0]] == BLUE) {
                    this.playerMessage.setImageResource(R.drawable.owin);
                } else {
                    this.playerMessage.setImageResource(R.drawable.xwin);
                }

                endGame();
            }
        }
    }
}