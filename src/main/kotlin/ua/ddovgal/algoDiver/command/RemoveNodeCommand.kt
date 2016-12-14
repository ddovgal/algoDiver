package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter
import ua.ddovgal.algoDiver.graph.InputGraph

class RemoveNodeCommand : Command {
    @Parameter(names = arrayOf("-id"), required = true, description = "Id of node to delete")
    var id: Int = -1

    override fun InputGraph.graphAction() {
        removeNode(id)
        println("Node with id=$id was removed")
    }
}