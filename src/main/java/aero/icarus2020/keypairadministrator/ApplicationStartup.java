package aero.icarus2020.keypairadministrator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${icarus.pae.url}")
    String paeURL;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

    }
}
