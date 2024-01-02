import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.JLabel
import javax.swing.JPanel

class TimelinePanel(private val locationLabel: JLabel) : JPanel(), MouseMotionListener, MouseListener {
    private val timeInterval = 5 // seconds
    private val labelSpacing = 60 // pixels between labels
    private val timelineLength = 1000 // Adjust this for the length of your timeline
    private var currentMouseX = -1 // Initial mouse x-coordinate
    private var mode = Mode.DISPLAY_PLAYHEAD

    init {
        preferredSize = Dimension(timelineLength, 100)
        addMouseMotionListener(this)
        addMouseListener(this)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        // Set a larger font size for the numbers
        val font = Font("Default", Font.BOLD, 16)
        g.font = font

        val numberOfLabels = timelineLength / labelSpacing

        for (i in 0..numberOfLabels) {
            val xPosition = i * labelSpacing
            val timeLabel = i * timeInterval

            // Draw wider tick marks
            g.drawLine(xPosition, 30, xPosition, 50)

            // Draw time label centered above the tick mark
            g.drawString("$timeLabel", xPosition - g.fontMetrics.stringWidth("$timeLabel") / 2, 25)
        }

        // Making state more readable
        val isMousePositionValid = currentMouseX != -1
        val isInFollowMouseMode = mode == Mode.DISPLAY_PLAYHEAD

        // Draw the vertical red line at the current mouse position in DISPLAY_PLAYHEAD mode
        if (isMousePositionValid && isInFollowMouseMode) {
            g.color = Color.RED
            g.drawLine(currentMouseX, 0, currentMouseX, height)
        }
    }

    override fun mouseDragged(e: MouseEvent?) {}

    override fun mouseMoved(e: MouseEvent) {
        if (mode == Mode.DISPLAY_PLAYHEAD) {
            currentMouseX = e.x
            val locationValue = (currentMouseX.toDouble() / labelSpacing * timeInterval).toInt()
            locationLabel.text = "Location: $locationValue"
            repaint()
        }
    }

    override fun mouseClicked(e: MouseEvent?) {
        val isLeftClick = e?.button == MouseEvent.BUTTON1
        if (isLeftClick) {
            mode = if (mode == Mode.DISPLAY_PLAYHEAD) Mode.HIDE_PLAYHEAD else Mode.DISPLAY_PLAYHEAD
            if (mode == Mode.DISPLAY_PLAYHEAD) {
                // Update and repaint immediately when switching back to DISPLAY_PLAYHEAD mode
                val locationValue = (currentMouseX.toDouble() / labelSpacing * timeInterval).toInt()
                locationLabel.text = "Location: $locationValue"
            } else {
                locationLabel.text = "Location: N/A"
            }
            repaint()
        }
    }

    override fun mousePressed(e: MouseEvent?) {}
    override fun mouseReleased(e: MouseEvent?) {}
    override fun mouseEntered(e: MouseEvent?) {}
    override fun mouseExited(e: MouseEvent?) {}
}