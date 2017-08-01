package com.gft.employeeappraisal.converter;

/**
 * Interface that specifies a SOURCE object to be converted into a DESTINATION object.
 *
 * @author Manuel Yepez
 */
public interface Mapper<S, D> {
	/**
	 * The inner implementations of this method should call Orika's MapperFacade#map method,
	 * after providing it with a proper classMap.
	 * @param source Source object to be converted.
	 * @return Destination object, already processed.
	 */
	D map(S source);
}
