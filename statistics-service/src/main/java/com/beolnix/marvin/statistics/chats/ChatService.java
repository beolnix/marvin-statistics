package com.beolnix.marvin.statistics.chats;

import com.beolnix.marvin.statistics.api.model.ChatDTO;
import com.beolnix.marvin.statistics.api.model.CreateChatDTO;
import com.beolnix.marvin.statistics.error.NotFound;
import com.beolnix.marvin.statistics.chats.domain.model.Chat;
import com.beolnix.marvin.statistics.chats.domain.dao.ChatDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChatService {
    private final ChatDAO chatDAO;
    private final static Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    public ChatService(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }

    public ChatDTO createChat(CreateChatDTO createChatDTO) {
        Chat chat = new Chat();
        BeanUtils.copyProperties(createChatDTO, chat);
        Chat savedChat = chatDAO.save(chat);

        return convert(savedChat);
    }

    public ChatDTO getChatById(String id) {
        Chat chat = chatDAO.findOne(id);
        if (chat != null) {
            return convert(chat);
        } else {
            throw new NotFound();
        }
    }

    public ChatDTO getChatByName(String name) {
        Chat chat = chatDAO.findByName(name).stream()
                .findFirst().orElseThrow(NotFound::new);
        return convert(chat);
    }

    public List<ChatDTO> getChats() {
        return StreamSupport.stream(chatDAO.findAll().spliterator(), false)
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private ChatDTO convert(Chat chat) {
        ChatDTO dto = new ChatDTO();
        BeanUtils.copyProperties(chat, dto);
        return dto;
    }


}
