package dev.alansantos.minefield.view.game.events

import dev.alansantos.minefield.model.Field
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseClickListener (
    private val field : Field,
    private val onLeftClickListener: (Field) -> Unit,
    private val onRightClickListener: (Field) -> Unit
) : MouseListener {

    override fun mousePressed(e: MouseEvent?) {
        when(e?.button){
            MouseEvent.BUTTON1 -> onLeftClickListener(field)
            MouseEvent.BUTTON3 -> onRightClickListener(field)
        }
    }

    override fun mouseReleased(e: MouseEvent?) {}
    override fun mouseEntered(e: MouseEvent?) {}
    override fun mouseClicked(e: MouseEvent?) {}
    override fun mouseExited(e: MouseEvent?) {}

}