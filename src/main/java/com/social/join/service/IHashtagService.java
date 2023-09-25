package com.social.join.service;

import com.social.join.dtos.HashtagDTO;

import java.util.Optional;

public interface IHashtagService {
    /**
     * Get the hashtag searched by id
     * @param id    the hashtag's id
     * @return  {@link Optional} of {@link HashtagDTO} instance
     */
    Optional<HashtagDTO> getHashtagById(int id);

    /**
     * Create a hashtag inside the database
     * @param hashtagDTO    the hashtag instance to save
     * @return  the {@link HashtagDTO} instance with the assigned id from the database
     */
    HashtagDTO createHashtag(HashtagDTO hashtagDTO);

    /**
     * This updates the Hashtag in the database
     * @param id    the id of the hashtag to update
     * @param hashtagDTO    the new {@link HashtagDTO} instance to update the Hashtag
     * @return  the updated hashtag
     */
    Optional<HashtagDTO> updateHashtagById(int id, HashtagDTO hashtagDTO);

}
