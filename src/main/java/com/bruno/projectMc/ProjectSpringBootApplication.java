package com.bruno.projectMc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruno.projectMc.services.S3Service;

@SpringBootApplication
public class ProjectSpringBootApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\temporario\\aa.jpg");
	}

}
