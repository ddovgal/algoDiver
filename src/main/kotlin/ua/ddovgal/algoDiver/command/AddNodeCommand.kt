package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter

class AddNodeCommand {
    @Parameter(names = arrayOf("-time"), required = true, description = "Lead time of task for node")
    var leadTime: Int = -1
}