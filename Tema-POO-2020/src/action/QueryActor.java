package action;

import actor.Actor;
import database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class QueryActor {

    private QueryActor() {
    }

    /**
     * Awards Query:
     * Returneaza actorii cu premiile mentionate din input, acestia fiind salvati inainte in
     * hashmap-ul awardedActors din Database. Actorii sunt sortati dupa media rating-urilor
     * show-urilor in care apar.
     *
     * @param data obiect de tip Database
     * @param sortType tipul sortarii
     * @return actorii sortati dupa premiile mentionate
     */
    public static String awardsActors(final Database data, final String sortType) {
        if (data.getAwardedActors().isEmpty()) {
            // nici un actor nu a primit premiile mentionate
            return "Query result: []";
        }
        Map<String, Double> sorted;
        // sorteaza awardedActor din Database in functie de tip, ascendent sau descendent
        if (sortType.equals("asc")) {
            sorted = Sorting.sortAsc(data.getAwardedActors());
        } else {
            sorted = Sorting.sortDesc(data.getAwardedActors());
        }
        StringBuilder strBuilder = new StringBuilder("Query result: [");
        for (Map.Entry<String, Double> it : sorted.entrySet()) {
            strBuilder.append(it.getKey()).append(", ");
        }
        String str = strBuilder.toString();
        str = str.substring(0, str.length() - 2);
        str += "]";
        return str;
    }

    /**
     * Filter Description Query:
     *  Sorteaza actorii ce contin cuvintele din lista. Actorii sunt salvati intr-un arraylist
     *  care este sortat ascendent sau descendent.
     *
     * @param data obiect de tip Database
     * @param words lista de cuvinte
     * @param sortType tipul sortarii
     * @return actorii ce contin cuvintele din lista
     */
    public static String filterDescriptionQuery(final Database data, final List<String> words,
                                                final String sortType) {
        String str = "Query result: [";
        // retine actorii ce au in dscriere cuvintele din lista
        ArrayList<String> filteredActors = new ArrayList<>();
        for (Actor x : data.getActor()) {
            // verifica daca descrirea actorului contine cuvintele
            if (x.containsWords(words)) {
                filteredActors.add(x.getName());
            }
        }
        // verific daca am gasit actori dupa filtrare
        if (!filteredActors.isEmpty()) {
            // daca am gasit actori, ii sortam in functie de tip
            if (sortType.equals("asc")) {
                Collections.sort(filteredActors);
            } else {
                filteredActors.sort(Collections.reverseOrder());
            }
            StringBuilder strBuilder = new StringBuilder("Query result: [");
            for (String filteredActor : filteredActors) {
                strBuilder.append(filteredActor);
                strBuilder.append(", ");
            }
            str = strBuilder.toString();
        } else {
            str += "]";
            return str;
        }
        str = str.substring(0, str.length() - 2);
        str += "]";
        return str;
    }

    /**
     * Average Query:
     * Sorteaza actorii dupa media rating-urilor show-urilor in care au jucat, acestia fiind
     * salvati mai inti in hashmap-ul ratingActor din Database. Actorii sunt parcursi pana cand
     *
     *
     *  @param data obiect de tip Database
     * @param sortType tipul sortarii
     * @param n numarul maxim de actori care sunt afisati
     * @return actorii sortati dupa media rating-urilor show-urilor in care au jucat
     */
    public static String averageQuery(final Database data, final String sortType, final int n) {
        String str;
        Map<String, Double> sorted;
        int rem = n; // salvez n
        if (sortType.equals("asc")) {
            sorted = Sorting.sortAsc(data.getRatingActor());
        } else {
            sorted = Sorting.sortDesc(data.getRatingActor());
        }
        StringBuilder strBuilder = new StringBuilder("Query result: [");
        for (Map.Entry<String, Double> it : sorted.entrySet()) {
            strBuilder.append(it.getKey());
            strBuilder.append(", ");
            rem--;
            // verific daca am gasit n actori
            if (rem == 0) {
                break;
            }
        }
        str = strBuilder.toString();
        str = str.substring(0, str.length() - 2);
        str += "]";
        return str;
    }
}
