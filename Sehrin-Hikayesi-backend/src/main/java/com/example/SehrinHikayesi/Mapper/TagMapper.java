package com.example.SehrinHikayesi.Mapper;

import com.example.SehrinHikayesi.DTO.Request.TagRequest;
import com.example.SehrinHikayesi.DTO.Response.TagResponse;
import com.example.SehrinHikayesi.Entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public Tag toEntity(TagRequest dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        return tag;
    }

    public TagResponse toResponse(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}
