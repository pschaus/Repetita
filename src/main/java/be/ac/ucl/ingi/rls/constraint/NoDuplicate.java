package be.ac.ucl.ingi.rls.constraint;

import be.ac.ucl.ingi.rls.state.PathState;
import be.ac.ucl.ingi.rls.state.Trial;

public class NoDuplicate implements Trial {
    private final PathState pathState;

    public NoDuplicate(PathState pathState) {
        this.pathState = pathState;
    }

    @Override
    public boolean check() {
        int[] changed = pathState.changed;
        int pChanged = pathState.nChanged();

        while (pChanged > 0) {
            pChanged--;
            int demand = changed[pChanged];
            int[] path = pathState.path(demand);
            int p = pathState.size(demand);
            while (p > 0) {
                p--;
                int q = p;
                while (q > 0) {
                    q--;
                    if (path[p] == path[q]) return false;
                }
            }
        }
        return true;
    }

    @Override public void revert() {}
    @Override public void commit() {}
    @Override public void update() {}
}
