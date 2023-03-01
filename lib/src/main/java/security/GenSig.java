package security;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;

public class GenSig {
    public static void main(String[] args) {
        /* Generate a DSA signature */

        if (args.length != 1) {
            System.out.println("Usage: GenSig nameOfFileToSign");
        } else try {
            // the rest of the code goes here
            //Step 1
//            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA","SUN");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
//            keyGen.initialize(1024,random);

            //Step 2
//            KeyPair pair = keyGen.generateKeyPair();
//            PrivateKey priv = pair.getPrivate();
            KeyStore ks = KeyStore.getInstance("JKS");
            String ksName = "C:/Users/Laidian/.keystore";
            FileInputStream ksfis = new FileInputStream(ksName);
            BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
            char[] spass = "123456".toCharArray();
            ks.load(ksbufin, spass);

            char[] kpass = "123456".toCharArray();
            PrivateKey priv = (PrivateKey) ks.getKey("my-first-self", kpass);

            Certificate pub = ks.getCertificate("my-first-self");
//            System.out.println("priv key : " + new String(priv.getEncoded()));
//            PublicKey pub = pair.getPublic();
//            System.out.println("pub key : " + new String(pub.getEncoded()));
            //Step 3
            Signature dsa = Signature.getInstance("SHA256withDSA", "SUN");
            dsa.initSign(priv);
            FileInputStream fis = new FileInputStream(args[0]);
            BufferedInputStream bufIn = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufIn.read(buffer)) > 0) {
                dsa.update(buffer, 0, len);
            }
            bufIn.close();
            byte[] realSig = dsa.sign();

            //Step 4
            FileOutputStream sigfos = new FileOutputStream("sig");
            sigfos.write(realSig);
            sigfos.close();

            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("suecert");
            keyfos.write(key);
            keyfos.close();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
            e.printStackTrace();
        }
    }
}
