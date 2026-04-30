package io.github.microservicos.icompras.faturamento.api;

import io.github.microservicos.icompras.faturamento.bucket.BucketFile;
import io.github.microservicos.icompras.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {
    private final BucketService bucketService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            MediaType mediaType = MediaType.parseMediaType(file.getContentType());
            var bucketFile = new BucketFile(file.getOriginalFilename(), inputStream, mediaType, file.getSize());
            bucketService.upload(bucketFile);
            return ResponseEntity.ok("File uploaded");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar o arquivo: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam String filename) {
        try {
            String url = bucketService.getUrl(filename);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter url: " + e.getMessage());
        }
    }
}
