package Movie;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Country implements Serializable {
    RUSSIA("RUSSIA"),
    FRANCE("FRANCE"),
    INDIA("INDIA"),
    CHINA("CHINA"),
    UKRAINE("UKRAINE"),
    USA("USA"),
    ENGLAND("ENGLAND"),
    IRELAND("IRELAND"),
    GERMAN("GERMAN"),
    JAPAN("JAPAN"),
    CANADA("CANADA");
    private String value;

    Country(String value) {
        this.value = value;
    }
}
