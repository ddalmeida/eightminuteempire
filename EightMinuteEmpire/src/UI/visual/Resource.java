package UI.visual;

import java.net.URL;

public class Resource {

    public static final URL getResourceFile(String name) {
        
        URL url = Resource.class.getResource(name);
        return url;
    }
}