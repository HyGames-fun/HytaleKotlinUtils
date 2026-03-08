package `fun`.hygames.kotlinutils.command

import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.command.system.CommandContext
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.world.World
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import `fun`.hygames.kotlinutils.codeInitialization.RunNodeManager

class HKUInvokeCommand : AbstractPlayerCommand {

    private val regex = Regex("\"[^\"]+\"")
    private val nodeArg : RequiredArg<String> = withRequiredArg("node","Name of node to invoke", ArgTypes.STRING)
    private val arguments : OptionalArg<List<String>> = withListOptionalArg("args", "a", ArgTypes.STRING)

    constructor() : super("invoke","Invokes nodes by names"){
        setAllowsExtraArguments(true)
    }

    override fun execute(
        context: CommandContext,
        store: Store<EntityStore?>,
        ref: Ref<EntityStore?>,
        player: PlayerRef,
        world: World
    ) {
        val nodeName = context.get(nodeArg)

        val node = RunNodeManager.nodes[nodeName]

        if (node == null){
            context.sendMessage(Message.raw("Node with name $nodeName don't exist. Check all nodes, using command \"/hku nodes\""))
            return
        }

        val args = HashMap<String, Any>()

        val playerArgs = context.get(arguments)

        for (arg in playerArgs) {
            val split = arg.split("=")
            val value = parse(split[1])

            args[split[0]] = value
        }

        args["_player"] = player

        node.invokeWithInjection(args)
    }

    private fun parse(string: String) : Any {
        if (regex.matches(string)) return string

        if (string == "true") return true
        if (string == "false") return false

        if (string.toIntOrNull() != null) return string.toInt()
        if (string.toLongOrNull() != null) return string.toLong()
        if (string.toFloatOrNull() != null) return string.toFloat()
        if (string.toDoubleOrNull() != null) return string.toDouble()
        return "_null"
    }
}