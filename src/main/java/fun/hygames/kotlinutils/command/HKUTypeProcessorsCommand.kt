package `fun`.hygames.kotlinutils.command

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import `fun`.hygames.kotlinutils.HytaleKotlinUtils
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.TypeProcessors
import `fun`.hygames.kotlinutils.internal.MessageFoldTree
import `fun`.hygames.kotlinutils.invoke
import `fun`.hygames.kotlinutils.sendMessage

class HKUTypeProcessorsCommand : AbstractPlayerCommand {

    constructor() : super("typeProcessors", "List of all type processors")

    override fun execute(
        context: CommandContext,
        store: Store<EntityStore?>,
        ref: Ref<EntityStore?>,
        player: PlayerRef,
        world: World
    ) {
        val tree = MessageFoldTree("TypeProcessors")

        for (entry in TypeProcessors.registeredByTypeProcessor){
            val typeProcessor = entry.key
            val classes = entry.value
            val plugin = TypeProcessors.getPlugin(typeProcessor)!!.name

            for (clazz in classes) {
                tree.branch("Plugin $plugin", "TypeProcessor \"$typeProcessor\"", "Class ${clazz.simpleName}")
            }
        }

        val result = tree.buildString()

        HytaleKotlinUtils.logger(result)

        player.sendMessage(result)
    }
}