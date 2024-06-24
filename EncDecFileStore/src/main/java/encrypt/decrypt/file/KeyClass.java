package encrypt.decrypt.file;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class KeyClass {

	
	private SecretKey secKey;
	private KeyPair pair;
	private Cipher cipher;
	
	public SecretKey getSecKey() {
		return this.secKey;
	}
	
	public KeyPair getKeyPair() {
		return this.pair;
	}
	
	/*
	public static void main(String[] args) {
		/*
		KeyClass kc = new KeyClass("RSA");
		long time =System.currentTimeMillis();
		//kc.encdecWithRSA(Cipher.ENCRYPT_MODE, new File("RawUnEncryptedImg.png"), new File("EncFile"));
		System.out.println("Encode operation took:" + (time - System.currentTimeMillis()));
		time =System.currentTimeMillis();
		kc.encdecWithRSA(Cipher.DECRYPT_MODE, new File("EncFile1"), new File("DecryptedFile"));
		System.out.println("Decode operation took:" + (time - System.currentTimeMillis()));
		
		/*
		KeyClass kc = new KeyClass("AES");
		long time =System.currentTimeMillis();
		kc.encdecWithAESCBC(Cipher.ENCRYPT_MODE, new File("RawUnEncryptedImg.png"), new File("EncFile"));
		System.out.println("Encode operation took:" + (time - System.currentTimeMillis()));
		time =System.currentTimeMillis();
		kc.encdecWithAESCBC(Cipher.DECRYPT_MODE, new File("EncFile"), new File("DecryptedFile"));
		System.out.println("Decode operation took:" + (time - System.currentTimeMillis()));
		
	}
	*/

	//java KeyClass ENCRYPT_MODE C://
	
	private void keyMaker(String str) {
		File f = new File("KP");
		try(
			FileOutputStream fout = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
		) {			
			if(str.equals("RSA")) {
				KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
				keyPairGen.initialize(2048);
				KeyPair pair = keyPairGen.generateKeyPair();  
				oos.writeObject(pair);
				pair = null;
				
			} else if(str.equals("AES")) {
				SecretKey secKey;
				KeyGenerator keyPairGen = KeyGenerator.getInstance("AES");
				keyPairGen.init(192);
				secKey = keyPairGen.generateKey(); 
				oos.writeObject(secKey);
				secKey = null;
			}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (NoSuchAlgorithmException e) {e.printStackTrace();} 
	}
	
	

	public KeyClass(String str) {
		File f = new File("KP");
		
		if (!f.exists()) {
			this.keyMaker(str);
		}
		try(
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
		) {			
			if(str.equals("RSA")) {
				this.pair = (KeyPair) ois.readObject();
				this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			} else if(str.equals("AES")) {
				this.secKey = (SecretKey) ois.readObject();
				this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (ClassNotFoundException e) {e.printStackTrace();} 
		catch (NoSuchAlgorithmException e) {e.printStackTrace();} 
		catch (NoSuchPaddingException e) {e.printStackTrace();} 

	}
	
	public void encdecWithAES(int cipherMode, File in, BufferedOutputStream bos) {
		try(
				FileInputStream fis = new FileInputStream(in);
			) {
				byte[] bin;

				if (cipherMode == Cipher.ENCRYPT_MODE) {
					cipher.init(Cipher.ENCRYPT_MODE, this.secKey);

					bin = new byte[15];

					while (fis.read(bin) != -1) {
						cipher.update(bin);
						bos.write(cipher.doFinal());
					}
				} else if (cipherMode == Cipher.DECRYPT_MODE) {
					cipher.init(Cipher.DECRYPT_MODE, this.secKey);

					bin = new byte[16];

					while (fis.read(bin) != -1) {
						cipher.update(bin);
						bos.write(cipher.doFinal());
					}
				}
				bos.flush();
			} 
			catch (FileNotFoundException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();} 
			catch (InvalidKeyException e) {e.printStackTrace();} 
			catch (IllegalBlockSizeException e) {e.printStackTrace();} 
			catch (BadPaddingException e) {e.printStackTrace();}
	}
	
	
	
	public void encdecWithAESCBC(int cipherMode, File in, File out) {
		try(
			FileInputStream fis = new FileInputStream(in);
			BufferedInputStream bis = new BufferedInputStream(fis);
			FileOutputStream fos = new FileOutputStream(out);
			BufferedOutputStream bos = new BufferedOutputStream(fos);			
		) {
			byte[] IV = new byte[16];	        
			byte[] bin;
			
			Cipher cip = Cipher.getInstance("AES/CBC/PKCS5Padding");
			if (cipherMode == Cipher.ENCRYPT_MODE) {
				SecureRandom random = new SecureRandom();
		        random.nextBytes(IV); bos.write(IV);
		        
				cip.init(Cipher.ENCRYPT_MODE, this.secKey, new IvParameterSpec(IV));
				
				bin = new byte[15];
				
				//System.out.println(bis.available());
				
				while (bis.read(bin) != -1) {
					cip.update(bin);
					bos.write(cip.doFinal());
				}
			} else if (cipherMode == Cipher.DECRYPT_MODE) {
				fis.read(IV);
				cip.init(Cipher.DECRYPT_MODE, this.secKey, new IvParameterSpec(IV));

				bin = new byte[16];

				while (bis.read(bin) != -1) {
					cip.update(bin);
					bos.write(cip.doFinal());
				}
			}
			bos.flush();
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (InvalidKeyException e) {e.printStackTrace();} 
		catch (IllegalBlockSizeException e) {e.printStackTrace();} 
		catch (BadPaddingException e) {e.printStackTrace();} 
		catch (NoSuchAlgorithmException e) {e.printStackTrace();} 
		catch (NoSuchPaddingException e) {e.printStackTrace();}
		catch (InvalidAlgorithmParameterException e) {e.printStackTrace();}
	}
	
	
	
	public void encdecWithAES(int cipherMode, File in, File out) {
		try(
			FileInputStream fis = new FileInputStream(in);
			BufferedInputStream bis = new BufferedInputStream(fis);
			FileOutputStream fos = new FileOutputStream(out);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
		) {
			byte[] bin;

			if (cipherMode == Cipher.ENCRYPT_MODE) {		        
				cipher.init(Cipher.ENCRYPT_MODE, this.secKey);
				
				bin = new byte[15];

				while (bis.read(bin) != -1) {
					cipher.update(bin);
					bos.write(cipher.doFinal());
				}
			} else if (cipherMode == Cipher.DECRYPT_MODE) {
				cipher.init(Cipher.DECRYPT_MODE, this.secKey);

				bin = new byte[16];

				while (bis.read(bin) != -1) {
					cipher.update(bin);
					bos.write(cipher.doFinal());
				}
			}
			bos.flush();
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (InvalidKeyException e) {e.printStackTrace();} 
		catch (IllegalBlockSizeException e) {e.printStackTrace();} 
		catch (BadPaddingException e) {e.printStackTrace();}
	}
	
	public void encdecWithAES(int cipherMode, BufferedInputStream bis, File out) {
		try(
			FileOutputStream fos = new FileOutputStream(out);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
		) {
			byte[] bin;

			if (cipherMode == Cipher.ENCRYPT_MODE) {
				cipher.init(Cipher.ENCRYPT_MODE, this.secKey);

				bin = new byte[15];

				while (bis.read(bin) != -1) {
					cipher.update(bin);
					bos.write(cipher.doFinal());
				}
			} else if (cipherMode == Cipher.DECRYPT_MODE) {
				cipher.init(Cipher.DECRYPT_MODE, this.secKey);

				bin = new byte[16];

				while (bis.read(bin) != -1) {
					cipher.update(bin);
					bos.write(cipher.doFinal());
				}
			}
			bos.flush();
			bis.close();
		} 
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (InvalidKeyException e) {e.printStackTrace();} 
		catch (IllegalBlockSizeException e) {e.printStackTrace();} 
		catch (BadPaddingException e) {e.printStackTrace();}
	}
	public void encdecWithRSA(int cipherMode, File in, File out) {
		try (
			FileInputStream fis = new FileInputStream(in);
			BufferedInputStream bis = new BufferedInputStream(fis);
			FileOutputStream fos = new FileOutputStream(out);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
		) {
			byte[] bin;
			
			if (cipherMode == Cipher.ENCRYPT_MODE) {
				cipher.init(Cipher.ENCRYPT_MODE, this.pair.getPublic());

				bin = new byte[244];

				while (bis.read(bin) != -1) {
					cipher.update(bin);
					bos.write(cipher.doFinal());
				}
			} else if (cipherMode == Cipher.DECRYPT_MODE) {
				cipher.init(Cipher.DECRYPT_MODE, this.pair.getPrivate());

				bin = new byte[256];

				while (bis.read(bin) != -1) {
					cipher.update(bin);
					bos.write(cipher.doFinal());
				}
			}
			
			bos.flush();
		}
		catch (FileNotFoundException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();} 
		catch (InvalidKeyException e) {e.printStackTrace();} 
		catch (IllegalBlockSizeException e) {e.printStackTrace();} 
		catch (BadPaddingException e) {e.printStackTrace();}
	}
}
