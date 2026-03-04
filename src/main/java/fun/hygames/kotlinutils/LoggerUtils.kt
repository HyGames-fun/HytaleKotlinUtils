package `fun`.hygames.kotlinutils

import com.hypixel.hytale.logger.HytaleLogger
import java.util.HashSet

operator fun HytaleLogger.invoke(message: String){
    this.atInfo().log(message)
}

operator fun HytaleLogger.Api.invoke(message: String){
    this.log(message)
}
