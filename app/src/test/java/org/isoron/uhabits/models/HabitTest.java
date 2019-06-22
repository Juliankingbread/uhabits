/*
 * Copyright (C) 2016 Álinson Santos Xavier <isoron@gmail.com>
 *
 * This file is part of Loop Habit Tracker.
 *
 * Loop Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Loop Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.isoron.uhabits.models;

import org.isoron.uhabits.*;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class HabitTest extends BaseUnitTest
{
    @Override
    public void setUp()
    {
        super.setUp();
    }

    @Test
    public void testConstructor_default()
    {
        Habit habit = modelFactory.buildHabit();
        assertFalse(habit.isArchived());

        assertThat(habit.hasReminder(), is(false));
        assertThat(habit.getStreaks(), is(not(nullValue())));
        assertThat(habit.getScores(), is(not(nullValue())));
        assertThat(habit.getRepetitions(), is(not(nullValue())));
        assertThat(habit.getCheckmarks(), is(not(nullValue())));
    }

    @Test
    public void test_copyAttributes()
    {
        Habit model = modelFactory.buildHabit();
        model.setArchived(true);
        model.setColor(0);
        model.setFrequency(new Frequency(10, 20));
        model.setReminder(new Reminder(8, 30, new WeekdayList(1)));

        Habit habit = modelFactory.buildHabit();
        habit.copyFrom(model);
        assertThat(habit.isArchived(), is(model.isArchived()));
        assertThat(habit.getColor(), is(model.getColor()));
        assertThat(habit.getFrequency(), equalTo(model.getFrequency()));
        assertThat(habit.getReminder(), equalTo(model.getReminder()));
    }

    @Test
    /* Added Test for New Habit (new Attributes) */
    public void test_multiNewAttributes()
    {

        // Make new Habit
        Habit h = modelFactory.buildHabit();
        
        // Set properties
        h.setArchived(false);
        h.setFrequency((new Frequency(1, 2));
        h.setReminder(new Reminder(8, 30, WeekdayList.EVERY_DAY));
        
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(1,2));
        assertThat(h.hasReminder(), is(true));
        assertThat(h.getReminder(), equalTo(new Reminder(8, 30, WeekdayList.EVERY_DAY)));
        
        // Archive (edit)
        h.setArchived(true);
        assertThat(h.isArchived(), is(true));
        
    }
    
    @Test 
    public void test_multiEditAttributes()
    {
    
        // Make new Habit
        Habit h = modelFactory.buildHabit();
        
        // Set properties
        h.setArchived(false);
        h.setFrequency((new Frequency(10, 20));
        h.setReminder(new Reminder(7, 10, WeekdayList.EVERY_DAY));
        h.setColor(0);
        
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(10,20));
        assertThat(h.getReminder(), equalTo(new Reminder(7, 10, WeekdayList.EVERY_DAY)));
        assertThat(h.getColor(), equalTo(0));
    
        // EDITTEN
        h.setFrequency((new Frequency(1, 2));
        h.setReminder(new Reminder(8, 30, new WeekdayList(1)));
        h.setColor(1);
        
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(1,2));
        assertThat(h.getReminder(), equalTo(new Reminder(8, 30, newWeekdayList(1))));
        assertThat(h.getColor(), equalTo(1));
        
    }
    
    @Test
    public void test_archiveReminder() {
    
        Habit h = modelFactory.buildHabit();
        assertFalse(h.isArchived());
        
        h.setArchived(true);
        assertTrue(h.isArchived());
    
    }

    @Test
    public void test_hasReminder_clearReminder()
    {
        Habit h = modelFactory.buildHabit();
        assertThat(h.hasReminder(), is(false));

        h.setReminder(new Reminder(8, 30, WeekdayList.EVERY_DAY));
        assertThat(h.hasReminder(), is(true));

        h.clearReminder();
        assertThat(h.hasReminder(), is(false));
    }
}
