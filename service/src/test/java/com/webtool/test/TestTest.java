package com.webtool.test;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.junit.Assert.*;

public class TestTest {

    @org.junit.Test
    public void testScript() {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine scriptEngine = factory.getScriptEngine("javascript");
        String fileName = "src/main/resources/task.js";
        String functionName = "createTask";
        try{
            scriptEngine.eval("load('"+fileName+ "');");
            Invocable invocable = (Invocable) scriptEngine;
            invocable.invokeFunction(functionName,"1","2","3","4");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}