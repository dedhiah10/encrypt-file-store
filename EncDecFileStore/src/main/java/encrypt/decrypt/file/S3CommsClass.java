package encrypt.decrypt.file;

import java.io.File;

import javax.crypto.Cipher;


public class S3CommsClass {
	KeyClass kc;
	S3ObjectDown s3obd;
	
	public S3CommsClass() {
		this.kc = new KeyClass("AES");
		//this.kc = new KeyClass("RSA");
		this.s3obd = new S3ObjectDown();
	}
	
	private void encupload(File f, String name) {
		kc.encdecWithAES(Cipher.ENCRYPT_MODE, f, new File("EncFile"));
		//kc.encdecWithRSA(Cipher.ENCRYPT_MODE, f, new File("EncFile"));		
		this.s3obd.s3put(new File("EncFile"), name);
	}
	
	private void decdownload(String name, File f) {		
		this.s3obd.s3get(name, new File("EncFile"));
		kc.encdecWithAES(Cipher.DECRYPT_MODE, new File("EncFile"), f);
		//kc.encdecWithRSA(Cipher.DECRYPT_MODE, new File("EncFile"), f);
	}
	
	
public static void main(String[] args) {		
		S3CommsClass s3 = new S3CommsClass();
		
		S3ObjectDown s3ob1 = new S3ObjectDown();
		//s3ob1.createbuck();
		s3ob1.listobjects();
		
		//s3.encupload(new File("RawUnEncryptedImg.png"), "uploadedFile");
		
		//s3.decdownload("uploadedFile", new File("DecryptedFile"));
		
		//s3ob1.deletebuck();
				
	}
}
