package org.msh.etbm.commons.commands;

/**
 * Interface that must be implemented by any command that wants to register its execution
 * in the command history.
 * Created by rmemoria on 17/10/15.
 */
@FunctionalInterface
public interface CommandLogHandler<E, R> {
    void prepareLog(CommandHistoryInput in, E request, R response);
}
