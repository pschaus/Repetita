package be.ac.ucl.ingi.defo.core.variables;

import be.ac.ucl.ingi.defo.constraints.PathConstraint;

public class PathEventListener {
    public final PathEventListener next;
    public final PathConstraint constraint;
    public final int priority;

    public PathEventListener(PathEventListener next, PathConstraint constraint, int priority) {
        this.next = next;
        this.constraint = constraint;
        this.priority = priority;
    }

    public boolean hasNext() {
        return next != null;
    }

    @Override
    public String toString() {
        return "PathEvent(constraint: " + constraint + ")";
    }
}
