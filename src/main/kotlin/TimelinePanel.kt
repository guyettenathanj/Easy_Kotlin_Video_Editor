import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.BorderLayout


class TimelinePanel(private val locationLabel: JLabel) : JPanel() {
    private val timeInterval = 5 // seconds
    private val labelSpacing = 60 // pixels between labels
    private val timelineLength = 1000 // Adjust this for the length of your timeline
    private var currentMouseX = -1 // Initial mouse x-coordinate
    private var playheadVisibility = PlayheadVisibility.DISPLAY_PLAYHEAD

    init {
        layout = BorderLayout()
        preferredSize = Dimension(timelineLength, 100)
        addTimelineDisplayPanel()
    }

    private fun addTimelineDisplayPanel() {
        val timelineDisplayPanel = TimelineDisplayPanel()
        add(timelineDisplayPanel, BorderLayout.CENTER)
    }

    inner class TimelineDisplayPanel : JPanel(), MouseMotionListener, MouseListener {
        init {
            addMouseMotionListener(this)
            addMouseListener(this)
            preferredSize = Dimension(timelineLength, 100) // Adjust the height as needed
        }

        override fun paintComponent(graphics: Graphics) {
            super.paintComponent(graphics)
            val font = Font("Default", Font.BOLD, 16)
            graphics.font = font
            val numberOfLabels = timelineLength / labelSpacing
            for (i in 0..numberOfLabels) {
                val xPosition = i * labelSpacing
                val timeLabel = i * timeInterval
                graphics.drawLine(xPosition, 30, xPosition, 50)
                graphics.drawString("$timeLabel", xPosition - graphics.fontMetrics.stringWidth("$timeLabel") / 2, 25)
            }

            val isMousePositionValid = currentMouseX != -1
            val isInFollowMousePlayheadVisibility = playheadVisibility == PlayheadVisibility.DISPLAY_PLAYHEAD
            if (isMousePositionValid && isInFollowMousePlayheadVisibility) {
                paintPlayheadLine(graphics, currentMouseX, height )
            }
        }

        override fun mouseDragged(e: MouseEvent) {
            if (playheadVisibility == PlayheadVisibility.DISPLAY_PLAYHEAD) {
                updateMousePosition(e)
            }
        }

        override fun mouseMoved(e: MouseEvent) {
        }

        override fun mouseClicked(e: MouseEvent) {
            if (e.button == MouseEvent.BUTTON1) {
                toggleMode()
                updateMousePosition(e)
            }
        }

        override fun mousePressed(e: MouseEvent) {}
        override fun mouseReleased(e: MouseEvent) {}
        override fun mouseEntered(e: MouseEvent) {}
        override fun mouseExited(e: MouseEvent) {}

        private fun updateMousePosition(e: MouseEvent) {
            currentMouseX = e.x
            val locationValue = (currentMouseX.toDouble() / labelSpacing * timeInterval).toInt()
            locationLabel.text = "Location: $locationValue"
            repaint()
        }

        private fun toggleMode() {
            playheadVisibility = if (playheadVisibility == PlayheadVisibility.DISPLAY_PLAYHEAD) PlayheadVisibility.HIDE_PLAYHEAD else PlayheadVisibility.DISPLAY_PLAYHEAD
        }
    }

    private fun paintPlayheadLine(graphics: Graphics, xCoord: Int, height: Int) {
        graphics.color = Color.RED
        graphics.drawLine(xCoord, 0, xCoord, height)
    }
}