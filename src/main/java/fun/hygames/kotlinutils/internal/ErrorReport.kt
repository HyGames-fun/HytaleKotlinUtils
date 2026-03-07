package `fun`.hygames.kotlinutils.internal

import com.hypixel.hytale.logger.HytaleLogger
import `fun`.hygames.kotlinutils.invoke

object ErrorReport {

    private val errors = ArrayList<String>()
    private var logger: HytaleLogger? = null

    operator fun invoke(errorMessage: String) {
        invoke(errorMessage, 1, 1)
    }

    operator fun invoke(errorMessage: String, stackTraceStart : Int, stackTraceDeep: Int) {
        val stackTrace = Throwable().stackTrace
        val location = ArrayList<String>()

        for (i in 0..<stackTraceDeep){
            val caller = stackTrace[i+stackTraceStart+1] ?: continue
            location.add("${caller.fileName}:${caller.lineNumber}")
        }

        location.reverse()

        val fullMessage = "[${location.joinToString("->")}] $errorMessage"

        errors.add(fullMessage)
        logger!!.atSevere()(fullMessage)
    }

    fun log(){
        if (errors.isEmpty()) return

        logger!!.atSevere()("\nHytaleKotlinUtils was launched with ${errors.size} errors! \n    Errors messages:\n      ${errors.joinToString("\n      ")}")
        errors.clear()
    }

    fun setLogger(logger: HytaleLogger){
        if (this.logger != null) return
        this.logger = logger
    }
}