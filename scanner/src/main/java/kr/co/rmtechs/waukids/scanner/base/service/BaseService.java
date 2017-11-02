package kr.co.rmtechs.waukids.scanner.base.service;

import java.util.List;

public interface BaseService<T, V> {

    List<V> lists();

    V get(T queryId);

    void insert(V vo);
}
