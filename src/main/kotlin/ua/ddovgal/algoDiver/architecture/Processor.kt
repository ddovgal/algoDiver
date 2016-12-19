package ua.ddovgal.algoDiver.architecture

import ua.ddovgal.algoDiver.architecture.TimeLine.Interval
import ua.ddovgal.algoDiver.graph.InputNode

class Processor(val id: Int, val system: System) {

    lateinit var nextProcessor: Processor

    val receiveTimeLine = TimeLine()
    val performTimeLine = TimeLine()
    val sendTimeLine = TimeLine()

    fun addWork(node: InputNode, onlyWatch: Boolean = false): Int {
        val executionEOT = performTimeLine.endOfTime
        val dataArrivalEOTs = node.inputLinks.map { system.initTransfer(it.first, this, it.second, node, onlyWatch) }
        val maxArrivalTime = dataArrivalEOTs.max() ?: 0

        val chosenStartTime = Math.max(executionEOT, maxArrivalTime)
        if (!onlyWatch) performTimeLine.addWork(chosenStartTime, node, { it.leadTime })
        return chosenStartTime
    }

    /**
     * @throws RuntimeException if work/node, that need to be transferred, hadn't been executed at this processor and isn't in cache
     */
    fun initTransfer(resultWorkTransfer: InputNode, to: Processor, transferTime: Int, initializer: InputNode, onlyWatch: Boolean = false): Int {
        //first, check, if necessary work had been transferred to next processor before
        //it means, that next processor already have it in cache
        //so just need to init transfer from him
        val transferToNextInterval = sendTimeLine.intervalsOfWork.find { it.work == resultWorkTransfer }

        if (transferToNextInterval != null && to != this) {
            val endOfSending = nextProcessor.initTransfer(resultWorkTransfer, to, transferTime, initializer, onlyWatch)
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

            val foundSendingInterval: Interval
            if (!onlyWatch) {
                val start = Math.max(sendTimeLine.endOfTime, resultAvailablePoint)
                foundSendingInterval = Interval(start, start + transferTime, resultWorkTransfer)
                sendTimeLine.intervalsOfWork.add(foundSendingInterval)
                sendTimeLine.endOfTime = foundSendingInterval.finished
            } else foundSendingInterval = sendTimeLine.smartPlaceTransferWork(resultAvailablePoint, resultWorkTransfer, transferTime, onlyWatch)

            val interval = Interval(foundSendingInterval.started, foundSendingInterval.finished, foundSendingInterval.work)
            nextProcessor.receiveTimeLine.intervalsOfWork.add(interval)
            endOfSending = nextProcessor.initTransfer(resultWorkTransfer, to, transferTime, initializer, onlyWatch)

            if (onlyWatch) nextProcessor.receiveTimeLine.intervalsOfWork.remove(interval)
        } else endOfSending = resultAvailablePoint

        return endOfSending
    }
}