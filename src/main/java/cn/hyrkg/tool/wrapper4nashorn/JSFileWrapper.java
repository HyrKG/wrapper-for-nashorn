package cn.hyrkg.tool.wrapper4nashorn;

import cn.hyrkg.tool.wrapper4nashorn.object.JSObjectWrapped;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptContext;
import java.util.HashMap;

/**
 * A wrapper of JavaScript, usually represents a .js file
 */
public class JSFileWrapper {
    protected final NashornScriptEngine engine;
    protected HashMap<Integer, JSObjectWrapped> scopeWrappedObjects = new HashMap<>();

    public JSFileWrapper(NashornScriptEngine engineIn) {
        engine = engineIn;
    }


    public JSObjectWrapped getEngineBindings() {
        return getBindings(ScriptContext.ENGINE_SCOPE);
    }

    public JSObjectWrapped getBindings(int scope) {
        if (!scopeWrappedObjects.containsKey(scope)) {
            scopeWrappedObjects.put(scope, new JSObjectWrapped(this, "scope-" + scope, engine.getBindings(scope)));
            return scopeWrappedObjects.get(scope);
        } else {
            return scopeWrappedObjects.get(scope);
        }

    }

    public NashornScriptEngine getEngine() {
        return engine;
    }
}
