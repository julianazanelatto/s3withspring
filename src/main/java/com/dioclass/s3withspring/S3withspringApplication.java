package com.dioclass.s3withspring;

import com.amazonaws.services.s3.model.Bucket;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static com.dioclass.s3withspring.ServiceBucket.BucketBasicOperations.*;
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "uc_s3withspringapplication_b", columnNames = {"b"})
})
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


	}

}
