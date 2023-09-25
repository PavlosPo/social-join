package com.social.join.service;

import com.social.join.dtos.HashtagDTO;

import java.util.Optional;

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
