import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
 * Quick Notes
 *  We assume that we have plaintext.txt - which has contents in it.	
 *	1. line22: AES only supports key sizes of 16, 24 or 32 bytes
 *  2. Notice, basic setup for encrypt() and decrypt() are similar.
 * */

public class AESmain {
	public static void main(String[] args) throws IOException {

		try {
			AES a = new AES("plaintext.txt", "VerrrrySecureKey"); // fileName and Key(String)
			String plainData = a.readFile();
			String encData = a.encrypt(plainData);
			System.out.println("Encrypted data: " + encData);
			String decData = a.decrypt(encData);
			System.out.println(decData);
			
			a.writeEnc(encData);	// write encryptedData to ciphertext.txt
			a.writeDec(decData);	// write decryptedData to decryptedtext.txt
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

