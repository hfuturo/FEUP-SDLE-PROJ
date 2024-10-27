package feup.sdle;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashAlgorithm implements HashAlgorithm {
    @Override
    public BigInteger getHash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        return new BigInteger(1, messageDigest);
    }
}
