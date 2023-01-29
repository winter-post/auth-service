package com.devwinter.authservice.config.security.converters;

public interface ProviderUserConverter<T, R> {
    R converter(T t);
}
