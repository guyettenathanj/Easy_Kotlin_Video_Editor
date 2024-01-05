package videoEditorActorSystem.actors

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.actor

sealed class VideoEditorMsg {
    object TogglePlayPause : VideoEditorMsg()
    data class SetPlayheadPosition(val xCoordinate: Int) : VideoEditorMsg()
    class GetPlayheadPosition(val response: CompletableDeferred<Int>) : VideoEditorMsg()
    // Add other message types as needed
}

data class VideoEditorState(var isPlaying: Boolean = false)

// Actor for managing the state
fun CoroutineScope.videoEditorActor() = actor<VideoEditorMsg> {
    var state = VideoEditorState()
    var playheadPosition = 0 // Initialize the playhead position

    for (msg in channel) {
        when (msg) {
            is VideoEditorMsg.TogglePlayPause -> {
                state.isPlaying = !state.isPlaying
                println("isPlaying state is now ${state.isPlaying}")
            }
            is VideoEditorMsg.SetPlayheadPosition -> {
                playheadPosition = msg.xCoordinate
                println("Playhead position set to ${playheadPosition}")
            }
            is VideoEditorMsg.GetPlayheadPosition -> {
                msg.response.complete(playheadPosition)
            }
            // Handle other messages
        }
    }
}