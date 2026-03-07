package `fun`.hygames.kotlinutils.command

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection
import `fun`.hygames.kotlinutils.codeInitialization.Register

@Register("command")
class HKUCommand : AbstractCommandCollection {

    constructor() : super("hku", "Command for developers") {
        addSubCommand(HKUInvokeCommand())
        addSubCommand(HKUNodes())
    }

}