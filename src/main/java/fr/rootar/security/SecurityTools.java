package fr.rootar.security;

import fr.rootar.entities.UtilisateurEntity;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
@RequestScoped
public class SecurityTools {
    public static String getToken(UtilisateurEntity utilisateur) {
        String token= Jwt.issuer("https://example.com/issuer")
                .expiresIn(Duration.ofMinutes(20))
                .upn(utilisateur.getLogin())
                .groups(utilisateur.getRole())
                .sign();
        return token;
    }

   private static String algorithm = "AES";
    private static SecretKeySpec secretKey = new SecretKeySpec("azertyuiopqsdfgh".getBytes(),algorithm);

    public static String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return new String(Base64.getUrlEncoder().encode(encryptedData));
    }

    public static String decrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        byte[] decryptedData = Base64.getUrlDecoder().decode(data);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String( cipher.doFinal(decryptedData));
    }

    public static Long checksum(String string) {
        long checksum = 0;
        for (int i = 0; i < string.length(); i++) {
            checksum += i * string.charAt(i);
        }
        return checksum;
    }
}
