package dataStructuresAndAlgorithms.HashMaps;

import java.util.ArrayList;

public class DenisNurmuhametovMap<K, V> implements DenisNurmuhametovMapADT<K, V> {
    ArrayList<DenisNurmuhametovPair<K, V>>[] denisTable;
    static final int DEFAULT_INITIAL_CAPACITY = 30;
    int tableSize;


    public DenisNurmuhametovMap() {
        this.tableSize = 0;
        this.denisTable = new ArrayList[DEFAULT_INITIAL_CAPACITY];
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            denisTable[i] = new ArrayList<>();
        }
    }


    public int hashCode(K key) {
        int length = key.toString().length();
        char start = key.toString().charAt(0);
        char end = key.toString().charAt(length - 1);
        return (int) start - (int) end + 'D' + 'e' + 'n' + 'N' - 'u' - 'v' - 'A';
    }


    @Override
    public V get(K key) {
        int currentHash = Math.abs(hashCode(key)) % DEFAULT_INITIAL_CAPACITY;
        for (DenisNurmuhametovPair<K, V> pair : denisTable[currentHash]) {
            if (pair.key.equals(key)) {
                return pair.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.tableSize;
    }

    @Override
    public void put(K key, V value) {
        int currentHash = Math.abs(hashCode(key)) % DEFAULT_INITIAL_CAPACITY;
        for (DenisNurmuhametovPair<K, V> pair : denisTable[currentHash]) {
            if (pair.key.equals(key)) {
                pair.value = value;
                return;
            }
        }
        this.denisTable[currentHash].add(new DenisNurmuhametovPair<K, V>(key, value));
        this.tableSize++;

    }

    @Override
    public void remove(K key) {
        int currentHash = Math.abs(hashCode(key)) % DEFAULT_INITIAL_CAPACITY;
        for (DenisNurmuhametovPair<K, V> pair : denisTable[currentHash]) {
            if (pair.key.equals(key)) {
                denisTable[currentHash].remove(pair);
                this.tableSize--;
                return;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.tableSize == 0;
    }


    public void denisListMap() {
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            for (DenisNurmuhametovPair<K, V> pair : denisTable[i]) {
                System.out.println(pair.key + ": " + pair.value);
            }
        }
    }

    public int[] values() {
        int[] values = new int[this.tableSize];
        int valuesIDX = 0;
        for (int i = 0; i < this.DEFAULT_INITIAL_CAPACITY; i++) {
            for (DenisNurmuhametovPair<K, V> pair : denisTable[i]) {
                if (pair.value != null) {
                    values[valuesIDX] = (int)pair.value;
                    valuesIDX++;
                }
            }
        }
        return values;
    }


    public String[] keys() {
        String[] keys = new String[this.tableSize];
        int keysIdx = 0;
        for (int i = 0; i < this.DEFAULT_INITIAL_CAPACITY; i++) {
            for (DenisNurmuhametovPair<K, V> pair : denisTable[i]) {
                if (pair.key != null) {
                    keys[keysIdx] = pair.key.toString();
                    keysIdx++;
                }
            }
        }
        return keys;
    }
}

