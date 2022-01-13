package com.dioclass.s3withspring.ServiceBucket;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;
import static com.dioclass.s3withspring.Config.AmazonConfig.*;

public class BucketBasicOperations {
    /*vamos pensar sobre a teoria do S3.
     * Dentro da amazon temos os recursos bucket e objetos que estão disponibilizados pelo serviço S3.
     * Dessa forma, podemos ter acesso a estes recursos utilizando as APIs do serviço. Portanto, antes
     * de tentarmos listar, criar ou deletar recursos precisamos definir o serviço S3. Dessa forma,
     * as APIs estarão disponpíveis para utilização.
     * */

    public static Bucket getBucket(String bucketName) {
        final AmazonS3 s3 = s3WithCredentials();
        //pegamos a lista de buckets para selecionar o respectivo
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
<<<<<<< HEAD
        System.out.println("Teste de entrada na função\n");
        boolean doesExists = s3.doesBucketExistV2(bucketName);
        if (doesExists) {
            System.out.format("Bucket %s already exists.\n", bucketName);
=======
        //System.out.println("Teste de entrada na função\n");
        boolean doesExists = s3.doesBucketExistV2(bucketName);
        if (doesExists) {
            System.out.format("*****\nBucket %s already exists.\n*****\n", bucketName);
>>>>>>> Initial commit
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

<<<<<<< HEAD
    public static void deleteBucket(String nameBucket) {
        //primeiro remover objetos do Bucket (caso contrário não é possível deleta-lo)
    }

=======
>>>>>>> Initial commit
    public static void listOfBuckets(){
        final AmazonS3 s3 = s3WithCredentials();
        List<Bucket> bucketlist = s3.listBuckets();
        System.out.println("List of buckets on you aws account:");
        for(Bucket buckte :bucketlist){
            System.out.println("-> " + buckte.getName());
        }
    }

}
