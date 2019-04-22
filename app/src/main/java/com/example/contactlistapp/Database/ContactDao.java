package com.example.contactlistapp.Database;

import com.example.contactlistapp.Contact;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Query("SELECT * from contact_table ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contact_table WHERE name LIKE :search " +
            "OR email LIKE :search")
    LiveData<List<Contact>> searchContact(String search);


}
