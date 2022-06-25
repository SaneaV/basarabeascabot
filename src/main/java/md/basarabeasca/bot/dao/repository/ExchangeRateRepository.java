package md.basarabeasca.bot.dao.repository;

import java.util.List;
import md.basarabeasca.bot.dao.repository.model.ExchangeRateJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateJpa, Long> {

  List<ExchangeRateJpa> findAllByOrderByIdAsc();

  @Modifying
  @Query(value = "UPDATE exchange_rates SET value = :exchangeRate WHERE currency = :currency", nativeQuery = true)
  void updateExchangeRate(String currency, String exchangeRate);
}
