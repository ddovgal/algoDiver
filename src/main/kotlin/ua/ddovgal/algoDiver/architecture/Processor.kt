package ua.ddovgal.algoDiver.architecture

import ua.ddovgal.algoDiver.graph.InputNode

class Processor(val id: Int) {

    lateinit var nextProcessor: Processor

    val receiveTimeLine = TimeLine<Pair<InputNode, Int>>()
    val performTimeLine = TimeLine<InputNode>()
    val sendTimeLine = TimeLine<Pair<InputNode, Int>>()

    fun addWork(node: InputNode) {

    }

    fun sendToNext(sendTime: Int) {

    }
}