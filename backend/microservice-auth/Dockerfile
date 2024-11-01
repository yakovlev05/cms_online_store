# Этап сборки
FROM gradle:jdk21-alpine AS build
WORKDIR /app

# Копируем файлы проекта и зависимости
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN gradle build --no-daemon --info || return 0

# Копируем исходный код проекта и собираем его
COPY src ./src
RUN gradle clean build -x test --no-daemon # --info

# Этап выполнения
FROM openjdk:21-slim
WORKDIR /app

# Копируем собранный JAR файл из этапа сборки
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# Указываем команду запуска
ENTRYPOINT ["java", "-jar", "app.jar"]

# Указываем порт, на котором работает приложение
EXPOSE 8080
