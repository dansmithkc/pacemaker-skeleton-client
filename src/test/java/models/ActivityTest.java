package models;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static models.Fixtures.activities;

public class ActivityTest
{

  Activity test;
  Activity test2;

  @Before
  public void setup()
  {
    test = new Activity("walk", "fridge", 0.001);
    test2 = new Activity("walkaaa", "bbbfridge", 0.4001);
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testCreate()
  {
    assertEquals("walk", test.type);
    assertEquals("fridge", test.location);
    assertEquals(0.0001, 0.001, test.distance);
  }

  @Test
  public void testToString()
  {
    assertEquals("Activity{" + test.id + ", walk, fridge, 0.001, []}", test.toString());
  }

  @Test
  public void useFieldAccessors()
  {
    // I cannot predict the UUID value, but I can at
    // least exercise the accessor
    test.getId();
    assertEquals("walk", test.getType());
    assertEquals("fridge", test.getLocation());
    assertEquals("0.001", test.getDistance());
  }

  @Test
  public void useEquals()
  {
    assertEquals(test, test);
    // For a complete test, each individual field should be tested
    assertNotEquals(test2, test);
    Object wrongObjectType = new Object();
    assertFalse(test.equals(wrongObjectType));
  }

  @Test
  public void useHashCode()
  {
    // Adding the object to a hashed collection uses the hashCode() method
    HashSet<Activity> testSet = new HashSet<>();
    testSet.add(test);
  }

  @Test
  public void useNoArgsConstructor()
  {
    // Not throwing an exception is a good-enough test
    new Activity();
  }

}