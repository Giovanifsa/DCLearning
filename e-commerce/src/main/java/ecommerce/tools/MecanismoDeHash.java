package ecommerce.tools;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Named;

/**
 * Classe que realiza hash de strings e bytes.
 * Hash é em resumo a transformação de um dado em um valor único, com base em um cálculo.
 * Tal cálculo só há uma direção, ou seja, é impossível obter o dado original matemáticamente.
 * 
 * @author Giovani
 *
 */
@Named
public class MecanismoDeHash implements Serializable {
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
