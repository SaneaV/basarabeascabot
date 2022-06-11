package md.basarabeasca.bot.web.converter;

import md.basarabeasca.bot.domain.repository.model.PhoneNumberJpa;
import md.basarabeasca.bot.web.dto.PhoneNumberDto;

public interface PhoneNumberMapper {

  PhoneNumberDto toDto(PhoneNumberJpa phoneNumberJpa);
}
