package com.dioclass.s3withspring;

import com.amazonaws.services.s3.model.Bucket;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static com.dioclass.s3withspring.ServiceBucket.BucketBasicOperations.*;
import static com.dioclass.s3withspring.ServiceObjects.ObjectServices.*;

@SpringBootApplication
public class S3withspringApplication {

	public static void main(String[] args){
		//criar um bucket
		String bucket_name = "dio-class-s3";
		Bucket b = createBucket(bucket_name);
		System.out.println("Creating the bucket " + bucket_name+"\n");

		if (b == null)
			System.out.println("Erro ao criar o bucket!\n");
		else
			System.out.println("Done");

		//listar todos os buckets existentes
		listOfBuckets();

		//upload de imagem
		String imagePath = "/home/jm/Pictures/index.jpeg";
		uploadObject(bucket_name,imagePath);

		// tentativa de upload de um arquivo .txt
		String filePathTxt = "/home/jm/aws s3";
		uploadObject(bucket_name,filePathTxt);
	}

}
