package `fun`.hygames.kotlinutils

import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.core.universe.world.World
import java.util.*

object Worlds {
    operator fun get(name : String) : World? {
        return Universe.get().getWorld(name)
    }

    operator fun get(uuid: UUID) : World? {
        return Universe.get().getWorld(uuid)
    }
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun World.invoke(command : Runnable){
    this.execute(command)
}