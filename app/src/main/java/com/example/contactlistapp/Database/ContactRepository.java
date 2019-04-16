package com.example.contactlistapp.Database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.contactlistapp.Contact;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ContactRepository {

    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void deleteData() { contactDao.deleteAll(); }

    // Ensures that long-running operations are not excuted on the main thread
    public void insert(Contact contact){
        new insertAsyncTask(contactDao).execute(contact);
    }

    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
