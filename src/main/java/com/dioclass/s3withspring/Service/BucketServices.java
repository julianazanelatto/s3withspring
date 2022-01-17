package com.dioclass.s3withspring.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;
import static com.dioclass.s3withspring.Config.AmazonConfig.*;

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

    public static Bucket createBucket(String bucketName){
        final AmazonS3 s3 = s3WithCredentials();
        Bucket newbucket = null;
        boolean doesExist = s3.doesBucketExistV2(bucketName);
        //System.err.println(doesExist);
        if (doesExist){
            System.out.println(("The bucket already exists!"));
            try {
                newbucket = getBucket(bucketName);
            }catch (AmazonS3Exception e){
                System.err.println("You are not the owner of this bucket! " +
                        "The bucket name is not available.\nMessage erorr: "+e.getMessage());
                System.exit(1);
            }
        }else{
            try{
                newbucket = s3.createBucket(bucketName);
            }catch(AmazonS3Exception e){
                System.out.println("Error during the bucket creation!");
                System.err.println("Message error:\n"+e.getMessage());
            }
        }
        return newbucket;
    }
}
