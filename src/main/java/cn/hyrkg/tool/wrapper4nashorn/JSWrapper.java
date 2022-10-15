package cn.hyrkg.tool.wrapper4nashorn;

import cn.hyrkg.tool.wrapper4nashorn.object.JSWrappedObject;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptContext;
import java.util.HashMap;

/**
 * A wrapper of JavaScript, usually represents a .js file
 */
public class JSWrapper {
    protected final NashornScriptEngine engine;
    protected HashMap<Integer, JSWrappedObject> scopeWrappedObjects = new HashMap<>();

    public JSWrapper(NashornScriptEngine engineIn) {
        engine = engineIn;
    }


    public JSWrappedObject getEngineBindings() {
        return getBindings(ScriptContext.ENGINE_SCOPE);
    }

    public JSWrappedObject getBindings(int scope) {
        if (!scopeWrappedObjects.containsKey(scope)) {
            scopeWrappedObjects.put(scope, new JSWrappedObject(this, "scope-" + scope, engine.getBindings(scope)));
            return scopeWrappedObjects.get(scope);
        } else {
            return scopeWrappedObjects.get(scope);
        }

    }

    public NashornScriptEngine getEngine() {
        return engine;
    }
}
