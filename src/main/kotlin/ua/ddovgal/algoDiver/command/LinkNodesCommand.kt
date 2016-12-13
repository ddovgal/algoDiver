package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter

class LinkNodesCommand {
    @Parameter(names = arrayOf("-from"), required = true, description = "Id of link come from")
    var fromId: Int = -1

    @Parameter(names = arrayOf("-to"), required = true, description = "Id of link come to")
    var toId: Int = -1

    @Parameter(names = arrayOf("-time"), required = true, description = "Time value of transfer")
    var transferTime: Int = -1
}