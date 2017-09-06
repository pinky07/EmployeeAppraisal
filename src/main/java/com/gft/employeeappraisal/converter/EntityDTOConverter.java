package com.gft.employeeappraisal.converter;

import ma.glasnost.orika.BoundMapperFacade;

/**
 * Interface that specifies a SOURCE object to be converted into a DESTINATION object.
 *
 * @author Manuel Yepez
 */
public abstract class EntityDTOConverter<S, D> {

    protected BoundMapperFacade<S, D> boundMapper;

    /**
     * The inner implementations of this method should call Orika's BoundMapperFacade#map method.
     *
     * @param source Source object to be converted.
     * @return Destination object, already processed.
     */
    public D convert(S source) {
        return boundMapper.map(source);
    }

    /**
     * The inner implementations of this method should call Orika's BoundMapperFacade#mapReverse method.
     *
     * @param source Source object to be converted.
     * @return Destination object, already processed.
     */
    public S convertBack(D source) {
        return boundMapper.mapReverse(source);
    }
}
