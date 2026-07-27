package be.ac.ucl.ingi.defo.modeling;

public class DEFONode {
    public final int nodeId;
    public final String label;

    public DEFONode(int nodeId, String label) {
        this.nodeId = nodeId;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
