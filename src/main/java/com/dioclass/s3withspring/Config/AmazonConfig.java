package com.dioclass.s3withspring.Config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    /*Neste classe iremos definir as credenciais de acesso ao serviço de
     * armazenamento da amazon - S3
     * */

    private static final String ACCESS_KEY = "accessKey";
    private static final String SECRET_KEY = "47/ePCULmAce/secretKey";

    @Bean
    public static AmazonS3 s3WithCredentials(){
        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.DEFAULT_REGION)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build()
                ;
    }
}
