package org.tonality.middleware;

public abstract class BaseMiddleware<T> {
    abstract public T execute(T entity);
}
