package by.bsu.recipebook.service;

import by.bsu.recipebook.dto.EmailDto;
import by.bsu.recipebook.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailMapper emailMapper;

    public void sendMessageToCompany(EmailDto emailDto) {
        emailMapper.mapToGoogleMail(emailDto).sendMessage();
    }
}
