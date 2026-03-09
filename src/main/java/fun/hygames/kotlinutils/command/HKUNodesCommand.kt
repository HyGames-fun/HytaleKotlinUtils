package `fun`.hygames.kotlinutils.command

import com.hypixel.hytale.codec.builder.StringTreeMap
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import com.sun.source.tree.Tree
import `fun`.hygames.kotlinutils.HytaleKotlinUtils
import `fun`.hygames.kotlinutils.codeInitialization.RunNodeManager
import `fun`.hygames.kotlinutils.codeInitialization.dependecyInjection.Inject
import `fun`.hygames.kotlinutils.internal.MessageFoldTree
import `fun`.hygames.kotlinutils.invoke
import `fun`.hygames.kotlinutils.sendMessage
import `fun`.hygames.kotlinutils.stringIfNotEmpty

class HKUNodesCommand : AbstractPlayerCommand {
    constructor() : super("nodes", "List of all nodes")

    override fun execute(
        context: CommandContext,
        store: Store<EntityStore?>,
        ref: Ref<EntityStore?>,
        player: PlayerRef,
        world: World
    ) {
        val tree = MessageFoldTree("Nodes")

        HytaleKotlinUtils.logger(RunNodeManager.nodes.toString())

        for (entry in RunNodeManager.nodes) {
            val name = entry.key
            val node = entry.value
            if (node.plugin == null || node.method == null) continue

            val plugin = node.plugin.name
            val clazz = node.method.declaringClass.simpleName

            val invokeArguments = ArrayList<String>()

            for (annotations in node.method.parameterAnnotations) {
                for (annotation in annotations) {
                    if (annotation !is Inject) continue
                    if (annotation.name.isBlank()) continue
                    invokeArguments.add(annotation.name)
                }
            }

            val nodeMessage = "$name, Params: ${node.method.parameterCount}${invokeArguments.stringIfNotEmpty { ", Args ${joinToString()}" }}"

            tree.branch(plugin, clazz, nodeMessage)
        }

        val result = tree.buildString()

        HytaleKotlinUtils.logger(result)

        player.sendMessage(result)
    }
}