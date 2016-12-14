package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter
import ua.ddovgal.algoDiver.graph.InputGraph

class AddNodeCommand : Command {
    @Parameter(names = arrayOf("-t", "--time"), required = true, description = "Lead time of task for node")
    var leadTime: Int = -1

    override fun InputGraph.graphAction() {
        println("Added with id: ${this.add(leadTime)}")
    }
}