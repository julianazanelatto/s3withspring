package com.dioclass.s3withspring.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.dioclass.s3withspring.Config.AmazonConfig.*;
@Controller
public class BucketServices {

    public static void listOfBuckets(){
        final AmazonS3 s3 = s3WithCredentials();
        List<Bucket> bucketlist = s3.listBuckets();
        System.out.println("List of buckets on you aws account:");
        for(Bucket buckte :bucketlist){
            System.out.println("-> " + buckte.getName());
        }
    }

    public static Bucket getBucket(String bucketName) {
        final AmazonS3 s3 = s3WithCredentials();
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucketName))
                return b;
        }
        return null;
    }

    public static boolean verificationBucketOwner(Bucket bucket){
        String owner = bucket.getOwner().getDisplayName();
        return owner.equals("julianazanelatto");
    }

    public static Bucket createBucket(String bucketName) {
        final AmazonS3 s3 = s3WithCredentials();
        Bucket newbucket;
        boolean doesExists = s3.doesBucketExistV2(bucketName);
        if (doesExists) {
            System.out.format("*****\nBucket %s already exists.\n*****\n", bucketName);
            newbucket = getBucket(bucketName);
            if (!verificationBucketOwner(newbucket))
                System.out.format("You don't own the bucket whit this name: %s",bucketName);
                //return null;
        } else {
            try {
                newbucket = s3.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                newbucket = getBucket(bucketName);
                System.out.println("AMAZON ERROR");
                System.err.println(e.getErrorMessage());
            }
        }
        return newbucket;
    }
}
