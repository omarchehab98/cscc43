package cscc43.assignment.viewmodel;

public class SearchResult {
    private String name;
    private Integer year;
    private String type;
    private String person;

    public SearchResult(String name, Integer year, String type, String person) {
        this.name = name;
        this.year = year;
        this.type = type;
        this.person = person;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPerson() {
        return this.person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPersonColumnName() {
        if (type.equals("Book")) return "Author";
        else if (type.equals("Music")) return "Singer";
        else if (type.equals("Movie")) return "Director";
        else throw new IllegalArgumentException();
    }

    public String toString() {
        return String.format(
            "SearchResult(%s, %s, %s, %s)",
            name,
            year,
            type,
            person
        );
    }
}
