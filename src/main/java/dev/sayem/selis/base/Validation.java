package dev.sayem.selis.base;

public interface Validation<T> {
	void apply(T data, ValidationScope scope);
}

