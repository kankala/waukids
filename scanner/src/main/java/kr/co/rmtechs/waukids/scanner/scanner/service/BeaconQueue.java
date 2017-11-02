package kr.co.rmtechs.waukids.scanner.scanner.service;

public interface BeaconQueue<T> {
    void publish(T beacon);

    T take() throws InterruptedException;

    void clear();
}
