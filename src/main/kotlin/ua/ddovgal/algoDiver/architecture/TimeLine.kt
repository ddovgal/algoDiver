package ua.ddovgal.algoDiver.architecture

import ua.ddovgal.algoDiver.graph.InputNode

class TimeLine {

    class Interval(val started: Int, val finished: Int, val work: InputNode) {
        val length: Int
            get() = finished - started
    }

    val intervalsOfWork = mutableListOf<Interval>()

    var endOfTime: Int = 0

    /**
     * @throws RuntimeException intended to add work before endOfTime(for performTimeLine)
     */
    fun addWork(started: Int, work: InputNode, unpackTime: (InputNode) -> Int) {
        if (endOfTime > started) throw RuntimeException()
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
    fun smartPlaceTransferWork(from: Int, work: InputNode, time: Int, onlyWatch: Boolean = false): Interval {
        val firstWorkInterval = intervalsOfWork.find { it.finished >= from }
        var startPointToPlaceWork: Int
        val nextIntervalIndex: Int

        if (intervalsOfWork.isEmpty() || firstWorkInterval == null) {
            startPointToPlaceWork = from
            nextIntervalIndex = -1
        } else if (firstWorkInterval.started <= from) {
            val indexOfThisInterval = intervalsOfWork.indexOf(firstWorkInterval)
            startPointToPlaceWork = firstWorkInterval.finished
            nextIntervalIndex = if (intervalsOfWork.last() == firstWorkInterval) -1 else indexOfThisInterval + 1
        } else {
            startPointToPlaceWork = from
            nextIntervalIndex = intervalsOfWork.indexOf(firstWorkInterval)
        }

        val windows = mutableListOf<Interval>()

        val fakeNode = InputNode(-1, -1)
        if (nextIntervalIndex != -1) {
            for (index in nextIntervalIndex..intervalsOfWork.lastIndex) {
                val interval = intervalsOfWork[index]
                windows.add(Interval(startPointToPlaceWork, interval.started, fakeNode))
                startPointToPlaceWork = interval.finished
            }

        }
        windows.add(Interval(startPointToPlaceWork, Int.MAX_VALUE, fakeNode))

        val necessaryWindow = windows.first { it.length >= time }
        val foundInterval = Interval(necessaryWindow.started, necessaryWindow.started + time, work)
        if (!onlyWatch) intervalsOfWork.add(foundInterval)

        return foundInterval
    }
}