package models;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static models.Fixtures.locations;

public class LocationTest
{

  @Before
  public void setup()
  {
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testCreate()
  {
    assertEquals(0.01, 23.3, locations.get(0).latitude);
    assertEquals(0.01, 33.3, locations.get(0).longitude);
  }

  @Test
  public void testToString()
  {
    assertEquals("Location{23.3, 33.3}", locations.get(0).toString());
  }

  @Test
  public void useFieldAccessors()
  {
    // I cannot predict the UUID value, but I can at
    // least exercise the accessor
    Location testLocation = locations.get(0);
    assertEquals(0.01, 23.3, testLocation.getLatitude());
    assertEquals(0.01, 33.3, testLocation.getLongitude());
  }

  @Test
  public void useEquals()
  {
    Location test = new Location(4.4, 33);
    Location test2 = new Location(3.44, 22.22);
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
    HashSet<Location> testSet = new HashSet<>();
    testSet.add(locations.get(0));
  }

  @Test
  public void useNoArgsConstructor()
  {
    // Not throwing an exception is a good-enough test
    new Location();
  }
}