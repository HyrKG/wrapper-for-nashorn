package cn.hyrkg.tool.wrapper4nashorn.object;

import cn.hyrkg.tool.wrapper4nashorn.JSWrapper;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class JSWrappedFunction {
    public final JSWrapper wrapper;
    public final ScriptObjectMirror objectMirror;


    public JSWrappedFunction(JSWrapper wrapper, ScriptObjectMirror objectMirror) {
        this.wrapper = wrapper;
        this.objectMirror = objectMirror;
    }

    public JSWrappedObject invoke(Object... args) {
        return new JSWrappedObject(wrapper, objectMirror.call(objectMirror, args));
    }
}
