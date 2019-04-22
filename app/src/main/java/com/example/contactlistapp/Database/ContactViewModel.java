package com.example.contactlistapp.Database;

import android.app.Application;

import com.example.contactlistapp.Contact;
import com.example.contactlistapp.ContactListAdapter;
import com.example.contactlistapp.R;
import com.example.contactlistapp.SearchResultsActivity;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository contactRepository;
    private LiveData<List<Contact>> allContacts;

    public ContactViewModel(Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
        allContacts = contactRepository.getAllContacts();
    }

    public void deleteData() { contactRepository.deleteData(); }

    public LiveData<List<Contact>> getAllContacts() { return allContacts; }

    public void insert(Contact contact) { contactRepository.insert(contact); }

    public void deleteAll() { contactRepository.deleteData();}

}
