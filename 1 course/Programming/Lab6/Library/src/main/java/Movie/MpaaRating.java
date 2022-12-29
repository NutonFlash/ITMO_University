package Movie;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum MpaaRating implements Serializable {
    G("G"),
    PG("PG"),
    PG_13("PG_13"),
    R("R");

    private String value;

    MpaaRating(String value) {
        this.value = value;
    }
}
