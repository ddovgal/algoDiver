package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter

class RemoveNodeCommand {
    @Parameter(names = arrayOf("-id"), required = true, description = "Id of node to delete")
    var id: Int = -1
}