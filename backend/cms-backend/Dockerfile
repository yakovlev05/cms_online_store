# Этап сборки
FROM gradle:jdk21-alpine AS build
WORKDIR /app


# Копируем файлы проекта и зависимости
COPY settings.gradle.kts .

# core
COPY core/build.gradle.kts core/build.gradle.kts

# microservice-auth
COPY microservice-auth/build.gradle.kts microservice-auth/build.gradle.kts
COPY microservice-auth/settings.gradle.kts microservice-auth/settings.gradle.kts

# microservice-user
COPY microservice-user/build.gradle.kts microservice-user/build.gradle.kts
COPY microservice-user/settings.gradle.kts microservice-user/settings.gradle.kts

# microservice-catalog
COPY microservice-catalog/build.gradle.kts microservice-catalog/build.gradle.kts
COPY microservice-catalog/settings.gradle.kts microservice-catalog/settings.gradle.kts

# microservice-cart
COPY microservice-cart/build.gradle.kts microservice-cart/build.gradle.kts
COPY microservice-cart/settings.gradle.kts microservice-cart/settings.gradle.kts

# microservice-notification
COPY microservice-notification/build.gradle.kts microservice-notification/build.gradle.kts
COPY microservice-notification/settings.gradle.kts microservice-notification/settings.gradle.kts

# RUN gradle build --no-daemon --info || return 0
RUN gradle build --no-daemon --info


# Копируем исходный код проекта и собираем его
# core
COPY core/src core/src

# microservice-auth
COPY microservice-auth/src microservice-auth/src

# microservice-user
COPY microservice-user/src microservice-user/src

# microservice-catalog
COPY microservice-catalog/src microservice-catalog/src

# microservice-cart
COPY microservice-cart/src microservice-cart/src

# microservice-notification
COPY microservice-notification/src microservice-notification/src

RUN gradle clean build -x test --no-daemon # --info


# Этап выполнения
FROM openjdk:21-slim AS microservice-auth
WORKDIR /app
# Копируем собранный JAR файл из этапа сборки
COPY --from=build /app/microservice-auth/build/libs/*-SNAPSHOT.jar app.jar
# Указываем команду запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
# Указываем порт, на котором работает приложение
EXPOSE 8080

FROM openjdk:21-slim AS microservice-user
WORKDIR /app
# Копируем собранный JAR файл из этапа сборки
COPY --from=build /app/microservice-user/build/libs/*-SNAPSHOT.jar app.jar
# Указываем команду запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
# Указываем порт, на котором работает приложение
EXPOSE 8080

FROM openjdk:21-slim AS microservice-catalog
WORKDIR /app
# Копируем собранный JAR файл из этапа сборки
COPY --from=build /app/microservice-catalog/build/libs/*-SNAPSHOT.jar app.jar
# Указываем команду запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
# Указываем порт, на котором работает приложение
EXPOSE 8080

FROM openjdk:21-slim AS microservice-cart
WORKDIR /app
# Копируем собранный JAR файл из этапа сборки
COPY --from=build /app/microservice-cart/build/libs/*-SNAPSHOT.jar app.jar
# Указываем команду запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
# Указываем порт, на котором работает приложение
EXPOSE 8080

FROM openjdk:21-slim AS microservice-notification
WORKDIR /app
# Копируем собранный JAR файл из этапа сборки
COPY --from=build /app/microservice-notification/build/libs/*-SNAPSHOT.jar app.jar
# Указываем команду запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
# Указываем порт, на котором работает приложение
EXPOSE 8080
