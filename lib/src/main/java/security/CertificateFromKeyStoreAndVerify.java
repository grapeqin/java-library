package security;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

public class CertificateFromKeyStoreAndVerify {

    public static void main(String[] args) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("C:/Users/Laidian/.keystore"), "123456".toCharArray());
            Certificate[] ces = ks.getCertificateChain("my-first-self");

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            CertPath cp = cf.generateCertPath(Arrays.asList(ces));
            cp.getCertificates().stream().forEach(c -> {
                System.out.println(c);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
