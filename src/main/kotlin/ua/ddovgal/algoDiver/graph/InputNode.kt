package ua.ddovgal.algoDiver.graph

class InputNode(val id: Int, val leadTime: Int) {

    val inputLinks = mutableListOf<Pair<InputNode, Int>>()
    val outputLinks = mutableListOf<Pair<InputNode, Int>>()

    val level: Int
        get() = inputLinks.map { it.first }.maxBy { it.level }?.level ?: 0
}