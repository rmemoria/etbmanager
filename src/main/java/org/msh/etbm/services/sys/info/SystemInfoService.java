package org.msh.etbm.services.sys.info;

import org.msh.etbm.commons.Item;
import org.msh.etbm.services.admin.sysconfig.SysConfigFormData;
import org.msh.etbm.services.admin.sysconfig.SysConfigService;
import org.msh.etbm.web.api.sys.GlobalListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Created by rmemoria on 16/8/15.
 */
@Service
public class SystemInfoService {

    /**
     * List of available languages supported by the system
     */
    @Value("${app.languages}")
    private String[] languages;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SysConfigService sysConfigService;

    @Autowired
    GlobalListsService globalListsService;

    /**
     * Store information about the manifest.md file
     */
    private JarManifest jarManifest;


    /**
     * Return information about the system
     *
     * @return
     */
    public SystemInformation getInformation(boolean includeLists) {
        SystemInformation inf = new SystemInformation();

        inf.setState(getState());

        inf.setLanguages(getLanguages());

        inf.setSystem(getJarManifest());

        SysConfigFormData cfg = sysConfigService.loadConfig();
        inf.setUlaActive(cfg.getUlaActive().get());
        inf.setAllowRegPage(cfg.getAllowRegPage().get());

        if (includeLists) {
            inf.setLists(globalListsService.getLists());
        }

        return inf;
    }


    /**
     * Return the state of the system
     *
     * @return SystemState instance
     */
    protected SystemState getState() {
        Number num = (Number) entityManager.createQuery("select count(*) from Workspace").getSingleResult();
        if (num.intValue() == 0) {
            return SystemState.NEW;
        }

        return SystemState.READY;
    }


    /**
     * Return the list of available languages, with its display name
     *
     * @return list of Item objects
     */
    protected List<Item<String>> getLanguages() {
        // create the list of languages
        List<Item<String>> lst = new ArrayList<>();
        for (String lang : languages) {
            Locale loc;
            String[] opts = lang.split("_");
            switch (opts.length) {
                case 2:
                    loc = new Locale(opts[0], opts[1]);
                    break;
                case 3:
                    loc = new Locale(opts[0], opts[1], opts[2]);
                    break;
                default:
                    loc = new Locale(opts[0]);
            }
            lst.add(new Item<String>(lang, loc.getDisplayName(loc)));
        }
        return lst;
    }


    /**
     * Load information from the MANIFEST.MF file in the META-INF folder
     */
    protected JarManifest getJarManifest() {
        if (jarManifest != null) {
            return jarManifest;
        }

        jarManifest = new JarManifest();

        Manifest manifest = getManifest();

        if (manifest != null) {
            Attributes attr = manifest.getMainAttributes();
            jarManifest.setBuildTime(attr.getValue("Build-Time"));
            jarManifest.setImplementationVendor(attr.getValue("Implementation-Vendor"));
            jarManifest.setImplementationVersion(attr.getValue("Implementation-Version"));
            jarManifest.setImplementationTitle(attr.getValue("Implementation-Title"));
            jarManifest.setBuiltBy(attr.getValue("Built-By"));
            jarManifest.setBuildNumber(attr.getValue("Implementation-Build"));
            jarManifest.setJavaBuildVersion(attr.getValue("Build-Jdk"));
        }

        return jarManifest;
    }

    /**
     * Get the manifest file declared in the application
     *
     * @return instance of Manifest class
     */
    private Manifest getManifest() {
        // get the full name of the application manifest file name
        String appManifestFileName = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString() + JarFile.MANIFEST_NAME;

        Enumeration resEnum;
        try {
            // get a list of all manifest files found in the jars loaded by the app
            resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resEnum.hasMoreElements()) {
                URL url = (URL) resEnum.nextElement();
                Manifest man = checkAppManifest(url, appManifestFileName);
                if (man != null) {
                    return man;
                }
            }
        } catch (IOException e1) {
            // Silently ignore wrong manifests on classpath?
        }
        return null;
    }

    /**
     * Check if the URL points to the application manifest file
     *
     * @param url
     * @param appManifestFileName
     * @return
     */
    private Manifest checkAppManifest(URL url, String appManifestFileName) throws IOException {
        // is the app manifest file?
        if (url.toString().equals(appManifestFileName)) {
            // open the manifest
            InputStream is = url.openStream();
            if (is != null) {
                // read the manifest and return it to the application
                return new Manifest(is);
            }
        }
        return null;
    }
}
