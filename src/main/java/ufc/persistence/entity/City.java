package ufc.persistence.entity;

import javax.persistence.Entity;

@Entity
public class City extends Item {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
