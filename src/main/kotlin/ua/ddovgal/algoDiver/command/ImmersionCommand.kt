package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter
import ua.ddovgal.algoDiver.architecture.System
import ua.ddovgal.algoDiver.gantt.GanttFrame
import ua.ddovgal.algoDiver.graph.InputGraph
import ua.ddovgal.algoDiver.scheduler.ProgressiveScheduler
import ua.ddovgal.algoDiver.scheduler.SimpleScheduler

class ImmersionCommand() : Command {
    @Parameter(names = arrayOf("-p", "--processors"), required = true, description = "Number of processors in system")
    var processorsCount: Int = -1

    @Parameter(names = arrayOf("-prg", "--progressive"), description = "Is progressive method of immersion ?")
    var isProgressive: Boolean = false

    override fun InputGraph.graphAction() {
        val system = System(processorsCount)
        (if (!isProgressive) SimpleScheduler else ProgressiveScheduler).placeTasks(system, this)
        GanttFrame(system) //by creating GanttFrame, it shows diagram
    }
}


