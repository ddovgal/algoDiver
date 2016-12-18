package ua.ddovgal.algoDiver.architecture

import ua.ddovgal.algoDiver.graph.InputNode

class Processor(val id: Int, val system: System) {

    lateinit var nextProcessor: Processor

    val receiveTimeLine = TimeLine()
    val performTimeLine = TimeLine()
    val sendTimeLine = TimeLine()

    fun addWork(node: InputNode) {
        val executionEOT = performTimeLine.endOfTime
        val dataArrivalEOTs = node.inputLinks.map { system.initTransfer(it.first, this, it.second, node) }
        val maxArrivalTime = dataArrivalEOTs.max() ?: 0

        val chosenStartTime = Math.max(executionEOT, maxArrivalTime)
        performTimeLine.addWork(chosenStartTime, node, { it.leadTime })
    }

    /**
     * @throws RuntimeException if work/node, that need to be transferred, hadn't been executed at this processor and isn't in cache
     */
    fun initTransfer(resultWorkTransfer: InputNode, to: Processor, transferTime: Int, initializer: InputNode): Int {
        //first, check, if necessary work had been transferred to next processor before
        //it means, that next processor already have it in cache
        //so just need to init transfer from him
        val transferToNextInterval = sendTimeLine.intervalsOfWork.find { it.work == resultWorkTransfer }

        if (transferToNextInterval != null && to != this) {
            val endOfSending = nextProcessor.initTransfer(resultWorkTransfer, to, transferTime, initializer)
            return endOfSending
        }


        val resultWorkInterval = performTimeLine.intervalsOfWork.find { it.work == resultWorkTransfer }
        val resultCachedInterval = receiveTimeLine.intervalsOfWork.find { it.work == resultWorkTransfer }

        val resultAvailablePoint: Int
        if (resultWorkInterval != null) resultAvailablePoint = resultWorkInterval.finished
        else if (resultCachedInterval != null) resultAvailablePoint = resultCachedInterval.finished
        else throw RuntimeException()

        val endOfSending: Int
        if (to != this) {
            val foundSendingInterval = sendTimeLine.smartPlaceTransferWork(resultAvailablePoint, resultWorkTransfer, transferTime)
            nextProcessor.receiveTimeLine.addWork(foundSendingInterval.started, resultWorkTransfer, { transferTime })
            endOfSending = nextProcessor.initTransfer(resultWorkTransfer, to, transferTime, initializer)
        } else endOfSending = resultAvailablePoint

        return endOfSending
    }
}