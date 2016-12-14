package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter
import ua.ddovgal.algoDiver.graph.InputGraph

class UnlinkNodesCommand : Command {
    @Parameter(names = arrayOf("-f", "--from"), required = true, description = "Id of link come from")
    var fromId: Int = -1

    @Parameter(names = arrayOf("-to"), required = true, description = "Id of link come to")
    var toId: Int = -1

    override fun InputGraph.graphAction() {
        val fromNode = nodes.find { it.id == fromId } ?: return
        val toNode = nodes.find { it.id == toId } ?: return
        unlink(fromNode, toNode)
        println("Unlinked $fromId to $toId")
    }
}