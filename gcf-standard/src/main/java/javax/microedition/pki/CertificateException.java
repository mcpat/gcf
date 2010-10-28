package javax.microedition.pki;

public class CertificateException extends java.io.IOException {
    private static final long serialVersionUID= 1L;
    
    private byte reason;
    private Certificate cert;
    public static final byte BAD_EXTENSIONS= 1;
    public static final byte CERTIFICATE_CHAIN_TOO_LONG= 2;
    public static final byte EXPIRED= 3;
    public static final byte UNAUTHORIZED_INTERMEDIATE_CA= 4;
    public static final byte MISSING_SIGNATURE= 5;
    public static final byte NOT_YET_VALID= 6;
    public static final byte SITENAME_MISMATCH= 7;
    public static final byte UNRECOGNIZED_ISSUER= 8;
    public static final byte UNSUPPORTED_SIGALG= 9;
    public static final byte INAPPROPRIATE_KEY_USAGE= 10;
    public static final byte BROKEN_CHAIN= 11;
    public static final byte ROOT_CA_EXPIRED= 12;
    public static final byte UNSUPPORTED_PUBLIC_KEY_TYPE= 13;
    public static final byte VERIFICATION_FAILED= 14;

    public CertificateException(Certificate certificate, byte status) {
        super(getMessageForReason(status));
        cert= certificate;
        reason= status;
    }

    public CertificateException(String message, Certificate certificate, byte status) {
        super(message);
        cert= certificate;
        reason= status;
    }

    public Certificate getCertificate() {
        return cert;
    }

    public byte getReason() {
        return reason;
    }

    static String getMessageForReason(int reason) {
        switch (reason) {
            case BAD_EXTENSIONS:
                return "Certificate has unrecognized critical extensions";

            case CERTIFICATE_CHAIN_TOO_LONG:
                return "Server certificate chain exceeds the length allowed " + "by an issuer's policy";

            case EXPIRED:
                return "Certificate is expired";

            case UNAUTHORIZED_INTERMEDIATE_CA:
                return "Intermediate certificate in the chain does not have the "
                        + "authority to be an intermediate CA";

            case MISSING_SIGNATURE:
                return "Certificate object does not contain a signature";

            case NOT_YET_VALID:
                return "Certificate is not yet valid";

            case SITENAME_MISMATCH:
                return "Certificate does not contain the correct site name";

            case UNRECOGNIZED_ISSUER:
                return "Certificate was issued by an unrecognized entity";

            case UNSUPPORTED_SIGALG:
                return "Certificate was signed using an unsupported algorithm";

            case INAPPROPRIATE_KEY_USAGE:
                return "Certificate's public key has been used in a way deemed " + "inappropriate by the issuer";

            case BROKEN_CHAIN:
                return "Certificate in a chain was not issued by the next " + "authority in the chain";

            case ROOT_CA_EXPIRED:
                return "Root CA's public key is expired";

            case UNSUPPORTED_PUBLIC_KEY_TYPE:
                return "Certificate has a public key that is not a " + "supported type";

            case VERIFICATION_FAILED:
                return "Certificate failed verification";
        }

        return "Unknown reason (" + reason + ")";
    }
}
