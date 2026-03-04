package `fun`.hygames.kotlinutils

import com.hypixel.hytale.logger.HytaleLogger

operator fun HytaleLogger.invoke(message: String){
    this.atInfo().log(message)
}

operator fun HytaleLogger.Api.invoke(message: String){
    this.log(message)
}
