package ru.yakovlev05.cms.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.catalog.entity.Media;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    Optional<Media> findByFileName(String fileName);
}
