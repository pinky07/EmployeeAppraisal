package com.gft.employeeappraisal.builder;

/**
 * Interface that specifies a build() method to be implemented by object builders,
 * in accordance to the Builder Pattern.
 *
 * @author Manuel Yepez
 */
public interface ObjectBuilder<T> {

    /**
     * Returns an instance of the Object according to the setter methods called.
     *
     * @return New Object
     */
    T build();

    /**
     * Returns a mock of the Object that returns the values according to the setter methods called.
     *
     * @return New Mock
     */
    T buildMock();
}
