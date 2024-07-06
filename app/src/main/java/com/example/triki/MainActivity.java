package com.example.triki;

import android.widget.ImageButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private ImageButton[][] buttons = new ImageButton[3][3];
    private boolean playerXTurn = true;
    private TextView textViewTurn;
    private GridLayout gridLayoutBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTurn = findViewById(R.id.textViewTurn);
        gridLayoutBoard = findViewById(R.id.gridLayoutBoard);

        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCellClick(v);
                    }
                });
            }
        }
    }

    public void onCellClick(View v)
    {
        ImageButton clickedButton = (ImageButton) v;

        // Only allow click if the cell is empty
        if(clickedButton.getDrawable() == null)
        {
            if (playerXTurn) {
                clickedButton.setImageResource(R.drawable.boton_1);
                clickedButton.setTag(R.drawable.boton_1);
                textViewTurn.setText("Turno de Jugador O");
            } else {
                clickedButton.setImageResource(R.drawable.boton_2);
                clickedButton.setTag(R.drawable.boton_2);
                textViewTurn.setText("Turno de Jugador X");
            }

            // Switch turns
            playerXTurn = !playerXTurn;
            checkForWinner();
        }
    }


    private void checkForWinner()
    {
        String[][] board = new String[3][3];
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                Object tag = buttons[i][j].getTag();
                if (tag != null)
                {
                    board[i][j] = tag.toString();
                }
                else
                {
                    board[i][j] = "";
                }
            }
        }

        for (int i=0; i<3; i++)
        {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty())
            {
                declareWinner(board[i][0]);
                return;
            }
        }

        for (int j=0; j<3; j++)
        {
            if (board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j]) && !board[0][j].isEmpty())
            {
                declareWinner(board[0][j]);
                return;
            }
        }

        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty())
        {
            declareWinner(board[0][0]);
            return;
        }

        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty())
        {
            declareWinner(board[0][2]);
        }

        boolean isDraw = true;
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                if (board[i][j].isEmpty())
                {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) break;
        }
        if (isDraw)
        {
            textViewTurn.setText("Empate");
        }
    }


    private void declareWinner(String winner)
    {
        int winnerImageId = Integer.parseInt(winner);
        int imageViewId = getResources().getIdentifier("winnerImage", "id", getPackageName());
        ImageView winnerImage = findViewById(imageViewId);
        winnerImage.setImageResource(winnerImageId);
        textViewTurn.setText("¡El jugador ha ganado!");
        disableAllButtons();
    }

    private void disableAllButtons()
    {
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    public void resetGame(View view)
    {
        // Reset the game board
        playerXTurn = true;
        textViewTurn.setText("INICIO DEL JUEGO");
        int imageViewId = getResources().getIdentifier("winnerImage", "id", getPackageName());
        ImageView winnerImage = findViewById(imageViewId);
        winnerImage.setImageResource(0);
        // Clear button texts
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                buttons[i][j].setImageResource(0);
                buttons[i][j].setTag(null);
                buttons[i][j].setEnabled(true);
            }
        }
    }
}
