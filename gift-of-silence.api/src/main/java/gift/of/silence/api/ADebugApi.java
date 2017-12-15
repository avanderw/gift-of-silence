package gift.of.silence.api;

public interface ADebugApi {
    void trace();
    void debug();
    void warning();
    void error();
    void off();
    void register();
    void connections();
    void disconnect();
}
