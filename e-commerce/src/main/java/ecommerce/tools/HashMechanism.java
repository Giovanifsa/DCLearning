package ecommerce.tools;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Named;

@Named
public class HashMechanism implements Serializable {
	public byte[] defaultHashBytes(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest digester = MessageDigest.getInstance("SHA-256");
		return digester.digest(bytes);
	}
	
	public void teste() {
		
	}
}
