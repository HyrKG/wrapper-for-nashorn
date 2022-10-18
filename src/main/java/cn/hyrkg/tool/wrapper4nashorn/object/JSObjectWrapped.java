package cn.hyrkg.tool.wrapper4nashorn.object;

import cn.hyrkg.tool.wrapper4nashorn.JSFileWrapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.HashMap;
import java.util.Map;


public class JSObjectWrapped {
    protected final JSFileWrapper wrapper;
    protected final String name;
    protected final Object object;
    protected final boolean isEmpty;

    protected Map<String, JSFunctionWrapped> functions = new HashMap<>();
    protected Map<String, JSObjectWrapped> variables = new HashMap<>();

    protected JsonElement cacheJsonObject = null;


    public JSObjectWrapped(JSFileWrapper wrapper, Object object) {
        this(wrapper, "undefine", object);
    }

    public JSObjectWrapped(JSFileWrapper wrapper, String name, Object object) {
        this.wrapper = wrapper;
        this.name = name;
        this.object = object;
        isEmpty = object == null;

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
                    JSFunctionWrapped wrappedFunction = new JSFunctionWrapped(wrapper, objectMirror);
                    functions.put(entry.getKey(), wrappedFunction);
                } else {
                    //treat as variables yet
                    JSObjectWrapped jsWrappedObject = new JSObjectWrapped(wrapper, entry.getKey(), entry.getValue());
                    variables.put(entry.getKey(), jsWrappedObject);
                }
            }
            JsonObject jsonObject = new JsonObject();
            for (Map.Entry<String, JSObjectWrapped> stringJSWrappedObjectEntry : variables.entrySet()) {
                jsonObject.add(stringJSWrappedObjectEntry.getKey(), stringJSWrappedObjectEntry.getValue().toJson());
            }
            cacheJsonObject = jsonObject;
        }
    }

    protected <T> T get(T defaultValue) {
        if (object == null) {
            return defaultValue;
        } else {
            return (T) object;
        }
    }

    public Map<String, JSFunctionWrapped> getFunctions() {
        return functions;
    }

    public JSFunctionWrapped getFunction(String name) {
        return functions.get(name);
    }

    public Map<String, JSObjectWrapped> getVariables() {
        return variables;
    }

    public JSObjectWrapped getVariable(String name) {
        JSObjectWrapped objectWrapped = variables.get(name);
        if (objectWrapped == null) {
            return new JSObjectWrapped(wrapper, null);
        } else {
            return variables.get(name);
        }
    }

    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    public JsonElement toJson() {
        return cacheJsonObject;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public String getName() {
        return name;
    }

    public JSFileWrapper getWrapper() {
        return wrapper;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
