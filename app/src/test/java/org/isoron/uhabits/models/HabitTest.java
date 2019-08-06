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
    /* Test for copying a habit multipe times in succession.
    App might be able to copy once but struggle to create 
    multiple copies, hence the test. */
    public void test_multipleCopies()
    {
        // Model of habit
        Habit model = modelFactory.buildHabit();
        model.setArchived(true);
        model.setColor(0);
        model.setFrequency(new Frequency(10, 20));
        model.setReminder(new Reminder(8, 30, new WeekdayList(1)));

        // Create a habit
        Habit habit = modelFactory.buildHabit();
        // Copy from model
        habit.copyFrom(model);
        // Test if data of habit matches data set in model
        assertThat(habit.isArchived(), is(model.isArchived()));
        assertThat(habit.getColor(), is(model.getColor()));
        assertThat(habit.getFrequency(), equalTo(model.getFrequency()));
        assertThat(habit.getReminder(), equalTo(model.getReminder()));
        
        // Create a second habit, copy from model, and test data
        Habit habit_two = modelFactory.buildHabit();
        habit_two.copyFrom(model);
        assertThat(habit_two.isArchived(), is(model.isArchived()));
        assertThat(habit_two.getColor(), is(model.getColor()));
        assertThat(habit_two.getFrequency(), equalTo(model.getFrequency()));
        assertThat(habit_two.getReminder(), equalTo(model.getReminder()));
        
        // Create a third habit, copy from model, and test data
        Habit habit_three = modelFactory.buildHabit();
        habit_three.copyFrom(model);
        assertThat(habit_three.isArchived(), is(model.isArchived()));
        assertThat(habit_three.getColor(), is(model.getColor()));
        assertThat(habit_three.getFrequency(), equalTo(model.getFrequency()));
        assertThat(habit_three.getReminder(), equalTo(model.getReminder()));
        
    }
    
    @Test
    /* Added Test for copying a habit, editing it, then copying again. 
    Similar to testing multiple copies but with the difference of editing
    before copying. Focus of this test is on the edit, focus of the other 
    test is on copying. */
    public void test_copyAfterEdit()
    {
        // Model of habit
        Habit model = modelFactory.buildHabit();
        model.setArchived(true);
        model.setColor(0);
        model.setFrequency(new Frequency(10, 20));
        model.setReminder(new Reminder(8, 30, new WeekdayList(1)));
        
        // Make habit
        Habit habit = modelFactory.buildHabit();
        // Copy from model
        habit.copyFrom(model);
        // Test if data of habit matches data set in model
        assertThat(habit.isArchived(), is(model.isArchived()));
        assertThat(habit.getColor(), is(model.getColor()));
        assertThat(habit.getFrequency(), equalTo(model.getFrequency()));
        assertThat(habit.getReminder(), equalTo(model.getReminder()));
        
        // Change data in model
        model.setArchived(false);
        model.setColor(0);
        model.setFrequency(new Frequency(5, 10));
        model.setReminder(new Reminder(2, 15, new WeekdayList(2)));
        
        // Make another habit
        Habit habit_copy = modelFactory.buildHabit();
        // Copy
        habit_copy.copyFrom(model);
        // Test if data in habit matches new, changed data
        assertThat(habit_copy.isArchived(), is(model.isArchived()));
        assertThat(habit_copy.getColor(), is(model.getColor()));
        assertThat(habit_copy.getFrequency(), equalTo(model.getFrequency()));
        assertThat(habit_copy.getReminder(), equalTo(model.getReminder()));
        
     }
        

    @Test
    /* Test for New Habit (new Attributes) */
    public void test_multiNewAttributes()
    {

        // Create a new habit
        Habit h = modelFactory.buildHabit();
        
        // Set the properties of this new habit
        h.setArchived(false);
        h.setFrequency(new Frequency(1, 2));
        h.setReminder(new Reminder(8, 30, WeekdayList.EVERY_DAY));
        
        // Test if the data is correct
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(1,2)));
        assertThat(h.hasReminder(), is(true));
        
        // Test to see if archiving works
        h.setArchived(true);
        assertThat(h.isArchived(), is(true));
        
    }
    
    @Test 
    public void test_multipleEdits()
    {
    
        // Create a new habit
        Habit h = modelFactory.buildHabit();
        
        // Set properties of the habit
        h.setArchived(false);
        h.setFrequency(new Frequency(10, 20));
        h.setReminder(new Reminder(7, 10, WeekdayList.EVERY_DAY));
        h.setColor(0);
        
        // Test if the data is correct
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(10,20)));
        assertThat(h.hasReminder(), is(true));
        assertThat(h.getColor(), is(0));
    
        // Edit the data of the habit
        h.setFrequency(new Frequency(1, 2));
        h.setReminder(new Reminder(8, 30, new WeekdayList(1)));
        h.setColor(1);
        
        // Test if the data is correct after editing
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(1,2)));
        assertThat(h.hasReminder(), is(true));
        assertThat(h.getColor(), is(1));
        
    }
    
    @Test 
    public void test_multiEditAttributes()
    {
    
        // Make new Habit
        Habit h = modelFactory.buildHabit();
        
        // Set properties
        h.setArchived(false);
        h.setFrequency(new Frequency(10, 20));
        h.setReminder(new Reminder(7, 10, WeekdayList.EVERY_DAY));
        h.setColor(0);
        
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(10,20)));
        assertThat(h.getColor(), is(0));
    
        // Edit multiple times (3) and check if data after all edits matches expected data
        h.setFrequency(new Frequency(3, 4));
        h.setReminder(new Reminder(2, 20, new WeekdayList(1)));
        h.setColor(3);
        
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(3,4)));
        assertThat(h.hasReminder(), is(true));
        assertThat(h.getColor(), is(3));
        
        // Second edit and test
        h.setFrequency(new Frequency(1, 2));
        h.setReminder(new Reminder(8, 30, new WeekdayList(1)));
        h.setColor(1);
        
        assertThat(h.isArchived(), is(false));
        assertThat(h.getFrequency(), equalTo(new Frequency(1,2)));
        assertThat(h.hasReminder(), is(true));
        assertThat(h.getColor(), is(1));
        
        // Third edit and test
        h.setFrequency(new Frequency(6, 2));
        h.setColor(1);
        h.setArchived(true);
        h.clearReminder();
        
        assertThat(h.isArchived(), is(true));
        assertThat(h.getFrequency(), equalTo(new Frequency(6,2)));
        assertThat(h.hasReminder(), is(false));
        assertThat(h.getColor(), is(1));
        
    }
    
    @Test
    /* Test to see if the archive (delete) 
    functionality works correctly */
    public void test_archiveReminder() {
    
        // Create habit
        Habit h = modelFactory.buildHabit();
        // Assert habit is not archived (true by default)
        assertFalse(h.isArchived());
        
        // Set archived to true
        h.setArchived(true);
        // Assert habit is archived (expected)
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
