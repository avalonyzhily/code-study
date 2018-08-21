package com.avalony.executor;

import com.avalony.config.MappedStatement;

import java.util.List;

public interface Executor {
    <T> List<T> execute(MappedStatement ms, Object args);
}
