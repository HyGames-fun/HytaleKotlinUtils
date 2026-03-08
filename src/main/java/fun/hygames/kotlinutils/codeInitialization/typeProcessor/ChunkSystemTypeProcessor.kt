package `fun`.hygames.kotlinutils.codeInitialization.typeProcessor

import com.hypixel.hytale.component.system.ISystem
import com.hypixel.hytale.component.system.System
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore
import `fun`.hygames.kotlinutils.codeInitialization.Register
import `fun`.hygames.kotlinutils.inheritsFrom
import `fun`.hygames.kotlinutils.internal.ErrorReport
import java.lang.reflect.Constructor

class ChunkSystemTypeProcessor : TypeProcessor {
    @Suppress("UNCHECKED_CAST")
    override fun run(register: Register, plugin: JavaPlugin, clazz: Class<*>) {
        if (!clazz.inheritsFrom(System::class.java)) {
            ErrorReport("Tried register system, on no system")
            throw Exception("Tried register system, on no system")
        }

        val constructor: Constructor<*> = clazz.getDeclaredConstructor()
        constructor.setAccessible(true)
        val system: ISystem<ChunkStore?> = constructor.newInstance() as ISystem<ChunkStore?>

        plugin.chunkStoreRegistry.registerSystem(system)
    }
}