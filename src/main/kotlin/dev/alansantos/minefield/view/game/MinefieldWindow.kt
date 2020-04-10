package dev.alansantos.minefield.view.game

import dev.alansantos.minefield.model.GameBoard
import dev.alansantos.minefield.model.enums.GameBoardEvent
import dev.alansantos.minefield.view.game.GameBoardPanel
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

class MinefieldWindow() : JFrame() {

    private val gameBoard = GameBoard(rowsNumber = 16, columnsNumber = 30, minesNumber = 89)
    private val gameBoardPanel = GameBoardPanel(gameBoard)

    init {
        gameBoard.onEvent(this::showResult)
        add(gameBoardPanel)

        setSize(690, 438)
        setLocationRelativeTo(null)

        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Minefield"
        isVisible = true
    }

    fun showResult(gameBoardEvent: GameBoardEvent) {
        SwingUtilities.invokeLater {
            val message = when(gameBoardEvent){
                GameBoardEvent.WIN -> "You Win!"
                GameBoardEvent.LOSE -> "You Loose!"
            }

            gameBoard.showField()

            JOptionPane.showMessageDialog(this, message)

            gameBoard.restart()
            gameBoardPanel.repaint()
            gameBoardPanel.validate()
        }
    }

}
