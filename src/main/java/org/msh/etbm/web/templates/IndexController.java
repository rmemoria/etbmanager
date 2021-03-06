package org.msh.etbm.web.templates;

import org.msh.etbm.commons.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Controller to expose the main page of the system (the root page)
 * Created by rmemoria on 26/8/15.
 */
@Controller
public class IndexController {

    @Value("${app.languages}")
    private String[] languages;

    @Value("${app.default-language}")
    private String defaultLanguage;

    @Value("${server.context-path:}")
    private String contextPath;

    @Value("${development:false}")
    private boolean development;

    @Autowired
    ResourceLoader resourceLoader;

    private Map<String, Object> langMap;

    /**
     * Fill the page variables and return the name of the template page
     *
     * @param model the injected model
     * @return
     */
    @RequestMapping("/")
    public String welcome(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        Locale locale = LocaleContextHolder.getLocale();
        String lang = locale.toString();
        Map<String, String> scripts = (Map<String, String>) langMap.get(lang);

        model.put("language", lang);
        model.put("development", development);
        model.put("path", contextPath);
        model.put("vendor", lang + "/" + scripts.get("vendor"));
        model.put("app", lang + "/" + scripts.get("app"));

        // avoid page to be included in the browser cache
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return "index";
    }

    /**
     * Create the list of languages supported by the application and its corresponding
     * Java Script file name
     *
     * @throws IOException
     */
    @PostConstruct
    public void createLanguageMap() throws IOException {
        langMap = new HashMap<>();

        for (String lang : languages) {
            Map<String, String> files = getJSAppFiles(lang);

            langMap.put(lang, files);
        }
    }

    /**
     * Return the name of the JavaScript file name used to start up application in the browser
     *
     * @param lang the language
     * @return the java script file to be used in the client side to start-up the application
     * @throws IOException
     */
    private Map<String, String> getJSAppFiles(String lang) throws IOException {
        // create map to return to the client
        Map<String, String> vals = new HashMap<>();

        // if it's under development, return the app.js file generated by webpack in development
        if (development) {
            vals.put("app", "app.js");
            vals.put("vendor", "vendor.js");

            return vals;
        }

        // parse javascript to a map
        Map<String, String> files = readScriptsManifest(lang);

        // return the name used in the main java script
        String appfname = files.get("app.js");
        String vendorfname = files.get("vendor.js");

        // some of the files are not available ?
        if (appfname == null || vendorfname == null) {
            throw new IllegalArgumentException("No name found in manifest.json for key app.js/vendor.js in language " + lang);
        }

        vals.put("app", appfname);
        vals.put("vendor", vendorfname);

        return vals;
    }


    /**
     * Read manifest file from the resources containing information about the generated script file names
     *
     * @param lang the selected language
     * @return list of files to be sent to the browser
     * @throws IOException
     */
    private Map<String, String> readScriptsManifest(String lang) throws IOException {
        // search for the manifest.json generated by webpack in production mode
        String resname = "classpath:static/scripts/" + lang + "/manifest.json";
        Resource res = resourceLoader.getResource(resname);

        // read the input string
        final InputStream in = res.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }
        br.close();

        return JsonParser.parseString(stringBuilder.toString(), HashMap.class);
    }
}
