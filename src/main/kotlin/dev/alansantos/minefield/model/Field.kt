package dev.alansantos.minefield.model

import dev.alansantos.minefield.model.enums.FieldEvent

data class Field(val row : Int, val column : Int) {

    private val neighbors = ArrayList<Field>()
    private val callbacks = ArrayList<(FieldEvent) -> Unit>()

    private var marked : Boolean = false
    private var opened : Boolean = false
    private var mined : Boolean = false

    val closed : Boolean get() = ! opened
    val safe : Boolean get() = ! mined

    val goalAchieved : Boolean get() = safe && opened || mined && marked
    val minedNeighborsQuantity : Int get() = neighbors.filter { it.mined }.size
    val safeNeighbors : Boolean get() = neighbors.map { it.safe }.reduce { result, safe ->  result && safe}

    fun addNeighbor(neighbor : Field) {
        neighbors.add(neighbor)
    }

    fun onEvent(callback : (FieldEvent) -> Unit){
        callbacks.add(callback)
    }

    fun open(){
        if(closed){
            opened = true

            if(mined){
                callbacks.forEach { it(FieldEvent.EXPOSE) }
            }else{
                callbacks.forEach { it(FieldEvent.OPEN) }

                neighbors.filter { it.closed && it.safe && safeNeighbors }.forEach{ it.open() }
            }
        }
    }

    fun changeMark(){
        if(closed) {
            marked = ! marked

            val event = if(marked) FieldEvent.MARK else FieldEvent.MARK_OFF

            callbacks.forEach { it(event) }
        }
    }

    fun mine(){
        mined = true
    }

    fun show(){
        callbacks.forEach { it(FieldEvent.SHOW) }
    }

    fun restart(){
        opened = false
        mined = false
        marked = false

        callbacks.forEach { it(FieldEvent.RESTART) }
    }
}