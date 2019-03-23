package org.schlimm.java9.jep219;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;

/**
 * <a href="https://openjdk.java.net/jeps/219">JEP 219: Datagram Transport Layer Security (DTLS)</a>
 * <br>
 * <a href="https://docs.oracle.com/javase/9/security/java-secure-socket-extension-jsse-reference-guide.htm#JSSEC-GUID-B7AB25FA-7F0C-4EFA-A827-813B2CE7FBDC">API Description</a>
 * <br>
 * <a href="https://docs.oracle.com/javase/9/security/java-secure-socket-extension-jsse-reference-guide.htm#JSSEC-GUID-B7AB25FA-7F0C-4EFA-A827-813B2CE7FBDC">Examples</a>
 * <br>
 * <a href="https://hg.openjdk.java.net/jdk9/jdk9/jdk/file/65464a307408/test/javax/net/ssl/DTLS">DTLS Jdk Test Suite</a>
 */
public class Jep219_DatagramTransportLayerSecurity {

    public static void main(String[] args)
        throws Exception
    {
        SSLContext sslContext = SSLContext.getInstance("DTLS");
        sslContext.init(
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()).getKeyManagers(), 
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).getTrustManagers(), 
                null);
        SSLEngine engine = sslContext.createSSLEngine();
        engine.setUseClientMode(true); 
    }
    
}
