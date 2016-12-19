package ua.ddovgal.algoDiver.architecture

import ua.ddovgal.algoDiver.graph.InputNode

class System(processorsCount: Int) {

    val processors = Array(processorsCount) { index -> Processor(index, this) }
    val submergedNodes = mutableMapOf<InputNode, Int>()

    init {
        for ((i, processor) in processors.withIndex()) {
            if (i != processors.lastIndex) processor.nextProcessor = processors[i + 1]
            else processor.nextProcessor = processors[0]
        }
    }

    /**
     * @throws RuntimeException if not all input nodes are immersed
     */
    fun addWorkForProcessor(work: InputNode, processorId: Int) {
        if (canBeImmerse(work)) {
            val processor = processors[processorId]
            processor.addWork(work)
            submergedNodes[work] = processorId
        } else throw RuntimeException()
    }

    fun canBeImmerse(work: InputNode) = work.inputLinks.map { it.first }.all { submergedNodes.contains(it) }

    /**
     * @throws RuntimeException if there is no processor, mapped with this [resultWorkTransfer]
     */
    fun initTransfer(resultWorkTransfer: InputNode, to: Processor, transferTime: Int, initializer: InputNode, onlyWatch: Boolean = false): Int {
        val startPointProcessor = processors[submergedNodes[resultWorkTransfer] ?: throw RuntimeException()]
        return startPointProcessor.initTransfer(resultWorkTransfer, to, transferTime, initializer, onlyWatch)
    }

    val latestWorkCompleteTime: Int
        get() = processors.maxBy { it.performTimeLine.endOfTime }?.performTimeLine?.endOfTime ?: 0
}