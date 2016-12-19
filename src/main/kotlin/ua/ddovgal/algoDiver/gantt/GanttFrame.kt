package ua.ddovgal.algoDiver.gantt

import ua.ddovgal.algoDiver.architecture.System
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Rectangle
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JScrollPane


class GanttFrame(system: System) : JFrame("Gantt diagram") {

    init {
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE

        val ganttDiagram = GanttDiagram(system)
        val jScrollPane = JScrollPane(ganttDiagram)

        size = Dimension(Math.min(ganttDiagram.width + 30, 1000), Math.min(ganttDiagram.height + 33, 900))
        contentPane.add(jScrollPane)
        isVisible = true
    }

    private class GanttDiagram(val system: System) : JComponent() {

        private val TIC_HEIGHT = 20
        private val TIC_WIDTH = 30
        private val BORDERS_PADDING = 40
        private val TEXT_HEIGHT = 20

        private val processorsNumber = system.processors.count()
        private val ticsCount = system.latestWorkCompleteTime

        override fun getWidth() = 2 * BORDERS_PADDING + processorsNumber * 3 * TIC_WIDTH
        override fun getHeight() = 2 * BORDERS_PADDING + ticsCount * TIC_HEIGHT + TEXT_HEIGHT * 2
        override fun getPreferredSize() = Dimension(width, height)

        override fun paint(g: Graphics) {
            drawSkeleton(g)
            drawBorderNumbers(g)
            drawWork(g)
            drawWorkTotal(g)
            g.dispose()
        }

        private fun drawSkeleton(g: Graphics) {
            g.drawLine(BORDERS_PADDING, BORDERS_PADDING, BORDERS_PADDING + processorsNumber * 3 * TIC_WIDTH, BORDERS_PADDING)

            for (i in system.processors.indices) {
                val leftLineXPos = BORDERS_PADDING + i * 3 * TIC_WIDTH
                val rightLineXPos = leftLineXPos + 3 * TIC_WIDTH
                g.drawLine(leftLineXPos, BORDERS_PADDING, leftLineXPos, BORDERS_PADDING + ticsCount * TIC_HEIGHT)
                g.drawLine(rightLineXPos, BORDERS_PADDING, rightLineXPos, BORDERS_PADDING + ticsCount * TIC_HEIGHT)
            }
        }

        private fun drawBorderNumbers(g: Graphics) {
            for (i in system.processors.indices) {

                val rect = Rectangle(BORDERS_PADDING + i * 3 * TIC_WIDTH, BORDERS_PADDING - TEXT_HEIGHT, 3 * TIC_WIDTH, TEXT_HEIGHT)
                val text = (i + 1).toString()

                drawStringCentred(g, rect, text)
            }

            IntRange(0, ticsCount - 1).forEach {
                val rect = Rectangle(BORDERS_PADDING - TIC_WIDTH, BORDERS_PADDING + it * TIC_HEIGHT, TIC_WIDTH, TEXT_HEIGHT)
                drawStringCentred(g, rect, (it + 1).toString())
            }
        }

        private fun drawStringCentred(g: Graphics, rect: Rectangle, text: String) {
            // Get the FontMetrics
            val metrics = g.getFontMetrics(font)
            // Determine the X coordinate for the text
            val x = rect.x + (rect.width - metrics.stringWidth(text)) / 2
            // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
            val y = rect.y + (rect.height - metrics.height) / 2 + metrics.ascent
            // Draw the String
            g.drawString(text, x, y)
        }

        private fun drawWork(g: Graphics) {
            for ((i, processor) in system.processors.withIndex()) {
                processor.receiveTimeLine.intervalsOfWork.forEach {
                    g.color = Color.CYAN
                    g.fillRect(BORDERS_PADDING + i * 3 * TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)
                    g.color = Color.BLACK
                    g.drawRect(BORDERS_PADDING + i * 3 * TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)

                    val rect = Rectangle(BORDERS_PADDING + i * 3 * TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)
                    drawStringCentred(g, rect, ">T${it.work.id}")
                }
                processor.performTimeLine.intervalsOfWork.forEach {
                    g.color = Color.GREEN
                    g.fillRect(BORDERS_PADDING + i * 3 * TIC_WIDTH + TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)
                    g.color = Color.BLACK
                    g.drawRect(BORDERS_PADDING + i * 3 * TIC_WIDTH + TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)

                    val rect = Rectangle(BORDERS_PADDING + i * 3 * TIC_WIDTH + TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)
                    drawStringCentred(g, rect, "T${it.work.id}")
                }
                processor.sendTimeLine.intervalsOfWork.forEach {
                    g.color = Color.MAGENTA
                    g.fillRect(BORDERS_PADDING + i * 3 * TIC_WIDTH + 2 * TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)
                    g.color = Color.BLACK
                    g.drawRect(BORDERS_PADDING + i * 3 * TIC_WIDTH + 2 * TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)

                    val rect = Rectangle(BORDERS_PADDING + i * 3 * TIC_WIDTH + 2 * TIC_WIDTH, BORDERS_PADDING + it.started * TIC_HEIGHT, TIC_WIDTH, TIC_HEIGHT * it.length)
                    drawStringCentred(g, rect, "T${it.work.id}>")
                }
            }
        }

        private fun drawWorkTotal(g: Graphics) {
            val rect = Rectangle(BORDERS_PADDING, BORDERS_PADDING + ticsCount * TIC_HEIGHT, TIC_WIDTH * 3 * processorsNumber, TEXT_HEIGHT * 2)
            val text = "Completed at $ticsCount tics"
            g.drawRect(BORDERS_PADDING, BORDERS_PADDING + ticsCount * TIC_HEIGHT, TIC_WIDTH * 3 * processorsNumber, TEXT_HEIGHT * 2)
            drawStringCentred(g, rect, text)
        }
    }
}