package com.beolnix.marvin.statistics.chats;

import com.beolnix.marvin.statistics.api.model.ChatDTO;
import com.beolnix.marvin.statistics.chats.domain.model.Chat;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ChatConverter {
    public ChatDTO convert(Chat chat) {
        ChatDTO dto = new ChatDTO();
        BeanUtils.copyProperties(chat, dto);
        return dto;
    }
}
