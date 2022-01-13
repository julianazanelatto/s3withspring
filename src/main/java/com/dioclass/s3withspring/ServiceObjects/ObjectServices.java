package com.dioclass.s3withspring.ServiceObjects;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import static com.dioclass.s3withspring.Config.AmazonConfig.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class ObjectServices {

    public static void uploadObject(String bucketName, String filePath){
        String key_name = Paths.get(filePath).getFileName().toString();
        System.out.format("Uploading %s to S3 bucket %s...\n", filePath, bucketName);
        if (fileVerificationType(filePath)){
            final AmazonS3 s3 = s3WithCredentials();
            try {
                s3.putObject(bucketName, key_name, new File(filePath));
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
            System.out.println("Done!");
        }else
            System.out.println("Verification file Failure: the file is not an image");
    }

    public static void uploadObjectWithMetadata(String bucketName, String filePath){
        String key_name = Paths.get(filePath).getFileName().toString();
        System.out.format("Uploading %s to S3 bucket %s with contexttype and metadata...\n", filePath, bucketName);

        if (fileVerificationType(filePath)) {
            final AmazonS3 s3 = s3WithCredentials();
            try {
                PutObjectRequest request = new PutObjectRequest(bucketName, key_name, new File(filePath));
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType("image/" +
                        filePath.substring(filePath.lastIndexOf(".") + 1));
                metadata.addUserMetadata("title", "someTitle");
                request.setMetadata(metadata);
                s3.putObject(request);
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
            System.out.println("Done!");
        }else
            System.out.println("Verification file Failure: the file is not an image");

    }

    private static boolean fileVerificationType(String filePath){
        String fileName = new File(filePath).getName();

        if (fileName.contains(".png") || fileName.contains(".jpeg")
        || fileName.contains(".jpg")) {
            System.out.println("The file is an image, the upload will continue.\n");
            return true;
        }
        int positionAfterDot = fileName.lastIndexOf(".");
        System.out.format("The file is not an image (%s). Select an image to continue.\n",
                fileName.substring(positionAfterDot + 1));
        return false;
    }

    //deletando arquivos
    public static void delettingObjects(String bucket_name){
        System.out.println(" - removing objects from bucket");
        AmazonS3 s3 = s3WithCredentials();
        ObjectListing objectListing = s3.listObjects(bucket_name);
        while(true){
            Iterator<S3ObjectSummary> objecInterator = objectListing
                .getObjectSummaries().iterator();
            while (objecInterator.hasNext()){
                S3ObjectSummary summary = (S3ObjectSummary) objecInterator.next();
                s3.deleteObject(bucket_name,summary.getKey());
        }

            // more object_listing to retrieve?
            if (objectListing.isTruncated()) {
                objectListing = s3.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
    }
}
