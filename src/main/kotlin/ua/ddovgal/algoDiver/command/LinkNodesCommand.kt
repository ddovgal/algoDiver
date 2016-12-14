package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter
import ua.ddovgal.algoDiver.graph.InputGraph

class LinkNodesCommand : Command {
    @Parameter(names = arrayOf("-from"), required = true, description = "Id of link come from")
    var fromId: Int = -1

    @Parameter(names = arrayOf("-to"), required = true, description = "Id of link come to")
    var toId: Int = -1

    @Parameter(names = arrayOf("-time"), required = true, description = "Time value of transfer")
    var transferTime: Int = -1

    override fun InputGraph.graphAction() {
        val fromNode = nodes.find { it.id == fromId } ?: return
        val toNode = nodes.find { it.id == toId } ?: return
        link(fromNode, toNode, transferTime)
        println("Linked $fromId to $toId with $transferTime time")
    }
}