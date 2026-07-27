package be.ac.ucl.ingi.rls.core;

import be.ac.ucl.ingi.rls.ShortestPaths;
import be.ac.ucl.ingi.rls.io.TopologyData;

public interface DelayData {
    int delay(int source, int destination);
}
