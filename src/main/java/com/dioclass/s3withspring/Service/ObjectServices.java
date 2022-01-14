package com.dioclass.s3withspring.Service;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import static com.dioclass.s3withspring.Config.AmazonConfig.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class ObjectServices {

    public static void listObjects(String bucketName){
        AmazonS3 s3 = s3WithCredentials();
        ListObjectsV2Result result = s3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
        }
    }

    public static boolean uploadObject(String bucketName, String filePath) {
        filePath = "/home/jm/Pictures/"+filePath;
        String key_name = Paths.get(filePath).getFileName().toString();
        System.out.format("Uploading %s to S3 bucket %s...\n", filePath, bucketName);
        if (fileVerificationType(filePath)) {
            final AmazonS3 s3 = s3WithCredentials();
            try {
                PutObjectResult s = s3.putObject(bucketName, key_name, new File(filePath));
            } catch (Exception e) {
                //System.err.println(e.getErrorMessage());
                System.out.println("Error: " + e.getMessage());
                System.exit(1);
                return false;
            }
            System.out.println("Done!");
            return true;
        }else
            System.out.println("Verification file Failure: the file is not an image");
        return false;
    }

    public static void uploadObjectWithMetadata(String bucketName, String filePath,
                                                String title, String description) {
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
                metadata.addUserMetadata(title, description);
                request.setMetadata(metadata);
                s3.putObject(request);
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
            System.out.println("**** Sucessfull upload! \n" +
                    "Your image with metadata is saved in the S3 storage. ****");
        }else
            System.out.println("Verification file Failure: the file is not an image");

    }

    private static boolean fileVerificationType(String filePath) {
        String fileName = new File(filePath).getName();

        if (fileName.contains(".png") || fileName.contains(".jpeg")
                || fileName.contains(".jpg")) {
            System.out.println("The file is an image, the upload will continue.\n");
            return true;
        }
        int positionAfterDot = fileName.lastIndexOf(".");
        System.out.format("The file is not an image (%s). Select an image to continue.\n",
                fileName.substring(positionAfterDot + 1));
        System.out.println("You can upload this types of files: png, jpg and jpeg.");
        return false;
 }
}
