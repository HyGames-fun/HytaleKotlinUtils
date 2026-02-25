package `fun`.hygames.kotlinutils.codeInitialization

import com.hypixel.hytale.event.EventPriority

annotation class Event (
    val type : EventType = EventType.GLOBAL,
    val async : Boolean = false,
    val key : String = "",
    val priority: EventPriority = EventPriority.NORMAL
)

enum class EventType {
    KEYED,
    GLOBAL,
    UNHANDLED
}
