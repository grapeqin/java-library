package security;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.X509EncodedKeySpec;

public class VerSig {
    public static void main(String[] args) {
        /* Verify a DSA signature */

        if (args.length != 3) {
            System.out.println("Usage: VerSig " +
                    "publickeyfile signaturefile " + "datafile");
        }
        else try {
            // the rest of the code goes here
            //Step 1
//            FileInputStream keyfis  = new FileInputStream(args[0]);
//            byte[] encKey = new byte[keyfis .available()];
//            keyfis .read(encKey);
//            keyfis .close();
//
//            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
//            KeyFactory keyFactory = KeyFactory.getInstance("DSA","SUN");
//            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            FileInputStream certfis = new FileInputStream(args[0]);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(certfis);
            PublicKey pubKey = cert.getPublicKey();
            //Step 2
            FileInputStream sigfis = new FileInputStream(args[1]);
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify);
            sigfis.close();

            //Step 3
            Signature sig = Signature.getInstance("SHA256withDSA","SUN");
            sig.initVerify(pubKey);

            FileInputStream datafis = new FileInputStream(args[2]);
            BufferedInputStream bufin = new BufferedInputStream(datafis);
            byte[] buffer = new byte[1024];
            int n ;
            while((n = bufin.read(buffer)) > 0){
                sig.update(buffer,0,n);
            }
            bufin.close();
            datafis.close();

            boolean verifies =  sig.verify(sigToVerify);
            System.out.println("signature verifies: " + verifies);
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
}
