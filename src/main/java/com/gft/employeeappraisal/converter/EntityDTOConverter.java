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
     * Maps a source object to its destination object using a provided destination object.
     * @param source Source object to be converted.
     * @param destination Already existing destination object
     * @return The destination object updated with the source object values.
     */
    public D convert(S source, D destination){
        return this.boundMapper.map(source, destination);
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

    /**
     * Maps a destination object to its source object using a provided source object.
     * @param destination Destination object to be converted.
     * @param source Already existing source object
     * @return The source object updated with the destination object values.
     */
    public S convertBack(D destination, S source){
        return this.boundMapper.mapReverse(destination, source);
    }
}
