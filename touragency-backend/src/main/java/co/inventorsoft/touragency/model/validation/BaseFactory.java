package co.inventorsoft.touragency.model.validation;

import co.inventorsoft.touragency.model.BaseEntity;

public interface BaseFactory<T extends BaseEntity> {
    T create();
}
