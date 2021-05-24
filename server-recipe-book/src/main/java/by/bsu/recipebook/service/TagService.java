package by.bsu.recipebook.service;

import by.bsu.recipebook.dto.TagDto;
import by.bsu.recipebook.entity.Tag;
import by.bsu.recipebook.mapper.TagMapper;
import by.bsu.recipebook.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    @Transactional(readOnly = true)
    public List<TagDto> getAll() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::mapToTagDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(TagDto tagDto) {
        Tag tag = tagRepository.findByTagName(tagDto.getTagName());
        tagRepository.save(tag);
    }
}
