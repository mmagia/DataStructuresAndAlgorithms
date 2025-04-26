package dataStructuresAndAlgorithms.HashMaps;

public class DenisNurmuhametovPair <K, V>{
    K key;
    V value;
    public int occurence;
    public static int inc = 0;
    public DenisNurmuhametovPair(K key, V value) {
        this.value = value;
        this.key = key;
        this.occurence = inc;
        inc++;
    }

    public DenisNurmuhametovPair() {
        this.value = null;
        this.key = null;
    }


}
