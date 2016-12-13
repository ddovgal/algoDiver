package ua.ddovgal.algoDiver.command

import com.beust.jcommander.Parameter

class SampleDiveCommand {
    @Parameter(names = arrayOf("-p", "--processors"), required = true, description = "Number of processors in system")
    var processorsCount: Int = -1
}


