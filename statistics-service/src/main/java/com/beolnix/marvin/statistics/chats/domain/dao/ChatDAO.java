package com.beolnix.marvin.statistics.chats.domain.dao;


import com.beolnix.marvin.statistics.chats.domain.model.Chat;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by beolnix on 18/01/16.
 */
public interface ChatDAO extends PagingAndSortingRepository<Chat, String> {

    List<Chat> findByName(String name);
}
