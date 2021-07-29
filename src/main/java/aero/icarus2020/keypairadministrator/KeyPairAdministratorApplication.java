package aero.icarus2020.keypairadministrator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class KeyPairAdministratorApplication {

  private static final Logger logger = LogManager.getLogger(KeyPairAdministratorApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(KeyPairAdministratorApplication.class, args);
  }

  @Value("${app.settings.timezone}")
  private String timezone;

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timezone));
    logger.info("Starting in timezone " + timezone + " (" + new Date() + ")");
  }

}
