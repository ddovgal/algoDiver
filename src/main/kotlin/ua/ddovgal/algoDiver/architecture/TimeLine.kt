package ua.ddovgal.algoDiver.architecture

import java.security.InvalidParameterException

class TimeLine<T> {

    class Interval<out T>(val started: Int, val finished: Int, val work: T)

    val intervalsOfWork = mutableListOf<Interval<T>>()

    var endOfTime: Int = 0

    fun addWork(started: Int, work: T, unpackTime: (T) -> Int) {
        if (started >= endOfTime) throw InvalidParameterException()

        val workTime = unpackTime(work)
        endOfTime = started + workTime

        val newInterval = Interval(started, endOfTime, work)
        intervalsOfWork.add(newInterval)
    }

    fun isFreeAtInterval(from: Int, to: Int): Boolean = intervalsOfWork.filter {
        (it.started <= to && it.finished >= to) || (it.started <= from && it.finished >= to)
    }.isEmpty()
}