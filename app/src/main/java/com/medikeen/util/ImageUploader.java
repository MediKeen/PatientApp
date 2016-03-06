package com.medikeen.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;


public class ImageUploader {
	
	// 10 mb
	private static final long MAX_FIXED_SIZE = 1024;
	private static final String CRLF = System.getProperty("line.separator");

//	public static void main(String args[])
//	{
//		try {
//			File file = new File("F:\\Dropbox\\Camera Uploads\\2015-01-25 01.30.53.jpg");
//			
//			int returnCode = postFileToURL(file, "image/jpeg", new URL("http://www.medikeen.com/android_api/prescription/upload.php"));
//			
//			System.out.println(returnCode);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public static int postFileToURL(File file, String fileName, String mimeType, URL url) throws IOException {
		
		DataOutputStream requestData = null;
		
		try {
			int size = (int) file.length();
			//String fileName = file.getName();
			
			//Create a random boundary string
			Random random = new Random();
			byte[] randomBytes = new byte[16];
			random.nextBytes(randomBytes);
			String boundary = "hQQHYlJoy4vhMgzfJw0Wcw==";
			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.setUseCaches(false);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			
			//Set the http headers
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Cache-Control", "no-cache");
			urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			
			urlConnection.setChunkedStreamingMode(size);
			
			// Open file for reading
			FileInputStream fileInput = new FileInputStream(file);
			// Open connection to server...
			OutputStream outputStream = urlConnection.getOutputStream();
			requestData = new DataOutputStream(outputStream);
			
			//Write first boundary for this file
			requestData.writeBytes("--" + boundary + CRLF);
			//Let the server know the filename
			requestData.writeBytes("Content-Disposition: form-data; name=\"fileToUpload\"; filename=\"" + fileName + CRLF);
			// and the MIME type of the file
			requestData.writeBytes("Content-Type:" + mimeType + CRLF + CRLF);
			
			// read the local file and write to server in one loop
			int bytesRead;
			byte[] buffer = new byte[8192];
			while((bytesRead = fileInput.read(buffer)) != -1) {
				requestData.write(buffer , 0 , bytesRead);
			}
			
			fileInput.close();
			
			//write boundary indicating end of file
			requestData.writeBytes(CRLF);
			requestData.writeBytes("--" + boundary + "--" + CRLF);
			//requestData.writeBytes("Content-Disposition: form-data; name=\"submit\"" + CRLF);
			//requestData.writeBytes(CRLF);
			// and the MIME type of the file
			//requestData.writeBytes("Upload Image" + CRLF);
			requestData.flush();
			
			InputStream content =  (InputStream)urlConnection.getContent();
			
			int length = content.read(buffer, 0, 2000);
			
			String c = new String(buffer, "UTF-8");
			
			System.out.println(c);
			
			return urlConnection.getResponseCode();
		} 
		finally {
			if(requestData != null) {
				requestData.close();
			}
		}
	}
}
