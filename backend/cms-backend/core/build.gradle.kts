plugins {
    id("java")
}

group = "ru.yakovlev05.cms.core"
version = "unspecified"

repositories {
    mavenCentral()
}

val lombokVersion = "1.18.34"

dependencies {
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
}