package cn.hyrkg.tool.wrapper4nashorn.test;

import cn.hyrkg.tool.wrapper4nashorn.JSFileWrapper;
import cn.hyrkg.tool.wrapper4nashorn.JSWrapperParser;
import cn.hyrkg.tool.wrapper4nashorn.object.JSFunctionWrapped;
import cn.hyrkg.tool.wrapper4nashorn.object.JSObjectWrapped;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

public class GlobalVariableTest {
    private static File file;

    public static void main(String[] args) {
        try {

            file = new File("src/test/resources/global_variable_test.js");

            System.out.println(String.format("fond %s and exists %s", file.getAbsolutePath(), file.exists()));

            testScope();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testScope() throws ScriptException, IOException {
        JSWrapperParser parser = new JSWrapperParser();
        JSFileWrapper wrapped = parser.parser(file, "UTF-8");

        JSObjectWrapped engineBinding = wrapped.getEngineBindings();
        JSFunctionWrapped functionWrapped = engineBinding.getFunction("test_scope");


        functionWrapped.objectMirror.put("raw_scope_value","adwb");
        functionWrapped.invoke();

    }
}
