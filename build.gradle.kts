plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.flywaydb.flyway") version "11.0.0"
}

group = "tut.dushyant.modulith"
version = "1.0"

repositories {
	mavenCentral()
	mavenLocal()
	maven {
		url = uri("https://repo.spring.io/release")
	}
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("org.springframework.modulith:spring-modulith-starter-core")
	implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
	implementation("org.springframework.modulith:spring-modulith-events-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-validation")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.springframework.boot:spring-boot-starter-actuator")
	runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
	runtimeOnly("org.springframework.modulith:spring-modulith-observability")
	runtimeOnly("org.springframework.modulith:spring-modulith-starter-insight")
	runtimeOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")
	runtimeOnly("io.micrometer:micrometer-tracing-bridge-otel")
	runtimeOnly("io.opentelemetry:opentelemetry-exporter-otlp")
	runtimeOnly("org.flywaydb:flyway-database-postgresql:${gradle.extra["flywayVersion"]}")
	runtimeOnly("org.springframework.modulith:spring-modulith-runtime")
	runtimeOnly("org.springframework.modulith:spring-modulith-starter-insight")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.modulith:spring-modulith-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.modulith:spring-modulith-bom:${gradle.extra["springModulithVersion"]}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}