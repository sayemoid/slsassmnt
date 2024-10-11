package dev.sayem.selis.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BaseServiceV1<ENTITY extends BaseEntity> {

	default void applyValidations(ENTITY entity) {
		validations().forEach(validation -> {
			validation.apply(entity, entity.isNew() ? ValidationScope.WRITE : ValidationScope.MODIFY);
		});
	}

	Set<Validation<ENTITY>> validations();

	JpaRepository<ENTITY, Long> getRepository();
}
