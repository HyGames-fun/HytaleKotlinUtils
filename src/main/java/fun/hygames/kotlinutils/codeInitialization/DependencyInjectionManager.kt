package `fun`.hygames.kotlinutils.codeInitialization

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import `fun`.hygames.kotlinutils.Scheduler
import java.lang.reflect.Parameter
import kotlin.reflect.KClass

object DependencyInjectionManager {
    val dependencyInjectionByParameterClass = HashMap<Class<*>, ParameterDI>()

    init {
        add(Scheduler::class) { node, parameter ->
            val pluginData = node.pluginData
            if (pluginData.scheduler == null)
                pluginData.scheduler = Scheduler(node.plugin!!)
            return@add pluginData.scheduler
        }

        add(JavaPlugin::class) { node, parameter ->
            return@add node.plugin
        }
    }

    private fun add(clazz: KClass<*>, parameterDI: ParameterDI){
        dependencyInjectionByParameterClass[clazz.java] = parameterDI
    }
}


fun interface ParameterDI{
    fun inject(runNode: RunNode, parameter: Parameter) : Any?
}