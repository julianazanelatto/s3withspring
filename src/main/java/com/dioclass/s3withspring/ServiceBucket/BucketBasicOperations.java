package com.dioclass.s3withspring.ServiceBucket;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;
import static com.dioclass.s3withspring.Config.AmazonConfig.*;

public class BucketBasicOperations {

    public static Bucket getBucket(String bucketName) {
        final AmazonS3 s3 = s3WithCredentials();
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucketName))
                return b;
        }
        return null;
    }

    public static Bucket createBucket(String bucketName) {
        final AmazonS3 s3 = s3WithCredentials();
        Bucket newbucket = null;
        //System.out.println("Teste de entrada na função\n");
        boolean doesExists = s3.doesBucketExistV2(bucketName);
        if (doesExists) {
            System.out.format("*****\nBucket %s already exists.\n*****\n", bucketName);
            newbucket = getBucket(bucketName);
        } else {
            try {
                newbucket = s3.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                System.out.println("AMAZON ERROR");
                System.err.println(e.getErrorMessage());
            }
        }
        return newbucket;
    }

    public static void listOfBuckets(){
        final AmazonS3 s3 = s3WithCredentials();
        List<Bucket> bucketlist = s3.listBuckets();
        System.out.println("List of buckets on you aws account:");
        for(Bucket buckte :bucketlist){
            System.out.println("-> " + buckte.getName());
        }
    }
}
