package Movie;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Person implements Serializable {
    String name; //Поле не может быть null, Строка не может быть пустой
    double weight; //Значение поля должно быть больше 0
    Country nationality; //Поле не может быть null
    Location location; //Поле может быть null
}
