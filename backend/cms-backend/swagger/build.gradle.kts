plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "ru.yakovlev05.cms.swagger"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val swaggerVersion = "2.6.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${swaggerVersion}")
	implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.withType<Test> {
	useJUnitPlatform()
}


springBoot{
	mainClass.set("ru.yakovlev05.cms.swagger.SwaggerApplication")
}