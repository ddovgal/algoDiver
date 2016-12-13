package ua.ddovgal.algoDiver

import ua.ddovgal.algoDiver.architecture.Processor
import ua.ddovgal.algoDiver.graph.InputGraph

class System(processorsCount: Int, val inputGraph: InputGraph) {

    val processors = Array(processorsCount, ::Processor)

    init {
        for ((i, processor) in processors.withIndex()) {
            if (i != processors.lastIndex) processor.nextProcessor = processors[i + 1]
            else processor.nextProcessor = processors[0]
        }
    }

}