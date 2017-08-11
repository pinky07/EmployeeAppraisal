package com.gft.employeeappraisal.converter;

/**
 * Interface that specifies a SOURCE object to be converted into a DESTINATION object.
 *
 * @author Manuel Yepez
 */
public interface EntityDTOConverter<S, D> {
	/**
	 * The inner implementations of this method should call Orika's BoundMapperFacade#map method.
	 * @param source Source object to be converted.
	 * @return Destination object, already processed.
	 */
	D convert(S source);

	/**
	 * The inner implementations of this method should call Orika's BoundMapperFacade#mapReverse method.
	 * @param source Source object to be converted.
	 * @return Destination object, already processed.
	 */
	S convertBack(D source);
}
