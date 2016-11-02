package com.beolnix.marvin.utils;

import com.beolnix.marvin.statistics.api.Constants;
import com.beolnix.marvin.statistics.api.model.ChatDTO;
import com.beolnix.marvin.statistics.api.model.CreateChatDTO;
import com.beolnix.marvin.statistics.chats.domain.dao.ChatDAO;
import com.beolnix.marvin.statistics.chats.domain.model.Chat;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by beolnix on 31/01/16.
 */
public class RestHelper {

    private final ChatDAO chatDAO;
    private final Integer port;
    private final String baseUrl;

    public RestHelper(ChatDAO chatDAO, Integer port) {
        this.chatDAO = chatDAO;
        this.port = port;
        this.baseUrl = "http://localhost:"+port+"/api/v1/";
    }

    public void testRepository(ChatDTO chatDTO) {
        Chat chat = chatDAO.findOne(chatDTO.getId());

        assertNotNull(chat);

        assertEquals(chatDTO.getName(), chat.getName());
        assertEquals(chatDTO.getProtocol(), chat.getProtocol());
        assertEquals(chatDTO.getConference(), chat.getConference());
    }

    public ChatDTO getChatByName(String name) {
        RestTemplate restTemplate = getRestTemplate();
        ResponseEntity<ChatDTO> result = restTemplate.getForEntity(baseUrl + "/chats/name/" + name, ChatDTO.class);
        return result.getBody();
    }

    public List<ChatDTO> getChats() {
        RestTemplate restTemplate = getRestTemplate();
        ResponseEntity<List<ChatDTO>> chatsResponse =
                restTemplate.exchange(baseUrl + "/chats", HttpMethod.GET, null, new ParameterizedTypeReference<List<ChatDTO>>() {
                });
        return chatsResponse.getBody();
    }


    public ChatDTO createChat(String chatName) {
        // given
        RestTemplate restTemplate = getRestTemplate();
        CreateChatDTO createChatDTO = new CreateChatDTO();
        createChatDTO.setConference(true);
        createChatDTO.setName(chatName);
        createChatDTO.setProtocol("SKYPE");

        // when
        ResponseEntity<ChatDTO> result = restTemplate.postForEntity(baseUrl + "/chats", createChatDTO, ChatDTO.class);
        ChatDTO chatDTO = result.getBody();

        // then
        assertNotNull(chatDTO);
        assertNotNull(chatDTO.getId());
        assertEquals(createChatDTO.getConference(), chatDTO.getConference());
        assertEquals(createChatDTO.getName(), chatDTO.getName());
        assertEquals(createChatDTO.getProtocol(), chatDTO.getProtocol());

        testRepository(chatDTO);

        return chatDTO;
    }

    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(getWriteHeaders());
        return restTemplate;
    }

    public List<ClientHttpRequestInterceptor> getWriteHeaders() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                HttpRequest wrapper = new HttpRequestWrapper(request);
                wrapper.getHeaders().set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                wrapper.getHeaders().set("Accept", MediaType.APPLICATION_JSON_VALUE);
                wrapper.getHeaders().set(Constants.API_KEY_HEADER, "test_write_key");
                wrapper.getHeaders().set(Constants.API_SECRET_HEADER, "test_write_auth");
                return execution.execute(wrapper, body);
            }
        });
        return interceptors;
    }
}
