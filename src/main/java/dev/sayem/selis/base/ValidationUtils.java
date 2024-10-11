package dev.sayem.selis.base;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class ValidationUtils {

	public static <T> Validation<T> genericValidation(
			String message,
			Set<ValidationScope> scopes,
			RuntimeException exception,
			Predicate<T> valid
	) {
		return (data, scope) -> {
			if (scopes.contains(scope) && !valid.test(data)) {
				if (exception != null) {
					throw exception;
				} else {
					throw new RuntimeException(message != null ? message : data + " is invalid");
				}
			}
		};
	}


	public static <T extends BaseEntity> Boolean validateUniqueOperation(T exEntity, T newEntity) {
		if (exEntity == null) {
			return true;
		} else {
			if (newEntity.isNew()) {
				return false;
			} else {
				return Objects.equals(exEntity.getId(), newEntity.getId());
			}
		}
	}
}
