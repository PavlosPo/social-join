package com.social.join.service;

import com.social.join.dtos.HashtagDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class HashtagServiceImpl implements IHashtagService {
    @Override
    public Optional<HashtagDTO> getHashtagById(int id) {
        return Optional.empty();
    }

    @Override
    public HashtagDTO createHashtag(HashtagDTO hashtagDTO) {
        return null;
    }

    @Override
    public Optional<HashtagDTO> updateHashtagById(int id, HashtagDTO hashtagDTO) {
        return Optional.empty();
    }
}
