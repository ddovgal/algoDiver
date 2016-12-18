package ua.ddovgal.algoDiver.scheduler

import ua.ddovgal.algoDiver.architecture.System
import ua.ddovgal.algoDiver.graph.InputGraph

interface Scheduler {
    fun placeTasks(system: System, inputGraph: InputGraph)
}