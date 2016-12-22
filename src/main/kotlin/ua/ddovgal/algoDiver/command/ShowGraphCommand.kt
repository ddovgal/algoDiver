package ua.ddovgal.algoDiver.command

import ua.ddovgal.algoDiver.graph.InputGraph

class ShowGraphCommand : Command {
    override fun InputGraph.graphAction() {
        val sortedNodes = nodes
        sortedNodes.sortBy { it.level }
        sortedNodes.forEach {
            print("(${it.level}) T${it.id}[${it.leadTime}] -> {")
            val sb = StringBuilder()
            it.outputLinks.forEachIndexed { i, pair ->
                sb.append("T").append(pair.first.id).append("=").append(pair.second)
                if (i != it.outputLinks.lastIndex) sb.append(", ")
            }
            println("$sb}")
        }
    }
}
