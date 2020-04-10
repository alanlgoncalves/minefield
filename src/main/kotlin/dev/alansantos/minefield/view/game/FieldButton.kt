package dev.alansantos.minefield.view.game

import dev.alansantos.minefield.model.Field
import dev.alansantos.minefield.model.enums.FieldEvent
import dev.alansantos.minefield.view.game.events.MouseClickListener
import java.awt.Color
import java.awt.Font
import javax.imageio.ImageIO
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.SwingUtilities


private val RGB_COLOR_NORMAL = Color(184, 184, 184)
private val RGB_COLOR_EXPLOSION = Color(189, 66, 68)
private val RGB_COLOR_TEXT_GREEN = Color(0, 100, 0)

class FieldButton(private val field : Field) : JButton() {

    val FLAG_ICON = ImageIcon(ImageIO.read(javaClass.getResource("/icons/triangular-flag-on-post_1f6a9.png"))
        .getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH))

    val BOMB_ICON = ImageIcon(ImageIO.read(javaClass.getResource("/icons/bomb_1f4a3.png"))
        .getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH))

    init {
        font = font.deriveFont(Font.BOLD)
        background = RGB_COLOR_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(
            MouseClickListener(
                field,
                { it.open() },
                { it.changeMark() })
        )

        field.onEvent(this::applyStyle)

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun applyStyle(fieldEvent: FieldEvent){
        when(fieldEvent){
            FieldEvent.EXPOSE -> applyExplosionStyle()
            FieldEvent.OPEN -> applyOpenStyle()
            FieldEvent.MARK -> applyMarkStyle()
            FieldEvent.SHOW -> showStyle()
            else -> applyDefaultStyle()
        }

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun showStyle() {
        if(!field.safe){
            applyExplosionStyle()
        }else{
            applyOpenStyle()
        }
    }

    private fun applyDefaultStyle() {
        background = RGB_COLOR_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
        icon = null
    }

    private fun applyExplosionStyle() {
        background = RGB_COLOR_EXPLOSION
        icon = BOMB_ICON
    }

    private fun applyOpenStyle() {
        background = RGB_COLOR_NORMAL
        border = BorderFactory.createLineBorder(Color.GRAY)
        icon = null

        foreground = when(field.minedNeighborsQuantity){
            1 -> RGB_COLOR_TEXT_GREEN
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4, 5, 6 -> Color.RED
            else -> Color.PINK
        }

        text = if(field.minedNeighborsQuantity > 0) field.minedNeighborsQuantity.toString() else ""
    }

    private fun applyMarkStyle() {
        icon = FLAG_ICON
    }
}