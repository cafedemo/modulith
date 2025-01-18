package tut.dushyant.modulith.cafe;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.core.ApplicationModules;

public class AppModularTests {
    ApplicationModules modules = ApplicationModules.of(CafeApplication.class);
    private final static Logger LOGGER = LoggerFactory.getLogger(AppModularTests.class);

    @Test
    void verify() {
        modules.forEach(module -> LOGGER.info("Module: {}", module.getDisplayName()));
        modules.verify();
    }

}
