FROM node:22-alpine AS build
WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
# Для сборки требуются переменные окружения
#COPY .env.development .env
ENV NEXT_PUBLIC_BACKEND_URL=""
ENV IMAGES_HOSTNAME=s3.localhost
ENV NEXT_PUBLIC_YANDEX_CAPTCHA_KEY=ysc1_QzDZ0lPsWE0ze2SBZ4E8Wmb6xBgcwCclq8IFhcfgb6a599ad
RUN npm run build

FROM node:22-alpine AS prod
WORKDIR /app
COPY --from=build /app/node_modules ./node_modules
COPY --from=build /app/package.json ./package.json
COPY --from=build /app/.next ./.next
COPY --from=build /app/public ./public

EXPOSE 3000
CMD ["npm", "start"]
