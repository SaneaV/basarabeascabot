package md.basarabeasca.bot.service.impl;

import static md.basarabeasca.bot.util.InputStreamReader.readFromInputStream;

import java.io.IOException;
import java.io.InputStream;
import md.basarabeasca.bot.service.BankHoursService;
import org.springframework.stereotype.Component;

@Component
public class BankHoursServiceImpl implements BankHoursService {

  private static final String bankHoursFile = "BankHours.txt";

  @Override
  public String getBankHours() {
    final ClassLoader classLoader = getClass().getClassLoader();
    final InputStream inputStream = classLoader.getResourceAsStream(bankHoursFile);
    try {
      return readFromInputStream(inputStream);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
