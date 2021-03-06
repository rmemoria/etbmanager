package org.msh.etbm.test.commons.forms;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.Test;
import org.msh.etbm.commons.forms.data.Form;
import org.msh.etbm.commons.forms.impl.JavaScriptFormGenerator;
import org.msh.etbm.commons.forms.impl.JsonFormParser;
import org.msh.etbm.test.AuthenticatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import static org.junit.Assert.assertNotNull;

/**
 * Test the generation of java script code to be sent to the client side
 *
 * Created by rmemoria on 26/7/16.
 */
public class JavaScriptFormGenTest extends AuthenticatedTest {

    @Autowired
    JavaScriptFormGenerator javaScriptFormGenerator;

    @Test
    public void test() throws Exception {
        ClassPathResource resource = new ClassPathResource("/test/forms/parse-test.json");
        JsonFormParser p = new JsonFormParser();
        Form frm = p.parse(resource.getInputStream());

        String script = javaScriptFormGenerator.generate(frm, "newSchema");

        System.out.println(script);

        ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");

        // compile the script to test if it was generated correctly
        engine.eval(script);

        Invocable invokable = (Invocable)engine;
        ScriptObjectMirror res = (ScriptObjectMirror)invokable.invokeFunction("newSchema");
        assertNotNull(res.getMember("defaultProperties"));
        assertNotNull(res.getMember("controls"));
    }
}
