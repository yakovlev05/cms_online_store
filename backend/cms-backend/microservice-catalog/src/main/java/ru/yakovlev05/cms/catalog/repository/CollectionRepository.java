package ru.yakovlev05.cms.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.catalog.entity.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

}
