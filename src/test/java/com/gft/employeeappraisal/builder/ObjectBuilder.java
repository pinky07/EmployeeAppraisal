package com.gft.employeeappraisal.builder;

/**
 * Interface that specifies a build() method to be implemented by object builders,
 * in accordance to the Builder Pattern.
 *
 * @author Manuel Yepez
 * @author Rubén Jiménez
 */
public interface ObjectBuilder<T> {

    /**
     * Returns an instance of the Object according to the setter methods called.
     *
     * @return New Object
     */
    T build();

    /**
     * Returns an instance of the Object and sets default values for fields, if the field wasn't set.
     *
     * @return New Object
     */
    T buildWithDefaults();
}
