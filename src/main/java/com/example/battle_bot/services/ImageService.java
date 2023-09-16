package com.example.battle_bot.services;

import com.example.battle_bot.exceptions.UploadFileException;
import com.example.battle_bot.models.BinaryContentEntity;
import com.example.battle_bot.models.ImageEntity;
import com.example.battle_bot.models.Value;
import com.example.battle_bot.repositories.BinaryContentRepository;
import com.example.battle_bot.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final BinaryContentRepository binaryContentRepository;
    private final ImageRepository imageRepository;

    public ImageEntity savePhoto(Message telegramMessage) throws TelegramApiException, IOException, URISyntaxException {
        var photoSizeCount = telegramMessage.getPhoto().size();
        var photoIndex = photoSizeCount > 1 ? telegramMessage.getPhoto().size() - 1 : 0;
        PhotoSize telegramPhoto = telegramMessage.getPhoto().get(photoIndex);
        String fileId = telegramPhoto.getFileId();
        ResponseEntity<String> response = getFilePath(fileId);

        if (response.getStatusCode() == HttpStatus.OK) {
            BinaryContentEntity persistentBinaryContentEntity = getPersistentBinaryContent(response);
            ImageEntity transientAppPhoto = buildTransientAppPhoto(telegramPhoto, persistentBinaryContentEntity);
            return imageRepository.save(transientAppPhoto);
        } else {
            throw new UploadFileException("Bad response from telegram service: " + response);
        }
    }

    private byte[] downloadFile(String filePath) {
        String fullUri = Value.FILE_STORAGE_URI.replace("{token}", Value.BOT_TOKEN)
                .replace("{filePath}", filePath);
        URL urlObj = null;
        try {
            urlObj = new URL(fullUri);
        } catch (MalformedURLException e) {
            throw new UploadFileException(e);
        }
        //TODO подумать над оптимизацией
        try (InputStream is = urlObj.openStream()) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new UploadFileException(urlObj.toExternalForm(), e);
        }
    }

    private BinaryContentEntity getPersistentBinaryContent(ResponseEntity<String> response) throws TelegramApiException, IOException, URISyntaxException {
        String filePath = getFilePath(response);
        byte[] fileInBytes = downloadFile(filePath);
        BinaryContentEntity binaryContentEntity = BinaryContentEntity.builder()
                .fileAsArrayOfBytes(fileInBytes)
                .build();
        return binaryContentRepository.save(binaryContentEntity);
    }

    public BinaryContentEntity getTheLastBinaryContextEntity() {
        long id = binaryContentRepository.findAll().stream()
                .mapToLong(BinaryContentEntity::getId)
                .max()
                .orElse(0);
        return binaryContentRepository.findById(id).orElseThrow();
    }


    private String getFilePath(ResponseEntity<String> response) {
        JSONObject jsonObject = new JSONObject(response.getBody());
        return String.valueOf(jsonObject
                .getJSONObject("result")
                .getString("file_path"));
    }

    private ImageEntity buildTransientAppPhoto(PhotoSize telegramPhoto, BinaryContentEntity persistentBinaryContent) {
        return ImageEntity.builder()
                .telegramFileId(telegramPhoto.getFileId())
                .binaryContentEntity(persistentBinaryContent)
                .fileSize(telegramPhoto.getFileSize())
                .build();
    }

    private ResponseEntity<String> getFilePath(String fileId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                Value.FILE_INFO_URI,
                HttpMethod.GET,
                request,
                String.class,
                Value.BOT_TOKEN, fileId
        );
    }
}

