package dev.sayem.selis.exceptions;

import dev.sayem.selis.base.BaseEntity;

public class NonExistentException extends RuntimeException {
	public NonExistentException(String message) {
		super(message);
	}

	public NonExistentException(Class<? extends BaseEntity> cls) {
		super(cls.getName() + "was not found");
	}
}
