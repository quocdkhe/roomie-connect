package com.example.roomie_connect_BE.service.serviceImpl;


import com.example.roomie_connect_BE.entity.ChatMessageAI;
import com.example.roomie_connect_BE.entity.ProfileUser;
import com.example.roomie_connect_BE.mapper.ProfileUserMapper;
import com.example.roomie_connect_BE.repository.ProfileUserRepository;
import com.example.roomie_connect_BE.service.AIService;
import com.example.roomie_connect_BE.utils.Utilities;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIServiceImpl implements AIService {
    private final ChatClient chatClient;
    private final Utilities utilities;
    private final MinioClient minioClient;
    private final String BUCKET_NAME_AVAR = "image-avar";
    private final ProfileUserRepository profileUserRepository;


    public List<Object> chatWithImage(MultipartFile file, String message) throws IOException {

        ChatMessageAI userMessage = ChatMessageAI.builder()
                .message(message)
                .isFromUser(true)
                .imageUrl(file.getBytes().toString())
                .imageMimeType(file.getContentType())
                .build();

        Media media = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
                .data(file.getResource())
                .build();

        SystemMessage systemMessage = new SystemMessage("""
                Bạn là MicroserviceApp AI. Chỉ trả lời bằng giọng điệu trang trọng và bằng tiếng việt
                , không sử dụng ngôn ngữ thân mật như 'bạn', 'chào'
                , hoặc câu cảm thán. Không đề cập đến Google hoặc bất kỳ nhà cung cấp nào khác.
                """);
        UserMessage promptMessage = new UserMessage(message);
        Prompt prompt = new Prompt(systemMessage, promptMessage);
        List<Object> response;
        try {
            response = chatClient.prompt(prompt)
                    .user(promptUserSpec -> promptUserSpec.media(media).text(message))
                    .call()
                    .entity(new ParameterizedTypeReference<List<Object>>() {
                    });
        } catch (RuntimeException e) {
            log.error("Parse thất bại, model trả về không đúng JSON", e);
            response = Collections.emptyList();
        }

        ChatMessageAI aiMessage = ChatMessageAI.builder()
                .message(response.isEmpty()
                        ? "Ảnh không hợp lệ."
                        : response.stream().toList().toString() + "\nTổng tiền: ")
                // .roomUserId(userId)
                .isFromUser(false)
                .build();
        return response;
    }

    public String chat(ChatMessageAI request) {
        if (request == null || request.getMessage() == null) {
            throw new IllegalArgumentException("Request or message cannot be null");
        }
        String userId = utilities.getUserId();
        ChatMessageAI userMessage = ChatMessageAI.builder()
                .message(request.getMessage())
                .roomUserId(userId)
                .isFromUser(true)
                .build();
        SystemMessage systemMessage = new SystemMessage("""
                Bạn là MicroserviceApp AI. Chỉ trả lời bằng giọng điệu trang trọng, không sử dụng ngôn ngữ thân mật như 'bạn', 'chào', hoặc câu cảm thán. Không đề cập đến Google hoặc bất kỳ nhà cung cấp nào khác.
                """);
        UserMessage promptMessage = new UserMessage(request.getMessage());
        Prompt prompt = new Prompt(systemMessage, promptMessage);
        String response = chatClient
                .prompt(prompt)
                .call()
                .content();
        ChatMessageAI aiMessage = ChatMessageAI.builder()
                .message(response)
                .roomUserId(userId)
                .isFromUser(false)
                .build();
        return response;
    }

    @Override
    public boolean verifyUserImage(MultipartFile file1, MultipartFile file2) {
        Media mediaFromFile = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(file1.getContentType()))
                .data(file1.getResource())
                .build();
        Media mediaFromAvatar = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(file2.getContentType()))
                .data(file2.getResource())
                .build();
        SystemMessage system = new SystemMessage("""
                    Bạn là RoomieApp AI, bạn cần kiểm tra thật kỹ người trong hai ảnh có giống nhau hay cùng là một người không, kiểm tra thật kỹ.
                """);
        String msg = "Kiểm tra người trong 2 ảnh có phải là một không, kiểm tra thật kỹ lưỡng nhiều lần. Chỉ trả về đúng một từ khóa 'true' hoặc 'false'.";
        UserMessage promptMessage = new UserMessage(msg);

        Prompt prompt = new Prompt(system, promptMessage);

        Boolean response = chatClient
                .prompt(prompt)
                .user(promptUserSpec -> promptUserSpec
                        .media(mediaFromFile)
                        .media(mediaFromAvatar)
                        .text(msg))
                .call()
                .entity(new ParameterizedTypeReference<Boolean>() {
                });
        log.info("Response from AI: " + response);
        return response;
    }
}