package ua.ddovgal.algoDiver.graph

import java.io.Serializable

class InputNode(val id: Int, val leadTime: Int) : Serializable {

    val inputLinks = mutableListOf<Pair<InputNode, Int>>()
    val outputLinks = mutableListOf<Pair<InputNode, Int>>()

    val level: Int
        get() = inputLinks.map { it.first }.maxBy { it.level }?.level?.plus(1) ?: 0
}