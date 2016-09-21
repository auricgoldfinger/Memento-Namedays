package com.alexstyl.specialdates.events.bankholidays;

import com.alexstyl.specialdates.Optional;
import com.alexstyl.specialdates.date.DayDate;
import com.alexstyl.specialdates.events.namedays.calendar.EasterCalculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BankHolidayRepositoryTest {

    private static final DayDate GREEK_INDEPENDENCE_DAY = DayDate.newInstance(25, DayDate.MARCH, 1990);

    @Mock
    private EasterCalculator calculator;
    private BankHolidayRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new BankHolidayRepository(calculator);
        when(calculator.calculateEasterForYear(1990)).thenReturn(DayDate.newInstance(1, 1, 1990));
        when(calculator.calculateEasterForYear(1991)).thenReturn(DayDate.newInstance(1, 1, 1991));
    }

    @Test
    public void whenCheckingForSameYearTwice_thenEasterIsCalculatedOnlyOnce() {
        repository.calculateBankholidayFor(aDateInYear(1990));
        repository.calculateBankholidayFor(aDateInYear(1990));
        verify(calculator, times(1)).calculateEasterForYear(1990);
    }

    @Test
    public void whenCheckingForDifferentYearthenEasterIsRecalculated() {
        repository.calculateBankholidayFor(aDateInYear(1990));
        repository.calculateBankholidayFor(aDateInYear(1991));
        verify(calculator, times(1)).calculateEasterForYear(1990);
        verify(calculator, times(1)).calculateEasterForYear(1991);
    }

    @Test
    public void testThatAGreekKnownBankholidayIsCalculatedProperly() {
        DayDate date = GREEK_INDEPENDENCE_DAY;
        Optional<BankHoliday> bankHoliday = repository.calculateBankholidayFor(date);
        assertThat(bankHoliday.get().getDate()).isEqualTo(DayDate.newInstance(25, DayDate.MARCH, 1990));
    }

    private DayDate aDateInYear(int year) {
        return DayDate.newInstance(1, 1, year);
    }
}
