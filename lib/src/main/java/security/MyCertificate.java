package security;


import com.google.common.collect.Sets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;

public class MyCertificate {

    public static void main(String[] args) throws CertificateException, IOException, NoSuchAlgorithmException {
        FileInputStream fis = new FileInputStream("C:\\Users\\Laidian\\Desktop\\test.cer");
        BufferedInputStream bis = new BufferedInputStream(fis);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        X509Certificate cert = (X509Certificate) cf.generateCertificate(bis);
//        System.out.println(cert.toString());
//        System.out.println("cert serialNumber : " + cert.getSerialNumber().toString(16).toUpperCase());
//        cert.checkValidity();
        System.out.println("=======cert list===========");
        cf.generateCertificates(fis).stream().forEach(c -> {
            System.out.println(c);
        });

        System.out.println("=======cert path===========");
        fis = new FileInputStream("C:\\Users\\Laidian\\Desktop\\test.p7b");
        bis = new BufferedInputStream(fis);
        CertPath cp = cf.generateCertPath(bis, "PKCS7");
        cp = cf.generateCertPath(cp.getCertificates().subList(0,cp.getCertificates().size()-1));
        cp.getCertificates().stream().forEach(c -> {
            System.out.println(c);
        });

        CertPathValidator cv = CertPathValidator.getInstance("PKIX");
        try {
            cv.validate(cp, new PKIXParameters(Sets.newHashSet(new TrustAnchor((X509Certificate) cp.getCertificates().get(cp.getCertificates().size() - 1), null))));
        } catch (CertPathValidatorException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }
}
