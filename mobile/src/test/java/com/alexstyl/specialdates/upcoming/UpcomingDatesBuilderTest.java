package com.alexstyl.specialdates.upcoming;

import android.support.annotation.NonNull;

import com.alexstyl.specialdates.DisplayName;
import com.alexstyl.specialdates.TestContact;
import com.alexstyl.specialdates.date.CelebrationDate;
import com.alexstyl.specialdates.date.ContactEvent;
import com.alexstyl.specialdates.date.Date;
import com.alexstyl.specialdates.events.bankholidays.BankHoliday;
import com.alexstyl.specialdates.events.namedays.NamesInADate;
import com.alexstyl.specialdates.events.peopleevents.EventType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.alexstyl.specialdates.date.DateConstants.FEBRUARY;
import static com.alexstyl.specialdates.date.DateConstants.MARCH;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UpcomingDatesBuilderTest {

    private static final Date FEBRUARY_1st = Date.on(1, FEBRUARY, 1990);
    private static final Date FEBRUARY_3rd = Date.on(3, FEBRUARY, 1990);
    private static final Date MARCH_5th = Date.on(5, MARCH, 1990);

    @Test
    public void givenASingleContactEvent_thenOneCelebrationDateIsCreated() {
        List<ContactEvent> contactEvents = Collections.singletonList(aContactEvent());

        List<CelebrationDate> dates = new UpcomingDatesBuilder()
                .withContactEvents(contactEvents)
                .build();

        assertThat(dates.size()).isEqualTo(1);
    }

    @Test
    public void givenASingleContactEvent_thenTheCelebrationDateContainsTheContactEvent() {
        List<ContactEvent> contactEvents = Collections.singletonList(aContactEvent());

        List<CelebrationDate> dates = new UpcomingDatesBuilder()
                .withContactEvents(contactEvents)
                .build();

        ContactEvent celebrationDateEvent = dates.get(0).getContactEvents().getEvent(0);
        assertThat(celebrationDateEvent).isEqualTo(contactEvents.get(0));
    }

    @Test
    public void givenTwoContactEventsOnSameDate_thenOneCelebrationDateIsCreated() {
        List<ContactEvent> contactEventsList = Arrays.asList(
                aContactEventOn(FEBRUARY_1st),
                aContactEventOn(FEBRUARY_1st)
        );

        List<CelebrationDate> dates = new UpcomingDatesBuilder()
                .withContactEvents(contactEventsList)
                .build();

        assertThat(dates.size()).isEqualTo(1);
    }

    @Test
    public void givenTwoContactEventsOnDifferentDate_thenTwoCelebrationDateAreCreated() {
        List<ContactEvent> contactEventsList = Arrays.asList(
                aContactEventOn(FEBRUARY_1st),
                aContactEventOn(FEBRUARY_3rd)
        );

        List<CelebrationDate> dates = new UpcomingDatesBuilder()
                .withContactEvents(contactEventsList)
                .build();

        assertThat(dates.size()).isEqualTo(2);
    }

    @Test
    public void givenABankHoliday_thenACelebrationDateIsCreated() {
        List<BankHoliday> bankHolidays = Collections.singletonList(aBankHoliday());
        List<CelebrationDate> dates = new UpcomingDatesBuilder()
                .withBankHolidays(bankHolidays)
                .build();

        assertThat(dates.size()).isEqualTo(1);
    }

    @Test
    public void givenEventsOnDifferentEvents_thenACelebrationDatesForEachOneAreCreated() {
        List<CelebrationDate> dates = new UpcomingDatesBuilder()
                .withContactEvents(Collections.singletonList(aContactEventOn(FEBRUARY_1st)))
                .withBankHolidays(Collections.singletonList(aBankHolidayOn(FEBRUARY_3rd)))
                .withNamedays(Collections.singletonList(new NamesInADate(MARCH_5th, Collections.singletonList("Name"))))
                .build();

        assertThat(dates.size()).isEqualTo(3);
    }

    private BankHoliday aBankHolidayOn(Date date) {
        return new BankHoliday("A bank holiday", date);
    }

    @NonNull
    private BankHoliday aBankHoliday() {
        return new BankHoliday("A bank holiday", Date.on(1, 1, 1990));
    }

    private static ContactEvent aContactEvent() {
        return aContactEventOn(Date.on(1, 1, 1990));
    }

    private static ContactEvent aContactEventOn(Date date) {
        TestContact contact = new TestContact(1, DisplayName.NO_NAME);
        return new ContactEvent(EventType.BIRTHDAY, date, contact);
    }

}
