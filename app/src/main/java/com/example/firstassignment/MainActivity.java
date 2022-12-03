package com.example.firstassignment;

import static com.example.firstassignment.CellPosition.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final Integer MAX_MOVES = 9;
    private final Integer O_PLAYER = 0;
    private final Integer X_PLAYER = 1;
    private final List<ImageView> boardCells = new ArrayList();
    private final CellPosition[] gameState = new CellPosition[this.MAX_MOVES];
    private final Integer[] cellIDs = {R.id.board_cell1, R.id.board_cell2, R.id.board_cell3,
            R.id.board_cell4, R.id.board_cell5, R.id.board_cell6, R.id.board_cell7,
            R.id.board_cell8, R.id.board_cell9};
    private final Integer[] winsSituations = {R.drawable.high, R.drawable.horizontal, R.drawable.low,
            R.drawable.left, R.drawable.vertical, R.drawable.right,
            R.drawable.slashwin, R.drawable.backslashwin};
    private final Integer[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    private boolean isGameActive = false;
    private Integer movesCounter;
    private Integer activePlayer;
    private Button startGameBtn;
    private ImageView playerMessage;
    private ImageView winStrike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.activePlayer = X_PLAYER;
        this.playerMessage = findViewById(R.id.board_player_message);
        this.playerMessage.setVisibility(View.INVISIBLE);
        this.winStrike = findViewById(R.id.board_win_strike);
        this.winStrike.setVisibility(View.INVISIBLE);
        this.startGameBtn = findViewById(R.id.board_play_again_button);
        this.startGameBtn.setText("Start Game");
        initializeCells(this.boardCells);
        this.startGameBtn.setOnClickListener((view) -> startGame());
    }

    private void initializeCells(List<ImageView> boardCells) {
        for (int i = 0; i < this.cellIDs.length; i++) {
            boardCells.add(findViewById(this.cellIDs[i]));
            boardCells.get(i).setOnClickListener(this::playerTurn);
        }
    }

    private void startGame() {
        resetGame();
        this.isGameActive = true;
        this.startGameBtn.setVisibility(View.INVISIBLE);
        this.winStrike.setVisibility(View.INVISIBLE);
        this.playerMessage.setImageResource(R.drawable.xplay);
        this.playerMessage.setVisibility(View.VISIBLE);
    }

    private void resetGame() {
        this.activePlayer = X_PLAYER;
        this.movesCounter = 0;
        Arrays.fill(this.gameState, EMPTY);
        for (int i = 0; i < this.MAX_MOVES; i++) {
            this.boardCells.get(i).setImageResource(R.drawable.empty);
            this.boardCells.get(i).setTag(i);
        }
    }

    private void endGame() {
        this.isGameActive = false;
        this.startGameBtn.setText("Play Again");
        this.startGameBtn.setVisibility(View.VISIBLE);
    }

    private void playerTurn(View view) {
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

    private void checkWinSituation() {
        for (int i = 0; i < this.winPositions.length; i++) {
            if (!Objects.equals(this.gameState[this.winPositions[i][0]], EMPTY) &&
                    Objects.equals(this.gameState[this.winPositions[i][0]], this.gameState[this.winPositions[i][1]]) &&
                    Objects.equals(this.gameState[this.winPositions[i][1]], this.gameState[this.winPositions[i][2]])) {

                this.winStrike.setImageResource(this.winsSituations[i]);
                this.winStrike.setVisibility(View.VISIBLE);

                if (Objects.equals(this.gameState[this.winPositions[i][0]], BLUE)) {
                    this.playerMessage.setImageResource(R.drawable.owin);
                } else {
                    this.playerMessage.setImageResource(R.drawable.xwin);
                }

                endGame();
            }
        }
    }
}