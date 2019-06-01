import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private byte[] keyVal; // the KEY for encryption in byte array.
	File file;

	AES(String fileName, String key) {
		keyVal = key.getBytes();

		file = new File(fileName);
		if (file.exists() == false)
			System.out.println("File doesn't exist");
		else
			System.out.println("File detected: " + file.getName());
	}

	public String encrypt(String plainData) {
		try {
			Key key = new SecretKeySpec(keyVal, "AES");
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encBytes = c.doFinal(plainData.getBytes());
			String encStr = Base64.getEncoder().encodeToString(encBytes);
			return encStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	void writeEnc(String encData) throws IOException {
		File file = new File("ciphertext.txt");
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(encData);
		writer.close();
	}

	public String decrypt(String encData) {
		try {
			Key key = new SecretKeySpec(keyVal, "AES");
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decBytes = c.doFinal(Base64.getDecoder().decode(encData));
			String decStr = new String(decBytes);
			return decStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	void writeDec(String decData) throws IOException {
		File file = new File("decryptedtext.txt");
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(decData);
		writer.close();
	}

	String readFile() throws IOException {
		Scanner sc = new Scanner(file);
		String plaintxt = "";
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			plaintxt += line + "\n";
		}
		sc.close();
		return plaintxt;
	}
}