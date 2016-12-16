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
     * @throws RuntimeException
     */
    fun addWorkForProcessor(work: InputNode, processorId: Int) {
        if (canBeImmerse(work)) {
            val processor = processors[processorId]
            processor.addWork(work)
            submergedNodes[work] = processorId
        } else throw RuntimeException()
    }

    private fun canBeImmerse(work: InputNode) = work.inputLinks.map { it.first }.all { submergedNodes.contains(it) }

    fun initTransfer(resultWorkTransfer: InputNode, to: Processor, transferTime: Int, initializer: InputNode): Int {
        val startPointProcessor = processors[submergedNodes[resultWorkTransfer] ?: throw RuntimeException()]
        return startPointProcessor.initTransfer(resultWorkTransfer, to, transferTime, initializer)
    }
}