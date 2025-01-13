FROM node:22-alpine AS build
WORKDIR /app
COPY package.json /app/package.json
RUN npm install
COPY . /app
ENV VITE_API_BASE_URL=""
RUN npm run build

FROM nginx:stable AS prod
COPY --from=build /app/dist /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
