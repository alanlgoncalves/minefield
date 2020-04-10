package dev.alansantos.minefield.view.menu

import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.SwingUtilities

class MinefieldMenu() : JFrame() {

    init {
        setSize(300, 300)
        setLocationRelativeTo(null)

        layout = GridLayout(5, 1)

        val easyButton = JButton("Easy")
        val mediumButton = JButton("Medium")
        val hardButton = JButton("Hard")
        val expertButton = JButton("Expert")

        add(easyButton)
        add(mediumButton)
        add(hardButton)
        add(expertButton)

        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Minefield"
        isVisible = true

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

}