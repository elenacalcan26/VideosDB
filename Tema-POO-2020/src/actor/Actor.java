package actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }


    /**
     * Verifica daca actorul a castigat toate premiile din lista de premii dat ca parametru
     * @param awards lista cu premii
     * @return true daca actorul a castigat toate premiile din lista
     */
    public boolean hasAwards(final List<String> awards) {
        int tmp = 0;
        for (String award : awards) {
            for (Map.Entry<ActorsAwards, Integer> it : this.awards.entrySet()) {
                if (award.equals(it.getKey().name())) {
                    tmp++;
                }
            }
        }
        return tmp == awards.size();
    }

    /**
     * Calculeaza suma tuturor premiilor din lista data ca parametru
     * @param awards lista de premii
     * @return suma tuturor premiilor din lista
     */
    public int totalAwards(final List<String> awards) {
        int sum = 0;
        for (int i = 0; i < awards.size(); i++) {
            for (Map.Entry<ActorsAwards, Integer> it : this.awards.entrySet()) {
                sum += it.getValue();
            }
        }
        return sum;
    }

    /**
     * Verifca daca in descrierea actorului exista toate cuvintele din lista
     * @param words lista ce contine cuvinte
     * @return true daca descrierea contine toate cuvintele, false in caz contrar
     */
    public boolean containsWords(final List<String> words) {
        int cnt = 0;
        for (String s : words) {
            Pattern word = Pattern.compile("[ ,!.')-]" + s + "[ ,!.'(-]",
                    Pattern.CASE_INSENSITIVE);
            Matcher match = word.matcher(careerDescription);
            if (match.find()) {
                cnt++;
            }
        }
        return cnt == words.size();
    }
}
