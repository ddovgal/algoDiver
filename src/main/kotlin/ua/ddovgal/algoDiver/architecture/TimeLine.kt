package ua.ddovgal.algoDiver.architecture

import ua.ddovgal.algoDiver.graph.InputNode

class TimeLine {

    class Interval(val started: Int, val finished: Int, val work: InputNode)

    val intervalsOfWork = mutableListOf<Interval>()

    var endOfTime: Int = 0

    fun addWork(started: Int, work: InputNode, unpackTime: (InputNode) -> Int) {
        /*val workTime = unpackTime(work)
        if (!isFreeAtInterval(started, started + workTime)) throw RuntimeException()
        if (endOfTime < started + workTime) endOfTime = started + workTime*/
        if (endOfTime >= started) throw RuntimeException()
        val workTime = unpackTime(work)
        endOfTime = started + workTime

        val newInterval = Interval(started, endOfTime, work)
        intervalsOfWork.add(newInterval)
    }

    fun smartPlaceTransferWork(from: Int, work: InputNode, time: Int): Interval {
        //TODO
        throw UnsupportedOperationException("Not implemented yet")
    }

    fun isFreeAtInterval(from: Int, to: Int): Boolean = intervalsOfWork.filter {
        (it.started <= to && it.finished >= to) || (it.started <= from && it.finished >= to)
    }.isEmpty()
}