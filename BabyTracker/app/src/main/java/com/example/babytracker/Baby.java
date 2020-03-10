package com.example.babytracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Baby {

    @PrimaryKey(autoGenerate = true)
    long id;

    String name;
    String dateOfBirth;
//    Boolean immunization;

    public Baby(String name, String dateOfBirth, boolean immunization) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
//        this.immunization = immunization;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

//    public Boolean getImmunization() {
//        return immunization;
//    }
}
