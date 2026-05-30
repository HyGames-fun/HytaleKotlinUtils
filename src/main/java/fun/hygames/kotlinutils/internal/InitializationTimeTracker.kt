package `fun`.hygames.kotlinutils.internal

import `fun`.hygames.kotlinutils.HytaleKotlinUtils
import `fun`.hygames.kotlinutils.invoke

internal object InitializationTimeTracker {
    private val timeMap = HashMap<String, Pair<Long, Long?>>()

    operator fun invoke(timerName: String){
        if (timeMap.contains(timerName)) {
            val pair = timeMap[timerName]!!
            timeMap[timerName] = Pair(pair.first, System.currentTimeMillis())
            return
        }

        timeMap[timerName] = Pair(System.currentTimeMillis(), null)
    }

    fun printResults(){
        val result = StringBuilder()
        result.append("\nInitialization time tracker result\n")
        for (timer in timeMap) {
            if (timer.value.second == null) {
                result.append("  Timer: \"${timer.key}\" not stopped!\n")
                continue
            }
            val ms = timer.value.second!! - timer.value.first

            if (ms > 1000)
                result.append("  Timer: \"${timer.key}\" stopped after ${ms / 1000.toDouble()} seconds\n")
            else
                result.append("  Timer: \"${timer.key}\" stopped after $ms ms\n")
        }

        HytaleKotlinUtils.logger(result.toString())
    }
}