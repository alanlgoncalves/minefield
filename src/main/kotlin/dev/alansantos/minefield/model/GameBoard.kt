package dev.alansantos.minefield.model

import dev.alansantos.minefield.model.enums.FieldEvent
import dev.alansantos.minefield.model.enums.GameBoardEvent
import java.util.*
import kotlin.collections.ArrayList

data class GameBoard(val rowsNumber : Int, val columnsNumber : Int, val minesNumber : Int) {

    private val fields = ArrayList<ArrayList<Field>>()
    private val callbacks = ArrayList<(GameBoardEvent) -> Unit>()

    private var showResult = true

    init {
        generateFields()
        associateNeighbors()
        sortMines()
    }

    private fun generateFields(){
        for(row in 0 until rowsNumber){
            fields.add(ArrayList())

            for(column in 0 .. columnsNumber){
                val field = Field(row, column)
                field.onEvent(this::verifyWinOrLoseGame)
                fields[row].add(field)
            }
        }
    }

    private fun associateNeighbors(){
        forEachField { associateNeighbors(it) }
    }

    private fun associateNeighbors(field : Field){
        val(row, column) = field
        val rows = arrayOf(row - 1, row, row + 1)
        val columns = arrayOf(column - 1, column, column + 1)

        rows.forEach { r ->
            columns.forEach { c ->
                val actualField = fields.getOrNull(r)?.getOrNull(c)
                actualField
                    ?.takeIf { field != it }
                    ?.let { field.addNeighbor(it) }
            }
        }
    }

    private fun sortMines(){
        val generator = Random()

        var sortRow : Int
        var sortColumn : Int
        var minesNumber = 0

        while (minesNumber < this.minesNumber){
            sortRow = generator.nextInt(rowsNumber)
            sortColumn = generator.nextInt(columnsNumber)

            val sortField = fields[sortRow][sortColumn]
            if(sortField.safe){
                sortField.mine()
                minesNumber++
            }
        }
    }

    private fun goalAchieved() : Boolean{
        var winnerPayer = true

        forEachField { if(!it.goalAchieved) winnerPayer = false}

        return winnerPayer;
    }

    private fun verifyWinOrLoseGame(fieldEvent: FieldEvent){
        if(fieldEvent == FieldEvent.EXPOSE){
            callbacks.forEach { it(GameBoardEvent.LOSE) }
        }else if(goalAchieved()){
            if(showResult) {
                callbacks.forEach { it(GameBoardEvent.WIN) }

                showResult = false
            }
        }
    }

    fun forEachField(callBack : (Field) -> Unit){
        fields.forEach { row -> row.forEach(callBack) }
    }

    fun onEvent(callback : (GameBoardEvent) -> Unit){
        callbacks.add(callback)
    }

    fun showField(){
        forEachField { it.show() }
    }

    fun restart(){
        forEachField { it.restart() }
        sortMines()

        showResult = true
    }
}