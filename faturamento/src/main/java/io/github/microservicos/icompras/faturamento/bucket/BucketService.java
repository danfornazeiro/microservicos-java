package io.github.microservicos.icompras.faturamento.bucket;

import io.github.microservicos.icompras.faturamento.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BucketService {
    private final MinioClient minioClient;
    private final MinioProps minioProps;

    public void upload(BucketFile file) {
        try {
            var object = PutObjectArgs
                    .builder()
                    .bucket(minioProps.getBucketName())
                    .object(file.name())
                    .stream(file.is(), file.size(), -1)
                    .contentType(file.type().toString())
                    .build();

            minioClient.putObject(object);

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl(String fileName){
        try {
            var object = GetPresignedObjectUrlArgs
                    .builder()
                    .method(Method.GET)
                    .bucket(minioProps.getBucketName())
                    .object(fileName)
                    .expiry(7, TimeUnit.DAYS)
                    .build();

            return minioClient.getPresignedObjectUrl(object);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
