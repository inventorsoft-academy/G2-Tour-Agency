package co.inventorsoft.touragency.model;

/**
 * The {@code BaseEntity} interface is a generalizing interface for objects of the entity type.
 * The main feature of these entities is their ability to set identifier of {@code int} type
 * and retrieve these identifiers.
 * */
public interface BaseEntity {
    /**
     * Allows an invoking method receive object's unique ID
     * @return object's id
     * */
    int getId();

    /**
     * Sets an ID value for an object.
     * <strong>This method must be effectively called only once to prevent collisions and
     * dao loss</strong>
     * @param id integer value that will be assigned as object's ID
     * */
    void setId(int id);
}
