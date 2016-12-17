package ua.ddovgal.algoDiver.architecture

import ua.ddovgal.algoDiver.graph.InputNode

class TimeLine {

    class Interval(val started: Int, val finished: Int, val work: InputNode)

    val intervalsOfWork = mutableListOf<Interval>()

    var endOfTime: Int = 0

    /**
     * @throws RuntimeException intended to add work before endOfTime(for performTimeLine)
     */
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

    /**
     * Not just find place to placement. It also adds work to this time line(used only with sendTimeLine)
     * WARNING ! You must add work in this method, not just return interval. Because adding this interval to
     * the time line (sendTimeLine) by [addWork] method is not correct(possible [RuntimeException]s)
     */
    fun smartPlaceTransferWork(from: Int, work: InputNode, time: Int): Interval {
        val firstWorkInterval = intervalsOfWork.find { it.finished >= from }
        val startPointToPlaceWork: Int
        val nextIntervalIndex: Int

        if (intervalsOfWork.isEmpty() || firstWorkInterval == null) {
            startPointToPlaceWork = from + 1
            nextIntervalIndex = -1
        } else if (firstWorkInterval.started <= from) {
            val indexOfThisInterval = intervalsOfWork.indexOf(firstWorkInterval)
            startPointToPlaceWork = firstWorkInterval.finished + 1
            nextIntervalIndex = if (intervalsOfWork.last() == firstWorkInterval) -1 else indexOfThisInterval + 1
        } else {
            startPointToPlaceWork = from + 1
            nextIntervalIndex = intervalsOfWork.indexOf(firstWorkInterval)
        }

        val windows = mutableListOf<Interval>()

        //region Fix and check
        val fakeNode = InputNode(-1, -1)
        if (nextIntervalIndex != -1) {
            for (index in nextIntervalIndex..intervalsOfWork.lastIndex) {
                val interval = intervalsOfWork[index]
                windows.add(Interval(startPointToPlaceWork, interval.finished - 1, fakeNode))
            }
        }

        throw UnsupportedOperationException("not implemented")
        //endregion
    }

    fun isFreeAtInterval(from: Int, to: Int): Boolean = intervalsOfWork.filter {
        (it.started <= to && it.finished >= to) || (it.started <= from && it.finished >= to)
    }.isEmpty()
}