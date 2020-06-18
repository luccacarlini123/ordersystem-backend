package com.mouzetech.ordersystem.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mouzetech.ordersystem.services.exceptions.FileException;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartfile) {
		try {
			String fileName = multipartfile.getOriginalFilename();
			InputStream is = multipartfile.getInputStream();
			String contentType = multipartfile.getContentType();
			return uploadFile(fileName, is, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO: "+e.getMessage());
		}
	}

	public URI uploadFile(String fileName, InputStream is, String contentType) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(contentType);
			s3Client.putObject(bucketName, fileName, is, metadata);
			return s3Client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI.");
		}
	}

}
