package be.ac.ucl.ingi.rls.state;

public interface Trial {
    void update();
    boolean check();
    void revert();
    void commit();
}
