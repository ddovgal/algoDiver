package ua.ddovgal.algoDiver.scheduler

import ua.ddovgal.algoDiver.architecture.System
import ua.ddovgal.algoDiver.graph.InputGraph
import ua.ddovgal.algoDiver.graph.InputNode
import java.util.*

object SimpleScheduler : Scheduler {
    override fun placeTasks(system: System, inputGraph: InputGraph) {
        val processorsNumber = system.processors.count()
        var processorIdCounter = 0

        val sourceNodes: MutableList<InputNode> = ArrayList(inputGraph.nodes)

        while (sourceNodes.isNotEmpty()) {

            val justImmersedNodes = sourceNodes.filter { system.canBeImmerse(it) }.map {
                system.addWorkForProcessor(it, processorIdCounter)
                processorIdCounter++
                if (processorIdCounter == processorsNumber) processorIdCounter = 0
                it
            }

            sourceNodes.removeAll(justImmersedNodes)
        }
    }
}