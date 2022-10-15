package cn.hyrkg.tool.wrapper4nashorn.object;

import cn.hyrkg.tool.wrapper4nashorn.JSWrapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.HashMap;
import java.util.Map;

public class JSWrappedObject {
    public final JSWrapper wrapper;
    public final String name;
    public final Object object;

    protected Map<String, JSWrappedFunction> functions = new HashMap<>();
    protected Map<String, JSWrappedObject> variables = new HashMap<>();

    protected JsonElement cacheJsonObject = null;


    public JSWrappedObject(JSWrapper wrapper, Object object) {
        this(wrapper, "undefine", object);
    }

    public JSWrappedObject(JSWrapper wrapper, String name, Object object) {
        this.wrapper = wrapper;
        this.name = name;
        this.object = object;

        initializeWrapper();
    }

    /*
     * Initializes by parsers all methods and variables
     * */
    protected void initializeWrapper() {
        if (object == null) {
            cacheJsonObject = new JsonObject();
        } else if (object instanceof Number) {
            cacheJsonObject = new JsonPrimitive((Number) object);
        } else if (object instanceof Boolean) {
            cacheJsonObject = new JsonPrimitive((Boolean) object);
        } else if (object instanceof Character) {
            cacheJsonObject = new JsonPrimitive((Character) object);
        } else if (object instanceof String) {
            cacheJsonObject = new JsonPrimitive((String) object);
        } else if (object instanceof ScriptObjectMirror) {
            ScriptObjectMirror thisObjectMirror = (ScriptObjectMirror) object;
            for (Map.Entry<String, Object> entry : thisObjectMirror.entrySet()) {
                if (entry.getValue() instanceof ScriptObjectMirror && ((ScriptObjectMirror) entry.getValue()).isFunction()) {
                    ScriptObjectMirror objectMirror = (ScriptObjectMirror) entry.getValue();
                    JSWrappedFunction wrappedFunction = new JSWrappedFunction(wrapper, objectMirror);
                    functions.put(entry.getKey(), wrappedFunction);
                } else {
                    //treat as variables yet
                    JSWrappedObject jsWrappedObject = new JSWrappedObject(wrapper, entry.getKey(), entry.getValue());
                    variables.put(entry.getKey(), jsWrappedObject);
                }
            }
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<String, JSWrappedObject> stringJSWrappedObjectEntry : variables.entrySet()) {
                jsonObject.add(stringJSWrappedObjectEntry.getKey(), stringJSWrappedObjectEntry.getValue().toJson());
            }
            cacheJsonObject = jsonObject;
        }
    }

    public Map<String, JSWrappedFunction> getFunctions() {
        return functions;
    }

    public JSWrappedFunction getFunction(String name) {
        return functions.get(name);
    }

    public Map<String, JSWrappedObject> getVariables() {
        return variables;
    }

    public JSWrappedObject getVariable(String name) {
        return variables.get(name);
    }

    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    public JsonElement toJson() {
        return cacheJsonObject;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
