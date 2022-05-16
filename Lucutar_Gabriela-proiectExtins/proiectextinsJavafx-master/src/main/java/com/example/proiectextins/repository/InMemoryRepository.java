package com.example.proiectextins.repository;

import com.example.proiectextins.domain.Entity;


import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    Map<ID, E> entities;

    public InMemoryRepository() {
        entities = new HashMap<ID, E>();
    }

    @Override
    public E findOne(ID id) {
        if(id==null)
            throw new IllegalArgumentException("The username cannot be null");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) {
        if(entity==null)
            throw new IllegalArgumentException("The entity cannot be null");
        entities.put(entity.getID(), entity);
        return entity;
    }

    @Override
    public E delete(ID id) {
        entities.remove(id);
        return null;
    }

    @Override
    public E update(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null!");

        entities.put(entity.getID(), entity);

        if (entities.get(entity.getID()) != null) {
            entities.put(entity.getID(), entity);
            return null;
        }
        return entity;
    }

}
