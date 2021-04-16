package by.bsu.recipebook.service.email;

import by.bsu.recipebook.dto.EmailDto;
import by.bsu.recipebook.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GmailService {
    private final EmailMapper emailMapper;

    public void sendMessageToCompany(EmailDto emailDto) {
        emailMapper.mapToGoogleMail(emailDto).sendMessage();
    }
}
