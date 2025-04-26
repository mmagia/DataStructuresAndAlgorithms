package dataStructuresAndAlgorithms.HashMaps;

public interface DenisNurmuhametovMapADT<K, V> {
    V get(K key);

    int size();

    void put(K key, V value);

    void remove(K key);

    boolean isEmpty();
}
