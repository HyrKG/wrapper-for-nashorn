package cn.hyrkg.tool.wrapper4nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.List;

public class JSWrapperParser {

    public final String ENGINE_NAME = "JavaScript";
    public final ScriptEngineManager manager;

    public JSWrapperParser() {
        manager = new ScriptEngineManager();
    }

    public JSFileWrapper parser(List<String> strings) throws ScriptException {
        return parser(String.join(";", strings));
    }

    public JSFileWrapper parser(String string) throws ScriptException {
        ScriptEngine engine = manager.getEngineByName(ENGINE_NAME);
        engine.eval(string);
        return new JSFileWrapper((NashornScriptEngine) engine);
    }

    public JSFileWrapper parser(File f, String charsetName) throws ScriptException, IOException {
        ScriptEngine engine = manager.getEngineByName(ENGINE_NAME);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(f), charsetName);
        engine.eval(isr);
        return new JSFileWrapper((NashornScriptEngine) engine);
    }
}
