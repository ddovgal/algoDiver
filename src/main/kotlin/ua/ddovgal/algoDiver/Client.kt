package ua.ddovgal.algoDiver

import com.beust.jcommander.JCommander
import ua.ddovgal.algoDiver.command.*
import ua.ddovgal.algoDiver.graph.InputGraph
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Client {

    private val inputGraph by lazy {
        try {
            FileInputStream("algorithm.alg").use { ObjectInputStream(it).use { it.readObject() as InputGraph } }
        } catch(e: Exception) {
            InputGraph()
        }
    }

    fun interact() {
        val jc = JCommander()
        jc.addCommand("show", ShowGraphCommand())

        println("Enter commands with parameters")
        while (true) {
            val line = readLine()
            if (line == "exit") {
                FileOutputStream("algorithm.alg").use { ObjectOutputStream(it).use { it.writeObject(inputGraph) } }
                return
            }

            addNewCommands(jc)

            try {
                jc.parse(*line!!.split(" ").toTypedArray())

                val command = jc.commands[jc.parsedCommand]!!.objects[0] as Command
                command.action(inputGraph)
            } catch(e: Exception) {
                println(e.message)
            }
        }
    }

    private fun addNewCommands(jc: JCommander) {
        val addNodeCommand = AddNodeCommand()
        jc.addCommand("add", addNodeCommand)
        val removeNodeCommand = RemoveNodeCommand()
        jc.addCommand("remove", removeNodeCommand)
        val linkNodesCommand = LinkNodesCommand()
        jc.addCommand("link", linkNodesCommand)
        val unlinkNodesCommand = UnlinkNodesCommand()
        jc.addCommand("unlink", unlinkNodesCommand)
        val immersionCommand = ImmersionCommand()
        jc.addCommand("immerse", immersionCommand)
    }
}