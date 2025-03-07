import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "ru.yakovlev05.cms.core"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val jjwtVersion = "0.12.6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
	implementation("org.springframework.boot:spring-boot-starter-web") // jwt
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion") //jwt
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion") // jwt
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<BootJar> { // отключение создания исполняемого jar
	enabled = false
}