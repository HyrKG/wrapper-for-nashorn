package cn.hyrkg.tool.wrapper4nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class JSWrapperParser {

    public final String ENGINE_NAME = "JavaScript";
    public final ScriptEngineManager manager;

    public JSWrapperParser() {
        manager = new ScriptEngineManager();
    }

    public JSWrapper parser(List<String> strings) throws ScriptException {
        return parser(String.join(";", strings));
    }

    public JSWrapper parser(String string) throws ScriptException {
        ScriptEngine engine = manager.getEngineByName(ENGINE_NAME);
        engine.eval(string);
        return new JSWrapper((NashornScriptEngine) engine);
    }

    public JSWrapper parser(File f) throws ScriptException, FileNotFoundException {
        ScriptEngine engine = manager.getEngineByName(ENGINE_NAME);
        engine.eval(new FileReader(f));
        return new JSWrapper((NashornScriptEngine) engine);
    }
}
