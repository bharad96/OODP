package com.company.Entity;
import java.io.Serializable;
/**
 * Represents a person with a name.
 * @author Group 2 - SS6
 * @version 1.0
 * @since 2019-11-12
 */
public class Person implements Serializable{
    /**
     * The name of this person.
     */
    private String name;

    /**
     * Creates a new Person with the given name.
     * @param name This person's name.
     */
    Person(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this person.
     * @return this Person's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the name of this Person.
     * @param name This Person's new name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
