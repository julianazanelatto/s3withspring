package com.dioclass.UploadImagesApi.Controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

import static com.dioclass.UploadImagesApi.Config.AccessConfig.AmazonConfig.s3ClientWithCredentials;
import static com.dioclass.s3withspring.Service.BucketServices.*;
import static com.dioclass.s3withspring.Service.ObjectServices.*;

@RestController
public class Controller {

    @GetMapping("/get/buckets")
    public ResponseEntity<?> listOfBuckets(){
        final AmazonS3 s3 = s3ClientWithCredentials();
        try {
            List<Bucket> bucketlist = s3.listBuckets();
            return new ResponseEntity<>(bucketlist, HttpStatus.OK);
        }catch (AmazonS3Exception e){
            return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/bucket/{bucketName}")
    public ResponseEntity<?> getTheBucket(@PathVariable String bucketName) {
        try{
            Bucket bucket = getBucket(bucketName);
            String messageResponse = "Searching .... Bucket founded!"
                    +"Name - "+ bucket.getName()
                    +"Owner - "+ bucket.getOwner()
                    +"Creation date - " + bucket.getCreationDate()
                    +"Bucket class - " + bucket.getClass();
            return new ResponseEntity<>(messageResponse,HttpStatus.OK);
        }catch (AmazonS3Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE) //
                    .header(HttpHeaders.CONTENT_TYPE)
                    .body("BucketName doesn't exists!\n " +
                            "Error message:\n" + e.getErrorMessage());
        }

    }

    //methods with the HTTP operations
    @GetMapping("/get/objects/{bucketName}")
    public ResponseEntity<?> listOfObjects(@PathVariable String bucketName){
        AmazonS3 s3 = s3ClientWithCredentials();
        try {
            ListObjectsV2Result result = s3.listObjectsV2(bucketName);
            List<S3ObjectSummary> objects = result.getObjectSummaries();
            System.out.println("List of objects");
            for (S3ObjectSummary os : objects) {
                System.out.println("* " + os.getKey());
            }
            return new ResponseEntity<>(objects,HttpStatus.OK);
        }catch (AmazonS3Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE) //
                    .header(HttpHeaders.CONTENT_TYPE)
                    .body("BucketName doesn't exists!\n " +
                            "Error message:\n" + e.getErrorMessage());
        }
    }

    @PutMapping("/put/upload/image/{bucketName}/{filePath}")
    public ResponseEntity<?> uploadAnImage(@PathVariable String bucketName, @PathVariable String filePath){
        try{
            uploadObject(bucketName, filePath);
            String messageResponse = "Successful upload! The file "+ new File(filePath).getName()
                    +"is storage in the bucket "+ bucketName;
            return new ResponseEntity<>(messageResponse,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getStackTrace(),HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("/put/bucket/{bucketName}")
    public ResponseEntity<?> createTheBucket(@PathVariable String bucketName) {
        final AmazonS3 s3 = s3ClientWithCredentials();
        Bucket newbucket = null;

        if (!s3.doesBucketExistV2(bucketName)) {
            try {
                newbucket = s3.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                return new ResponseEntity<>(e.getErrorMessage(),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(newbucket,HttpStatus.OK);
        }

        try{
            newbucket = getBucket(bucketName);
            return new ResponseEntity<>(newbucket, HttpStatus.OK);
        }catch (AmazonS3Exception e) {
            String messageError = "The Bucket already exists. You don't own the bucket with this name.\n" +
                    "Amazon message error:" + e.getMessage();
            return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
        }
    }

}

