import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import videoEditorActorSystem.actors.VideoEditorMsg
import videoEditorActorSystem.actors.videoEditorActor
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.*

fun main() {
    // Swing components are processed one at a time
    // in a special one-off thread.
    SwingUtilities.invokeLater { createAndShowGUI() }
}

fun createAndShowGUI() {
    val scope = CoroutineScope(Dispatchers.Default)
    val videoEditorActor = scope.videoEditorActor()

    val frame = JFrame("Easy Kotlin Video Editor").apply {
        // Set general JFrame Configuration
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        // Create components to add to JFrame, and wire them into the actor system.
        val locationLabel = JLabel("Location: 0")
        val playPauseButton =
            PlayPauseButton(Font("Arial", Font.BOLD, 23 ))
            .apply {
            addActionListener {
                scope.launch {
                    videoEditorActor.send(VideoEditorMsg.TogglePlayPause)
                }
            }
        }

        // Create and configure the timeline panel
        // Assuming TimelinePanel is a class you have defined
        val timelinePanel = TimelinePanel(locationLabel) // Replace with actual implementation

        // Create and configure the scroll pane
        val scrollPane = JScrollPane(timelinePanel).apply {
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_NEVER
        }

        val horizontalGap = 20
        val verticalGap = 10
        val topPanel =
            JPanel(FlowLayout(FlowLayout.LEFT, horizontalGap, verticalGap)).
            apply {
            add(locationLabel)
            add(playPauseButton)
        }

        // Add components to JFrame contentPane
        contentPane.add(topPanel, BorderLayout.NORTH)
        contentPane.add(scrollPane, BorderLayout.CENTER)
        setSize(800, 200)
        setLocationRelativeTo(null)
    }

    frame.isVisible = true
}