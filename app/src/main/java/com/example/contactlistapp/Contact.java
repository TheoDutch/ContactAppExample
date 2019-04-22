package com.example.contactlistapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "contact_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;
    private String email;
    private String phonenumber;
    private String picturePath;

    public Contact(int id, String name, String email, String phonenumber, String picturePath){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.picturePath = picturePath;
    }

    public int getId(){return this.id;}

    public String getName(){return this.name;}

    public String getEmail(){return this.email;}

    public String getPhonenumber(){return this.phonenumber;}

    public String getPicturePath(){return this.picturePath;}

}
