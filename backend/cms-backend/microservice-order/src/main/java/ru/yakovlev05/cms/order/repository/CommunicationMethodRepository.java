package ru.yakovlev05.cms.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yakovlev05.cms.order.entity.CommunicationMethod;

public interface CommunicationMethodRepository extends JpaRepository<CommunicationMethod, Long> {
}
