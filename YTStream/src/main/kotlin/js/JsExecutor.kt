package js

import kotlinx.coroutines.coroutineScope
import javax.script.Invocable
import javax.script.ScriptEngineManager


internal object JsExecutor {

    private val scriptEngineManager = ScriptEngineManager()
    private val scriptEngine = scriptEngineManager.getEngineByName("nashorn")

    suspend fun executeScript(functionName: String, script: String): String {
        return coroutineScope {
            scriptEngine.eval(script)
            val invocable = scriptEngine as Invocable
            val result = invocable.invokeFunction(functionName) as String?
                ?: throw JsFunctionException("js decoder function returned null")
            result
        }
    }
}