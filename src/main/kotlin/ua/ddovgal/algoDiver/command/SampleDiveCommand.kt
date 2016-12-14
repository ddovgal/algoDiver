package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter
import ua.ddovgal.algoDiver.System
import ua.ddovgal.algoDiver.graph.InputGraph

class SampleDiveCommand : Command {
    @Parameter(names = arrayOf("-p", "--processors"), required = true, description = "Number of processors in system")
    var processorsCount: Int = -1

    override fun InputGraph.graphAction() {
        //TODO complete logic
        val system = System(processorsCount)
//        sampleScheduler.placeTasks(system, this)
//        system.showTimeLines()
    }
}


