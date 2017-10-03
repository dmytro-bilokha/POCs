package com.dmytrobilokha.viaproxy;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;

public class ProxySelectorWrapper extends ProxySelector {

    private final ProxySelector realProxySelector;

    private ProxySelectorWrapper(ProxySelector realProxySelector) {
        this.realProxySelector = realProxySelector;
    }

    public static void setup() {
        System.setProperty("java.net.useSystemProxies", "true");
/*        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        System.setProperty("https.proxyHost", "localhost");
        System.setProperty("https.proxyPort", "8118");
        System.setProperty("https.proxyUser", "User");
        System.setProperty("https.proxyPassword", "password");*/
        ProxySelectorWrapper proxySelectorWrapper = new ProxySelectorWrapper(ProxySelector.getDefault());
        ProxySelector.setDefault(proxySelectorWrapper);
    }

    @Override
    public List<Proxy> select(URI uri) {
        System.out.println("Asked to select proxy for uri: " + uri);
        List<Proxy> proxies = realProxySelector.select(uri);
        System.out.println("Got list of proxies: " + proxies);
        return proxies;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        System.out.println("Connection failed for uri: " + uri + " socket address: " + sa
                            + " with IOException: " + ioe);
        realProxySelector.connectFailed(uri, sa, ioe);
    }
}
