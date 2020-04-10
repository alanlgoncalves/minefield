package dev.alansantos.minefield.view.game

import dev.alansantos.minefield.model.GameBoard
import java.awt.GridLayout
import javax.swing.JPanel

class GameBoardPanel(gameBoard: GameBoard) : JPanel(){

    init {
        layout = GridLayout(gameBoard.rowsNumber, gameBoard.columnsNumber)

        gameBoard.forEachField { field ->
            val button = FieldButton(field)
            add(button)
        }
    }

}