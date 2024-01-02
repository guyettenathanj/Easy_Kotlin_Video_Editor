import javax.swing.JButton
import java.awt.Font
import java.awt.event.ActionListener

enum class PlayPauseState {
    PLAY,
    PAUSE
}

class PlayPauseButton(font: Font) : JButton(), ActionListener {
    private var state: PlayPauseState = PlayPauseState.PLAY

    init {
        setFont(font)
        updateButtonAppearance()
        addActionListener(this)
    }

    override fun actionPerformed(e: java.awt.event.ActionEvent?) {
        // Toggle the state
        state = if (state == PlayPauseState.PLAY) PlayPauseState.PAUSE else PlayPauseState.PLAY
        updateButtonAppearance()
    }

    private fun updateButtonAppearance() {
        // Update the text based on the state
        text = when (state) {
            PlayPauseState.PLAY -> ">"
            PlayPauseState.PAUSE -> "||"
        }
    }
}