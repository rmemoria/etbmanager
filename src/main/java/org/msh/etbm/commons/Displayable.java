package org.msh.etbm.commons;

/**
 * Interface that every object must implement in order to expose a method
 * to display its content to the final user
 * <p>
 * Created by rmemoria on 26/10/15.
 */
@FunctionalInterface
public interface Displayable {

    /**
     * The displayable representation of the object
     *
     * @return
     */
    String getDisplayString();
}
