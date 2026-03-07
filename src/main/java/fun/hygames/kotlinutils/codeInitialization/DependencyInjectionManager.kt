package `fun`.hygames.kotlinutils.codeInitialization

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.universe.PlayerRef
import `fun`.hygames.kotlinutils.Players
import `fun`.hygames.kotlinutils.Scheduler
import java.lang.reflect.Parameter
import kotlin.reflect.KClass

object DependencyInjectionManager {
    val dependencyInjectionByParameterClass = HashMap<Class<*>, ParameterDI>()
    val extraDependencyInjectionByInjector = HashMap<String, HashMap<Class<*>, ParameterDI>>()

    init {
        register(Scheduler::class) { node, parameter, args ->
            val pluginData = node.pluginData
            if (pluginData.scheduler == null)
                pluginData.scheduler = Scheduler(node.plugin!!)
            return@register pluginData.scheduler
        }

        register(JavaPlugin::class) { node, parameter, args ->
            return@register node.plugin
        }

        register(PlayerRef::class) { node, parameter, args ->
            if (args.contains("_player")) return@register args["_player"]
        }

        register(HytaleLogger::class) { node, parameter, args ->
            return@register node.plugin!!.logger
        }

        register("byName", PlayerRef::class) { node, parameter, args ->
            val name = parameter.getAnnotation(Inject::class.java).name

            val username = args[name] as String
            return@register Players[username]
        }

        register(Int::class) { node, parameter, args ->
            val name = parameter.getAnnotation(Inject::class.java).name

            return@register args[name] as Int
        }

        register(Float::class) { node, parameter, args ->
            val name = parameter.getAnnotation(Inject::class.java).name

            return@register args[name] as Float
        }

        register(Double::class) { node, parameter, args ->
            val name = parameter.getAnnotation(Inject::class.java).name

            return@register args[name] as Double
        }

        register(String::class) { node, parameter, args ->
            val name = parameter.getAnnotation(Inject::class.java).name

            return@register args[name] as String
        }
    }

    fun register(clazz: KClass<*>, parameterDI: ParameterDI){
        dependencyInjectionByParameterClass[clazz.java] = parameterDI
    }

    fun register(injector : String, clazz: KClass<*>, parameterDI: ParameterDI){
        val map = extraDependencyInjectionByInjector[injector]
        if (map == null) extraDependencyInjectionByInjector[injector] = HashMap()

        extraDependencyInjectionByInjector[injector]!![clazz.java] = parameterDI
    }
}


fun interface ParameterDI{
    fun inject(runNode: RunNode, parameter: Parameter, arguments: Map<String, Any>) : Any?
}

annotation class Inject(val injector: String = "", val name : String = "") {

}