package md.basarabeasca.bot.feature.hotnumbers.service.impl;

import lombok.RequiredArgsConstructor;
import md.basarabeasca.bot.feature.hotnumbers.model.PhoneNumber;
import md.basarabeasca.bot.feature.hotnumbers.repository.PhoneNumberRepository;
import md.basarabeasca.bot.feature.hotnumbers.dto.PhoneNumberDto;
import md.basarabeasca.bot.feature.hotnumbers.service.PhoneNumberService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private static final String NUMBER_WAS_ADDED = "Номер был добавлен";
    private static final String NUMBER_WAS_DELETED = "Номер был удалён";
    private static final String ERROR = "Произошла ошибка при отправлении сообщения. Пожалуйста, обратитесь к @SaneaV";

    private final PhoneNumberRepository phoneNumberRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PhoneNumberDto> getNextPage(Long lastId) {
        return phoneNumberRepository.getNextPage(lastId).stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    public List<PhoneNumberDto> getPreviousPage(Long lastId) {
        return phoneNumberRepository.getPreviousPage(lastId).stream()
                .map(this::convertToDTO)
                .collect(toList());
    }

    @Override
    public String addNumber(String number, String description) {
        final PhoneNumber phoneNumber = PhoneNumber.builder()
                .phoneNumber(number)
                .description(description)
                .build();

        try {
            phoneNumberRepository.save(phoneNumber);
            return NUMBER_WAS_ADDED;
        } catch (Exception exception) {
            return ERROR;
        }
    }

    @Override
    public String deleteNumber(String number) {
        try {
            phoneNumberRepository.delete(phoneNumberRepository.findByPhoneNumber(number));
            return NUMBER_WAS_DELETED;
        } catch (Exception exception) {
            return ERROR;
        }
    }

    @Override
    public List<PhoneNumberDto> findByDescription(String description) {
        try {
            return phoneNumberRepository.findByDescriptionIgnoreCaseContaining(description)
                    .stream()
                    .map(this::convertToDTO)
                    .sorted(Comparator.comparing(PhoneNumberDto::getDescription))
                    .collect(toList());
        } catch (Exception exception) {
            return null;
        }
    }

    public PhoneNumberDto convertToDTO(PhoneNumber phoneNumber) {
        return modelMapper.map(phoneNumber, PhoneNumberDto.class);
    }
}
