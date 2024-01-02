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

    override fun paintComponent(graphics: Graphics) {
        super.paintComponent(graphics)

        // Set a larger font size for the numbers
        val font = Font("Default", Font.BOLD, 16)
        graphics.font = font

        val numberOfLabels = timelineLength / labelSpacing

        for (i in 0..numberOfLabels) {
            val xPosition = i * labelSpacing
            val timeLabel = i * timeInterval

            // Draw wider tick marks
            graphics.drawLine(xPosition, 30, xPosition, 50)

            // Draw time label centered above the tick mark
            graphics.drawString("$timeLabel", xPosition - graphics.fontMetrics.stringWidth("$timeLabel") / 2, 25)
        }

        // Making state more readable
        val isMousePositionValid = currentMouseX != -1
        val isInFollowMouseMode = mode == Mode.DISPLAY_PLAYHEAD

        // Track objects need to be rendered here...

        // Draw the vertical red line at the current mouse position in DISPLAY_PLAYHEAD mode
        if (isMousePositionValid && isInFollowMouseMode) {
            graphics.color = Color.RED
            graphics.drawLine(currentMouseX, 0, currentMouseX, height)
        }
    }

    override fun mouseDragged(e: MouseEvent) {
        if (mode == Mode.DISPLAY_PLAYHEAD) {
            updateMouse(e)
            repaint()
        }
    }

    override fun mouseMoved(e: MouseEvent) {
    }

    private fun updateMouse(e: MouseEvent) {
        currentMouseX = e.x
        val locationValue = (currentMouseX.toDouble() / labelSpacing * timeInterval).toInt()
        locationLabel.text = "Location: $locationValue"
    }

    override fun mouseClicked(e: MouseEvent) {
        updateMouse(e)
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