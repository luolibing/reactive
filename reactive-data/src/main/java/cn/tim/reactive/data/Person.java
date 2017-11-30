package cn.tim.reactive.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User: luolibing
 * Date: 2017/11/29 15:18
 */
@Data
@Document
public class Person {

    private String id;

    private String firstName;

    private String lastName;

    public Person() {
    }

    public Person(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
