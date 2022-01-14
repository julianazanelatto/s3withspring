package com.dioclass.s3withspring;

import com.amazonaws.services.s3.model.Bucket;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.file.Paths;

import static com.dioclass.s3withspring.Service.BucketServices.*;
import static com.dioclass.s3withspring.Service.ObjectServices.*;

@SpringBootApplication
public class S3withspringApplication {

	public static void main(String[] args){
		//bucket creation
		String bucket_name = "dio-class-s3";
		Bucket b = createBucket(bucket_name);
		System.out.println("Creating the bucket " + bucket_name+"\n");
		if (b == null)
			System.out.println("Erro ao criar o bucket!\n");
		else
			System.out.println("Done");

		listOfBuckets(); //list of buckets
		String imagePath = "index.jpeg";
		uploadObject(b.getName(),imagePath); //upload an image
		System.out.println(Paths.get(imagePath).getFileName().toString());
		// trying to upload a file .txt
		//String filePathTxt = "/home/jm/aws s3";
		//uploadObject(b.getName(),filePathTxt);

		listObjects(bucket_name);
	}

}
