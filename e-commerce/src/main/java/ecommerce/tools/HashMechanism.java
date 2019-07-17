package ecommerce.tools;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Named;

/**
 * Classe que realiza hash de strings e bytes
 * @author Giovani
 *
 */
@Named
public class HashMechanism implements Serializable {
	/**
	 * Aplica SHA-256 em algum dado. Strings podem ser transformadas em bytes[] pelo método getBytes.
	 * @param dado
	 * @return byte[] contendo o hash dos dados enviados por parâmetro.
	 * @throws NoSuchAlgorithmException
	 */
	public byte[] aplicarSHA256(byte[] dado) throws NoSuchAlgorithmException {
		MessageDigest digester = MessageDigest.getInstance("SHA-256");
		return digester.digest(dado);
	}
}
