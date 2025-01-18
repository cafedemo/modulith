package tut.dushyant.modulith.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Modulith(systemName = "Cafe", useFullyQualifiedModuleNames = true, sharedModules = "common")
@SpringBootApplication
public class CafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeApplication.class, args);
	}

}
