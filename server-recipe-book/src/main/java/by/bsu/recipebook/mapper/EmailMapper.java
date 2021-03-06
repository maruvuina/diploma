package by.bsu.recipebook.mapper;

import by.bsu.recipebook.dto.EmailDto;
import by.bsu.recipebook.email.GoogleMail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    @Mappings({
            @Mapping(target = "fromEmail", source = "emailDto.fromEmail"),
            @Mapping(target = "title", source = "emailDto.title"),
            @Mapping(target = "message", source = "emailDto.message")
    })
    GoogleMail mapToGoogleMail(EmailDto emailDto);
}
