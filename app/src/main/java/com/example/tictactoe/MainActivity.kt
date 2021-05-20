package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var buttons: Array<Array<Button>>
    lateinit var textViewPlayer1: TextView
    lateinit var textViewPlayer2: TextView


    var player1Score: Int = 0
    var player2Score: Int = 0
    var isPlayer1Turn: Boolean = true

    var turnCount: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewPlayer1 = findViewById(R.id.p1score)
        textViewPlayer2 = findViewById(R.id.p2score)

        buttons = Array(3){i->
            Array(3){j->
                initButton(i, j)
            }
        }

        val resetBtn: Button = findViewById(R.id.reset_btn)
        resetBtn.setOnClickListener{
            player1Score = 0
            player2Score = 0
            updateScore()
            resetBoard()
        }
    }

    private fun initButton(i: Int,j: Int): Button{
        println("$i,$j")
        val btn:Button = findViewById(resources.getIdentifier("btn_$i$j", "id", packageName))
        btn.setOnClickListener{onBtnClick(btn)}
        return btn
    }

    private fun onBtnClick(btn: Button) {
        //If button is clicked, break
        //Else : Add symbol to btn text
        // If win : Update score and clean board
        // elsif draw : Clean board
        // Else : Change turn and increment turn count
        if (btn.text != "") {
            return
        }
        if (isPlayer1Turn) {
            btn.text = "X"
        } else {
            btn.text = "O"
        }
        turnCount++

        if (hasWinner()){
            if (isPlayer1Turn) win(1) else win(2)
        }
        if (turnCount == 9){
            draw()
        }

        isPlayer1Turn = !isPlayer1Turn
    }

    private fun hasWinner():Boolean{
        val values: Array<Array<String>> = Array(3){i ->
            Array(3){j ->
                buttons[i][j].text.toString()}
        }

        for(i in 0..2){
            // Checking lines
            if(isWinning(values[i][0], values[i][1], values[i][2])) return true
        }

        for(i in 0..2){
            //Checking Columns
            if(isWinning(values[0][i], values[1][i], values[2][i])) return true
        }

        // Checking diagonal
        if(isWinning(values[0][0], values[1][1], values[2][2])) return true

        // Checking anti-diagonal
        if(isWinning(values[0][2], values[1][1], values[0][2])) return true

        return false

    }

    private fun isWinning(e1: String, e2: String, e3: String):Boolean{
        return e1 == e2 && e2 == e3 && e1 != ""
    }
    private fun win(player:Int){
        if (player == 1) player1Score++ else player2Score++
        Toast.makeText(
            applicationContext,
            "GG player $player",
            Toast.LENGTH_SHORT).show()
        updateScore()
        resetBoard()
    }

    private fun draw(){
        Toast.makeText(
            applicationContext,
            "It's a draw!",
            Toast.LENGTH_SHORT).show()
        resetBoard()
    }
    private fun resetBoard(){
        for (btnLine in buttons){
            for (btn in btnLine){
                btn.text = ""
            }
        }
        turnCount = 0
        isPlayer1Turn = true
    }

    private fun updateScore(){
        textViewPlayer1.text = "Player 1 : $player1Score"
        textViewPlayer2.text = "Player2 : $player2Score"
    }
    //TODO() 4: declare initButton() function to initialize and set onClickListener to each button in 3x3 array.
}