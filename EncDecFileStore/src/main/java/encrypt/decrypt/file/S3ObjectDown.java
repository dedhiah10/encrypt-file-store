package encrypt.decrypt.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;

public class S3ObjectDown {
	
	private String bucketName = "harsh-test-encryptdecrypt";
	private static Regions clientRegion = Regions.AP_SOUTH_1;
	private AmazonS3 s3Client;

	public S3ObjectDown() {
		s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new ProfileCredentialsProvider())
				.withRegion(clientRegion).build();
	}
	
	public void createbuck() {
		if (!s3Client.doesBucketExistV2(this.bucketName)) {
			s3Client.createBucket(new CreateBucketRequest(this.bucketName));
			String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(this.bucketName));
			System.err.println("Bucket location: " + bucketLocation);
		}
	}
	
	public void createbuck(String str) {
		if (!str.equals("")) {
			if (!s3Client.doesBucketExistV2(str)) {
				s3Client.createBucket(new CreateBucketRequest(str));
				String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(str));
				System.err.println("Bucket location: " + bucketLocation);
			}
		}
	}

	public void emptybuck(String str) {
		if (!str.equals("")) {
			ObjectListing objectListing = s3Client.listObjects(str);
			while (true) {
				Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
				while (objIter.hasNext()) {
					s3Client.deleteObject(str, objIter.next().getKey());
				}
				if (objectListing.isTruncated()) {
					objectListing = s3Client.listNextBatchOfObjects(objectListing);
				} else {
					break;
				}
			}

			VersionListing versionList = s3Client.listVersions(new ListVersionsRequest().withBucketName(str));
			while (true) {
				Iterator<S3VersionSummary> versionIter = versionList.getVersionSummaries().iterator();
				while (versionIter.hasNext()) {
					S3VersionSummary vs = versionIter.next();
					s3Client.deleteVersion(str, vs.getKey(), vs.getVersionId());
				}
				if (versionList.isTruncated()) {
					versionList = s3Client.listNextBatchOfVersions(versionList);
				} else {
					break;
				}
			}
		}
	}

	public void emptybuck() {
		ObjectListing objectListing = s3Client.listObjects(this.bucketName);
		while (true) {
			Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
			while (objIter.hasNext()) {
				s3Client.deleteObject(this.bucketName, objIter.next().getKey());
			}
			if (objectListing.isTruncated()) {
				objectListing = s3Client.listNextBatchOfObjects(objectListing);
			} else {
				break;
			}
		}

		VersionListing versionList = s3Client.listVersions(new ListVersionsRequest().withBucketName(this.bucketName));
		while (true) {
			Iterator<S3VersionSummary> versionIter = versionList.getVersionSummaries().iterator();
			while (versionIter.hasNext()) {
				S3VersionSummary vs = versionIter.next();
				s3Client.deleteVersion(this.bucketName, vs.getKey(), vs.getVersionId());
			}
			if (versionList.isTruncated()) {
				versionList = s3Client.listNextBatchOfVersions(versionList);
			} else {
				break;
			}
		}
	}
	
	public void deletebuck() {
		this.emptybuck(this.bucketName);
		s3Client.deleteBucket(this.bucketName);
	}
	
	public void deletebuck(String str) {
		if (!str.equals("")) {
			this.emptybuck(str);
			s3Client.deleteBucket(str);
		}
	}

	
	public void listobjects() { 
		ObjectListing objectListing = s3Client.listObjects(this.bucketName); 
		Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
		while (objIter.hasNext()) {
			System.err.println(objIter.next());
		}
	}
	  
	  
	  
	public void listobjects(String str) { 
		if(!str.equals("")) {
			ObjectListing objectListing = s3Client.listObjects(str); 
			Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
			while (objIter.hasNext()) {
				System.err.println(objIter.next());
			}
		} 
	}
	 

	public void s3put(File f, String name) {
		this.s3Client.putObject(this.bucketName, name, f);
		System.err.println("The File has been Uploaded to"+ this.bucketName +"!");
	}

	public void s3put(File f, String name, String str) {
		if (!str.equals("")) {
			this.s3Client.putObject(str, name, f);
			System.err.println("The File has been Uploaded to" + str + "!");
		}
	}

	public void s3get(String name, File f) {
		try (
				FileOutputStream fos = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fos);) {
			this.s3Client.getObject(new GetObjectRequest(this.bucketName, name), f);
			System.err.println("The File has been Downloadedto"+ this.bucketName +"!");
		} 
		catch (IOException e1) {e1.printStackTrace();}
	}

	public void s3get(String name, File f, String str) {
		if (!str.equals("")) {
			try (
					FileOutputStream fos = new FileOutputStream(f);
					BufferedOutputStream bos = new BufferedOutputStream(fos);) {	
				this.s3Client.getObject(new GetObjectRequest(str, name), f);
				System.err.println("The File has been Downloaded from" + str + "!");
			} 
			catch (IOException e1) {e1.printStackTrace();}
		}
	}
}
