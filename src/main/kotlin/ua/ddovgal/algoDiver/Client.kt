package ua.ddovgal.algoDiver

import com.beust.jcommander.JCommander
import ua.ddovgal.algoDiver.command.*
import ua.ddovgal.algoDiver.graph.InputGraph
import ua.ddovgal.algoDiver.scheduler.SampleScheduler

class Client {

    private val inputGraph = InputGraph()
    private val sampleScheduler = SampleScheduler()

    fun interact() {
        val jc = JCommander()
        while (true) {
            val addNodeCommand = AddNodeCommand()
            jc.addCommand("add", addNodeCommand)
            val removeNodeCommand = RemoveNodeCommand()
            jc.addCommand("remove", removeNodeCommand)
            val linkNodesCommand = LinkNodesCommand()
            jc.addCommand("link", linkNodesCommand)
            val unlinkNodesCommand = UnlinkNodesCommand()
            jc.addCommand("ulink", unlinkNodesCommand)
            val sampleDiveCommand = SampleDiveCommand()
            jc.addCommand("sdive", sampleDiveCommand)
//        val progressiveDiveCommand = ProgressiveDiveCommand()
//        jc.addCommand("pdive", progressiveDiveCommand)

            val line = readLine()
            if (line == "exit") return

            try {
                jc.parse(*line!!.split(" ").toTypedArray())
            } catch(e: Exception) {
                println(e.message)
            }

            with(inputGraph) {
                when (jc.parsedCommand) {
                    "add" -> println("Added with id: ${add(addNodeCommand.leadTime)}")
                    "remove" -> removeNode(removeNodeCommand.id)
                    "link" -> {
                        val fromNode = nodes.find { it.id == linkNodesCommand.fromId } ?: return@with
                        val toNode = nodes.find { it.id == linkNodesCommand.toId } ?: return@with
                        link(fromNode, toNode, linkNodesCommand.transferTime)
                    }
                    "ulink" -> {
                        val fromNode = nodes.find { it.id == unlinkNodesCommand.fromId } ?: return@with
                        val toNode = nodes.find { it.id == unlinkNodesCommand.toId } ?: return@with
                        unlink(fromNode, toNode)
                    }
                    "sdive" -> {
                        //TODO
                        val system = System(sampleDiveCommand.processorsCount, inputGraph)
//                        sampleScheduler.placeTasks(system)
//                        system.showTimeLines()
                    }
                    else -> throw NoSuchMethodException()
                }
            }
        }
    }
}