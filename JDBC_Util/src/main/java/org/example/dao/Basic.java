package org.example.dao;

import java.util.Set;

public interface Basic {
    public <T> int updateEntity(T t);
    public <T> int deleteEntity(T t);
    public <T> int insertEntity(T t);
    public <T> Set<T> queryEntity(T t);
}
