import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JScrollPane
import javax.swing.SwingUtilities

// Sample...

enum class Mode {
    DISPLAY_PLAYHEAD,
    HIDE_PLAYHEAD
}

fun main() {
    SwingUtilities.invokeLater { createAndShowGUI() }
}

fun createAndShowGUI() {
    val frame = JFrame("Timeline Demo").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val locationLabel = JLabel("Location: 0")
        val timelinePanel = TimelinePanel(locationLabel)
        val scrollPane = JScrollPane(timelinePanel).apply {
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_NEVER
        }
        contentPane.add(locationLabel, BorderLayout.NORTH)
        contentPane.add(scrollPane, BorderLayout.CENTER)
        setSize(800, 200)
        setLocationRelativeTo(null)
    }

    frame.isVisible = true
}

