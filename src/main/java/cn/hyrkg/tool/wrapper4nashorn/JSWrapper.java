package cn.hyrkg.tool.wrapper4nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper of JavaScript, usually represents a .js file
 */
public class JSWrapper {
    protected final NashornScriptEngine engine;

    protected Map<String, ScriptObjectMirror> functions = new HashMap<>();
    protected Map<String, Object> variables = new HashMap<>();

    public JSWrapper(NashornScriptEngine engineIn) {
        engine = engineIn;
        initializeWrapper();
    }

    /*
     * Initializes and reads all methods and variables
     * */
    protected void initializeWrapper() {

    }

    public ScriptObjectMirror getEngineBindings() {
        return (ScriptObjectMirror) engine.getBindings(ScriptContext.ENGINE_SCOPE);
    }

    public NashornScriptEngine getEngine() {
        return engine;
    }
}
