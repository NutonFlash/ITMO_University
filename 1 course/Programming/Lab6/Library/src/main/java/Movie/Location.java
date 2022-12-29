package Movie;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Location implements Serializable {
    Float x; //Поле не может быть null
    double y;
    Integer z; //Поле не может быть null
    String name; //Строка не может быть пустой, Поле не может быть null
}
