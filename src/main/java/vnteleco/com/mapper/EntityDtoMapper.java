package vnteleco.com.mapper;

import java.util.Collection;

public interface EntityDtoMapper<E, T> {
    /**
     * Transforms an object to a destination object.
     *
     * @param entity The entity will be transformed.
     * @return The target object.
     */
    T transform(E entity);

    /**
     * Transforms an object to a destination object.
     *
     * @param model The entity will be transformed.
     * @return The target object.
     */
    E transformReverse(T model);

    /**
     * Transforms a list of object to a list of target object.
     *
     * @param entities The collection of source object.
     * @return The collection of target object.
     */
    Collection<T> transform(Collection<E> entities);

    /**
     * Transforms a list of object to a list of target object.
     *
     * @param models The entity will be transformed.
     * @return The target object.
     */
    Collection<E> transformReverse(Collection<T> models);

}
