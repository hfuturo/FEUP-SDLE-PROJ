package feup.sdle.crypto;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public interface HashAlgorithm  {
    public BigInteger getHash(String input) throws NoSuchAlgorithmException;
}