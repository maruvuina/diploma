package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.TagDto;
import by.bsu.recipebook.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mapping(target = "tagName", source = "tag.tagName")
    TagDto mapToTagDto(Tag tag);

    @Mapping(target = "tagName", source = "tagDto.tagName")
    Tag mapToTag(TagDto tagDto);
}
