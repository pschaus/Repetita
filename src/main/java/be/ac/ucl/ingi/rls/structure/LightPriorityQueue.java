package be.ac.ucl.ingi.rls.structure;

public interface LightPriorityQueue<V> {
    V head();
    V dequeue();
    void enqueue(int key, V value);
    boolean remove(int key, V value);
    void removeAll();
    boolean changeKey(int oldKey, int newKey, V value);
    int size();
    boolean isEmpty();
}
