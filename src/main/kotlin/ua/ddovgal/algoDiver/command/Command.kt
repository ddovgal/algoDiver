package ua.ddovgal.algoDiver.command

import ua.ddovgal.algoDiver.graph.InputGraph

interface Command {
    fun action(inputGraph: InputGraph) = inputGraph.graphAction()
    fun InputGraph.graphAction()
}