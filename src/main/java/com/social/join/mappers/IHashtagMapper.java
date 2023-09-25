package com.social.join.mappers;

import com.social.join.dtos.HashtagDTO;
import com.social.join.entities.Hashtag;
import org.mapstruct.Mapper;

@Mapper
public interface IHashtagMapper {

    Hashtag hashtagDTOToHashtag(HashtagDTO hashtagDTO);
    HashtagDTO hashtagToHashtagDTO(Hashtag hashtag);
}
