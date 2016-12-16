package ua.ddovgal.algoDiver.architecture

import ua.ddovgal.algoDiver.graph.InputNode

class Processor(val id: Int, val system: System) {

    lateinit var nextProcessor: Processor

    val receiveTimeLine = TimeLine()
    val performTimeLine = TimeLine()
    val sendTimeLine = TimeLine()

    fun addWork(node: InputNode) {
        val executionEOT = performTimeLine.endOfTime + 1
        val dataArrivalEOTs = node.inputLinks.map { system.initTransfer(it.first, this, it.second, node) }
        val maxArrivalTime = dataArrivalEOTs.max()?.plus(1) ?: throw RuntimeException()

        val chosenStartTime = Math.max(executionEOT, maxArrivalTime)
        performTimeLine.addWork(chosenStartTime, node, { it.leadTime })
    }

    //TODO делал полусонный - проверь метод и таймлайн и initTransfer в System
    fun initTransfer(resultWorkTransfer: InputNode, to: Processor, transferTime: Int, initializer: InputNode): Int {
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