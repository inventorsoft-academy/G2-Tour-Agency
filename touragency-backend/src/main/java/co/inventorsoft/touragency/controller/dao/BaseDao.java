package co.inventorsoft.touragency.controller.dao;

import co.inventorsoft.touragency.model.BaseEntity;

import java.util.List;

/**
 * Interface {@code BaseDao<T>} is a generalized interface
 * */
public interface BaseDao<T extends BaseEntity> {

    List<T> getAll();
    boolean saveAll(List<T> data);

    default void assignIdentifiers(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setId(i + 1);
        }
    }
}
