package com.example.babytracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Baby {

    @PrimaryKey(autoGenerate = true)
    long id;

    String name;
    String dateOfBirth;
    String imageUrl;

    public Baby(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.imageUrl = imageUrl;
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
