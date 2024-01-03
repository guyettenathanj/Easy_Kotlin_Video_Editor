import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.*


fun main() {
    SwingUtilities.invokeLater { createAndShowGUI() }
}

fun createAndShowGUI() {
    val frame = JFrame("Easy Kotlin Video Editor").apply {
        // Set general JFrame Configuration
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        // Create components to add to JFrame
        val locationLabel = JLabel("Location: 0")
        val playPauseButton = PlayPauseButton(Font("Arial", Font.BOLD, 22))
        val timelinePanel = TimelinePanel(locationLabel)
        val scrollPane = JScrollPane(timelinePanel).apply {
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_NEVER
        }
        val horizontalGap = 20
        val verticleGap = 10
        val topPanel =
            JPanel(FlowLayout(FlowLayout.LEFT, horizontalGap, verticleGap))
                .apply {
            add(locationLabel)
            add(playPauseButton)
        }

        // Add components to JFrame contentPane...
        contentPane.add(topPanel, BorderLayout.NORTH)
        // Can't have two "NORTH" Ccomponents...
        contentPane.add(scrollPane, BorderLayout.CENTER)
        setSize(800, 200)
        setLocationRelativeTo(null)
    }

    frame.isVisible = true
}