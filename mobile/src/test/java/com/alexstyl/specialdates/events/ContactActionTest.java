package com.alexstyl.specialdates.events;

import com.alexstyl.specialdates.DisplayName;
import com.alexstyl.specialdates.TestContact;
import com.alexstyl.specialdates.contact.Contact;
import com.alexstyl.specialdates.date.ContactEvent;
import com.alexstyl.specialdates.date.Date;
import com.alexstyl.specialdates.events.peopleevents.ContactEvents;
import com.alexstyl.specialdates.events.peopleevents.EventType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ContactActionTest {

    private final List<ContactEvent> ANY_CONTACTS = new ArrayList<>();
    private final TestContact CONTACT_ONE = new TestContact(1, DisplayName.from("Alex Styl"));
    private final ContactEvent EVENT_ONE = new ContactEvent(EventType.BIRTHDAY, Date.on(1, 1, 1990), CONTACT_ONE);

    private final TestContact CONTACT_TWO = new TestContact(2, DisplayName.from("George Peterson"));
    private final ContactEvent EVENT_TWO = new ContactEvent(EventType.BIRTHDAY, Date.on(1, 1, 1970), CONTACT_TWO);

    @Test
    public void testTheSameDateIsReturned() throws Exception {
        Date expectedDate = Date.on(1, 1, 1990);
        ContactEvents events = ContactEvents.createFrom(expectedDate, ANY_CONTACTS);

        Date actualDate = events.getDate();
        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    public void testContactCorrectContactIsReturned() {
        Date date = Date.on(1, 1, 2016);
        ArrayList<ContactEvent> contactEvent = new ArrayList<>();
        contactEvent.add(EVENT_ONE);

        ContactEvents events = ContactEvents.createFrom(date, contactEvent);

        List<Contact> contacts = events.getContacts();
        assertThat(contacts.get(0)).isEqualTo(CONTACT_ONE);
    }

    @Test
    public void testContactsAreCorrectlyReturned() {
        Date date = Date.on(1, 1, 2016);
        ArrayList<ContactEvent> contactEvent = new ArrayList<>();
        contactEvent.add(EVENT_ONE);
        contactEvent.add(EVENT_TWO);
        ContactEvents events = ContactEvents.createFrom(date, contactEvent);

        List<Contact> contacts = events.getContacts();
        assertThat(contacts).contains(CONTACT_ONE);
        assertThat(contacts).contains(CONTACT_TWO);
    }

    @Test
    public void testReturnedContactsSizeIsCorrect() {
        Date date = Date.on(1, 1, 2016);
        ArrayList<ContactEvent> contactEvent = new ArrayList<>();
        contactEvent.add(EVENT_ONE);
        contactEvent.add(EVENT_TWO);
        ContactEvents events = ContactEvents.createFrom(date, contactEvent);

        List<Contact> contacts = events.getContacts();
        assertThat(contacts.size()).isEqualTo(2);
    }
}
