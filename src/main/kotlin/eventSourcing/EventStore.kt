package eventSourcing

class EventStore {
    private val events = mutableListOf<Event>()

    fun store(event: Event) {
        events.add(event)
    }
}

open class Event {
}