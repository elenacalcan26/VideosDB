package action;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

/**
 * Clasa contine comparatori pentru sortarea HashMap-urilor
 */
public final class Sorting {

    private Sorting() {
    }

    /**
     *
     * @param map HashMap nesortat
     * @return Map sortat in ordine crescatare a valorilor si lexicografica a cheilor
     */
    public static Map<String, Double> sortAsc(final HashMap<String, Double> map) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(map.entrySet());
        list.sort(Comparator.comparingDouble((ToDoubleFunction<Map.Entry<String, Double>>)
                Map.Entry::getValue).thenComparing(Map.Entry::getKey));
        HashMap<String, Double> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Double> x : list) {
            sorted.put(x.getKey(), x.getValue());
        }
        return sorted;
    }

    /**
     *
     * @param map HashMap-ul nesortat
     * @return Map sortat descresctaor dupa valoare
     */
    public static Map<String, Double> sortMapVal(final HashMap<String, Double> map) {
        List<Map.Entry<String, Double>> list = new
                LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
        HashMap<String, Double> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Double> x : list) {
            sorted.put(x.getKey(), x.getValue());
        }
        return sorted;
    }

    /**
     *
     * @param map HashMap nesortat
     * @return Map sortat descrescator dupa valoare si descrescator dupa cheie
     */
    public static Map<String, Double> sortDesc(final HashMap<String, Double> map) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> {
            int cmp = (o2.getValue()).compareTo(o1.getValue());
            if (cmp != 0) {
                return cmp;
            }
            return o2.getKey().compareTo(o1.getKey());
        });
        HashMap<String, Double> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Double> x : list) {
            sorted.put(x.getKey(), x.getValue());
        }
        return sorted;
    }
}
