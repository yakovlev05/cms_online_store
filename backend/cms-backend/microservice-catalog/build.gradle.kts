plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "ru.yakovlev05.cms.catalog"
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
val swaggerVersion = "2.6.0"
val transliterator = "76.1"
val s3AmazonSdk = "2.29.17"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion") // jwt
    implementation("com.ibm.icu:icu4j:$transliterator") // transliterator
    implementation(project(":core"))
    implementation("org.projectlombok:lombok")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${swaggerVersion}")
    implementation("software.amazon.awssdk:s3:$s3AmazonSdk") // s3 amazon SDK
    runtimeOnly("org.postgresql:postgresql")
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

springBoot {
    mainClass.set("ru.yakovlev05.cms.catalog.MicroserviceCatalogApplication")
}