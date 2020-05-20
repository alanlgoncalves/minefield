package dev.alansantos.minefield.view.game

import dev.alansantos.minefield.model.GameBoard
import dev.alansantos.minefield.model.enums.GameBoardEvent
import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

class MinefieldWindow() : JFrame() {

    private val OS_NAME = System.getProperty("os.name")

    private val GAME_NAME = "Minefield"

    private val gameBoard = GameBoard(rowsNumber = 16, columnsNumber = 30, minesNumber = 89)
    private val gameBoardPanel = GameBoardPanel(gameBoard)

    private val TROPHY_ICON = ImageIcon(ImageIO.read(javaClass.getResource("/icons/trophy_1f3c6.png"))
        .getScaledInstance(64, 64, Image.SCALE_SMOOTH))
    private val BOMB_ICON = ImageIcon(ImageIO.read(javaClass.getResource("/icons/bomb_1f4a3.png"))
        .getScaledInstance(64, 64, Image.SCALE_SMOOTH))

    init {
        gameBoard.onEvent(this::showResult)
        add(gameBoardPanel)

        setSize(690, 438)
        setLocationRelativeTo(null)

        defaultCloseOperation = EXIT_ON_CLOSE
        title = GAME_NAME
        iconImage = ImageIO.read(javaClass.getResource("/icons/bomb_1f4a3.png"))
        isVisible = true;
    }

    fun showResult(gameBoardEvent: GameBoardEvent) {
        SwingUtilities.invokeLater {
            val message = when(gameBoardEvent){
                GameBoardEvent.WIN -> "You Win!"
                GameBoardEvent.LOSE -> "You Loose!"
            }

            val icon = when(gameBoardEvent){
                GameBoardEvent.WIN -> TROPHY_ICON
                GameBoardEvent.LOSE -> BOMB_ICON
            }

            gameBoard.showField()

            JOptionPane.showMessageDialog(this, message, GAME_NAME, JOptionPane.ERROR_MESSAGE, icon)

            gameBoard.restart()
            gameBoardPanel.repaint()
            gameBoardPanel.validate()
        }
    }

}
