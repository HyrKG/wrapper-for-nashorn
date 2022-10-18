package cn.hyrkg.tool.wrapper4nashorn.object;

import cn.hyrkg.tool.wrapper4nashorn.JSFileWrapper;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class JSFunctionWrapped {


    public final JSFileWrapper wrapper;
    public final ScriptObjectMirror objectMirror;


    public JSFunctionWrapped(JSFileWrapper wrapper, ScriptObjectMirror objectMirror) {
        this.wrapper = wrapper;
        this.objectMirror = objectMirror;
    }

    public JSObjectWrapped invoke(Object... args) {
        if (objectMirror == null) {
            return new JSObjectWrapped(wrapper, null, 0);
        }
        return new JSObjectWrapped(wrapper, objectMirror.call(objectMirror, args));
    }
}
