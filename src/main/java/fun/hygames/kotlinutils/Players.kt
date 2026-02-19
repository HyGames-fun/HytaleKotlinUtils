package `fun`.hygames.kotlinutils

import com.hypixel.hytale.server.core.NameMatching
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.Universe
import com.hypixel.hytale.server.core.universe.world.World
import `fun`.hygames.kotlinutils.codeInitialization.Run
import `fun`.hygames.kotlinutils.codeInitialization.RunOn
import java.util.*

object Players {
    operator fun get(name : String) : PlayerRef? {
        return Universe.get().getPlayer(name, NameMatching.EXACT_IGNORE_CASE)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline operator fun get(uuid: UUID) : PlayerRef? {
        return Universe.get().getPlayer(uuid)
    }

    val count : Int
        inline get() = Universe.get().playerCount
}

val PlayerRef.world : World?
    get() {
        if (this.worldUuid == null) return null

        return Universe.get().getWorld(this.worldUuid!!)
    }

inline operator fun PlayerRef.invoke(crossinline body : (World) -> Unit){
    val world = this.world ?: return

    world.execute {body.invoke(world)}
}

inline operator fun PlayerRef.invoke(crossinline body : (World) -> Unit, worldIsNull : () -> Unit){
    val world = this.world

    if (world == null){
        worldIsNull.invoke()
        return
    }

    world.execute { body.invoke(world) }
}