package js

import kotlinx.coroutines.coroutineScope
import javax.script.Invocable
import javax.script.ScriptEngineManager


internal object JsExecutor {

    private val scriptEngineManager = ScriptEngineManager()
    private val scriptEngine = scriptEngineManager.getEngineByName("nashorn")
    private const val attemptsAmount = 4

    suspend fun executeScript(functionName: String, script: String): String {
        return coroutineScope {
            scriptEngine.eval(script)
            val invocable = scriptEngine as Invocable
            var result: String? = null

            run loop@{
                (1..attemptsAmount).forEach { _ ->
                    (invocable.invokeFunction(functionName) as String?)?.let {
                        result = it
                        return@loop
                    }
                }
            }

            result ?: throw JsFunctionException("js function returned null")
        }
    }
}