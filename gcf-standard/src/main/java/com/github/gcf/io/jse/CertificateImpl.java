package com.github.gcf.io.jse;

import java.security.cert.X509Certificate;

import javax.microedition.pki.Certificate;

/**
 * @author Marcel Patzlaff
 */
final class CertificateImpl implements Certificate {
    private final X509Certificate _cert;
    
    CertificateImpl(X509Certificate cert) {
        _cert= cert;
    }
    
    public String getIssuer() {
        return _cert.getIssuerX500Principal().getName();
    }

    public long getNotAfter() {
        return _cert.getNotAfter().getTime();
    }

    public long getNotBefore() {
        return _cert.getNotBefore().getTime();
    }

    public String getSerialNumber() {
        return _cert.getSerialNumber().toString();
    }

    public String getSigAlgName() {
        return _cert.getSigAlgName();
    }

    public String getSubject() {
        return _cert.getSubjectX500Principal().getName();
    }

    public String getType() {
        return _cert.getType();
    }

    public String getVersion() {
        return String.valueOf(_cert.getVersion());
    }
    
    public String toString() {
        return _cert.toString();
    }

}
