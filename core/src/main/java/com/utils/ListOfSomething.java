package com.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListOfSomething<T> implements ParameterizedType {

    private Class<?> wrapped;

    public ListOfSomething(Class<T> wrapped) {
        this.wrapped = wrapped;
    }

    @NotNull
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{wrapped};
    }

    @NotNull
    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}

