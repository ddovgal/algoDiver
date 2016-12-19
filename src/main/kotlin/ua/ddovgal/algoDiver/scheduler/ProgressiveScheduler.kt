package ua.ddovgal.algoDiver.scheduler

import ua.ddovgal.algoDiver.architecture.System
import ua.ddovgal.algoDiver.graph.InputGraph
import ua.ddovgal.algoDiver.graph.InputNode

object ProgressiveScheduler : Scheduler {
    override fun placeTasks(system: System, inputGraph: InputGraph) {
        val levels = inputGraph.levels

        val selector: (InputNode) -> Int = { it.outputLinks.count() }
        levels.values.forEach { it.sortedByDescending(selector) }

        levels.values.forEach {
            it.forEach {
                var minStartPoint = Int.MAX_VALUE
                var chosenProcessor = -1

                for (i in system.processors.indices) {
                    val startPoint = system.processors[i].addWork(it, true)

                    if (startPoint < minStartPoint) {
                        chosenProcessor = i
                        minStartPoint = startPoint
                        system.submergedNodes[it] = i
                    }
                }

                system.addWorkForProcessor(it, chosenProcessor)
            }
        }
    }
}