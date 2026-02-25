package `fun`.hygames.kotlinutils

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.util.concurrent.ThreadUtil
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.DurationUnit

class Scheduler(plugin: JavaPlugin) {

    val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor(ThreadUtil.daemon(plugin.name+"Scheduler"));

    fun schedule(duration: Duration, runnable: Runnable) : ScheduledFuture<*> {
        return executor.schedule(
            runnable,
            duration.toLong(DurationUnit.MILLISECONDS),
            TimeUnit.MILLISECONDS
        )
    }

    /**
     * Schedules a task for repeated execution at a fixed rate.
     *
     * The [duration] is converted to milliseconds, with a minimum effective value of 1ms.
     * Any value less than 1ms will be rounded down to 0, which may cause the task to
     * execute as frequently as possible.
     *
     * @param duration time interval between task executions
     * @param runnable code to be executed periodically
     */
    fun scheduleAtFixedRate(duration: Duration, runnable: Runnable) : ScheduledFuture<*> {
        val time = duration.toLong(DurationUnit.MILLISECONDS)
        return executor.scheduleAtFixedRate(
            runnable,
            time,
            time,
            TimeUnit.MILLISECONDS
        )
    }

    fun stop(){
        executor.shutdownNow()
    }

}