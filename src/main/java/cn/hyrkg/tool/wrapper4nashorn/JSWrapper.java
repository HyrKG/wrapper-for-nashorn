package cn.hyrkg.tool.wrapper4nashorn;

import cn.hyrkg.tool.wrapper4nashorn.object.JSWrappedFunction;
import cn.hyrkg.tool.wrapper4nashorn.object.JSWrappedObject;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptContext;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper of JavaScript, usually represents a .js file
 */
public class JSWrapper {
    protected final NashornScriptEngine engine;

    protected Map<String, JSWrappedFunction> functions = new HashMap<>();
    protected Map<String, JSWrappedObject> variables = new HashMap<>();

    public JSWrapper(NashornScriptEngine engineIn) {
        engine = engineIn;
        initializeWrapper();
    }

    /*
     * Initializes by reads all methods and variables
     * */
    protected void initializeWrapper() {
        for (Map.Entry<String, Object> entry : getEngineBindings().entrySet()) {
            if (entry.getValue() instanceof ScriptObjectMirror) {

            } else {
                //treat as variables yet
            }
        }
    }

    public ScriptObjectMirror getEngineBindings() {
        return (ScriptObjectMirror) engine.getBindings(ScriptContext.ENGINE_SCOPE);
    }

    public NashornScriptEngine getEngine() {
        return engine;
    }
}
