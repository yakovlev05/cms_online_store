# Проблемы / тонкости работы с MinIO

*[MinIO](https://min.io/) - это S3 хранилище*

1. [Не создаётся bucket](#не-создаётся-bucket)

## Не создаётся bucket 🤕

Вся проблема связана с url адресами.  
В качестве примера пусть s3 хранилище расположено и доступно по адресу ```s3.yakovlev05.ru```

### Как работает AWS S3 SDK (соответственно оригинальное хранилище от Amazon)

Url при взаимодействии с bucket выглядит так: ```<название bucket>.s3.yakovlev05.ru```
То есть, если имя backet = *images*, то url: ```images.s3.yakovlev05.ru```  
Стиль: *Virtual-Host-Style*

### Как по умолчанию работает MinIO

В моём случае я запускал MinIO в docker и указывал всего две переменные окружения:

- ```MINIO_ROOT_USER```
- ```MINIO_ROOT_PASSWORD```

При таком варианте конфигурации MinIO используется стиль *Path-Style*  
То есть если bucket = *images*, то обращение к нему будет по пути ```s3.yakovlev05.ru/images```

Чтобы в MinIO использовать *Virtual-Host-Style*, то необходимо задать
переменную окружения ```MINIO_DOMAIN``` (например: yakovlev05.ru или s3.localhost)

Если вы хотите использовать *Path-Style*, то в *S3Client* добавьте конфигурацию:
```
.serviceConfiguration(S3Configuration.builder()
    .pathStyleAccessEnabled(true) // Включить Path-Style
    .build())
```

### Ссылка на файл (при условии что bucket публичен для чтения)
- bucket = images
- файл = cat.jpg

`http://localhost:9000/images/cat.jpg`

`http://images.s3.localhost:9000/cat.png`
