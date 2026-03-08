package `fun`.hygames.kotlinutils.command

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import `fun`.hygames.kotlinutils.HytaleKotlinUtils
import `fun`.hygames.kotlinutils.codeInitialization.RunNodeManager
import `fun`.hygames.kotlinutils.codeInitialization.dependecyInjection.Inject
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
        val string = StringBuilder()

        val nodes = HashMap<String, HashMap<String, ArrayList<String>>>() // Plugin -> Classes -> Nodes

        for (entry in RunNodeManager.nodes) {
            val node = entry.value
            val name = entry.key
            if (node.plugin == null || node.method == null) continue

            val plugin = node.plugin.name
            val clazz = node.method.declaringClass.simpleName

            val classesToNodes = nodes.computeIfAbsent(plugin) { HashMap() }

            val nodes = classesToNodes.computeIfAbsent(clazz) { ArrayList() }

            val invokeArguments = ArrayList<String>()

            for (annotations in node.method.parameterAnnotations) {
                for (annotation in annotations) {
                    if (annotation !is Inject) continue
                    if (annotation.name.isBlank()) continue
                    invokeArguments.add(annotation.name)
                }
            }

            nodes.add("$name, Params: ${node.method.parameterCount}${invokeArguments.stringIfNotEmpty { ", Args ${joinToString()}}" }}")
        }

        for (plugin in nodes) {
            string.append("г  ${plugin.key}\n")
            val classes = plugin.value
            for (clazz in classes) {
                string.append("|--  ${clazz.key}\n")
                for (node in clazz.value) {
                    string.append("|----  ${node}\n")
                }

            }
        }

        val result = string.toString()

        HytaleKotlinUtils.logger(result)

        player.sendMessage(result)
    }
}