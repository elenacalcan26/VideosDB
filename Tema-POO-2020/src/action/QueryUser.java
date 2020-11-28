package action;

import database.Database;

import java.util.Map;

public final class QueryUser {
    private QueryUser() { }

    /**
     * User Query:
     * Cauta maxim n utilizatori activi. Utilizatorii sunt sortati dupa activitatea lor in ordinea
     * precizata de tipul sortatii, dat ca parametru.
     *
     * @param data obiect de tip Database
     * @param sortingType tipul sortarii
     * @param n numarul maxim de utilizatori cautati
     * @return cei mai activi utilizatori sortati
     */
    public static String queryActiveUser(final Database data, final String sortingType,
                                         final int n) {
        String str;
        int rem = n; // salvez n
        Map<String, Double> map;
        // sortez
        if (sortingType.equals("desc")) {
            map = Sorting.sortDesc(data.getActiveUser());
        } else {
            map = Sorting.sortAsc(data.getActiveUser());
        }
        StringBuilder strBuilder = new StringBuilder("Query result: [");
        // parcurg utilizatorii
        for (Map.Entry<String, Double> it : map.entrySet()) {
            strBuilder.append(it.getKey());
            strBuilder.append(", ");
            rem--;
            // am gasit maxim n utilizatori
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
