package ua.ddovgal.algoDiver.graph

import java.io.Serializable

class InputGraph : Serializable {

    val nodes = mutableListOf<InputNode>()
    private var counter: Int = 1

    fun add(leadTime: Int): Int {
        nodes.add(InputNode(counter++, leadTime))
        return counter - 1
    }

    fun removeNode(id: Int) {
        val nodeToRemove = nodes.find { it.id == id }
        nodeToRemove?.let {
            it.inputLinks.forEach { it.first.outputLinks.remove(it) }
            it.outputLinks.forEach { it.first.inputLinks.remove(it) }
        }
        nodes.remove(nodeToRemove)
    }

    fun link(from: InputNode, to: InputNode, transferTime: Int) {
        from.outputLinks.add(to to transferTime)
        to.inputLinks.add(from to transferTime)
    }

    fun unlink(from: InputNode, to: InputNode) {
        from.outputLinks.removeAll { it.first == to }
        from.inputLinks.removeAll { it.first == from }
    }
}