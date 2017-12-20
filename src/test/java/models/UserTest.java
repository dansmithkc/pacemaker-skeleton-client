package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest
{
  User homer;
  User dan;

  @Before
  public void setup()
  {
    homer = new User("homer", "simpson", "homer@simpson.com", "secret");
    dan = new User("dan", "simpson", "homer@simpson.com", "secret");
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testCreate()
  {
    assertEquals("homer", homer.firstname);
    assertEquals("simpson", homer.lastname);
    assertEquals("homer@simpson.com", homer.email);
    assertEquals("secret", homer.password);
  }

  @Test
  public void testToString()
  {
    assertEquals("User{" + homer.id + ", homer, simpson, secret, homer@simpson.com}", homer.toString());
  }

  @Test
  public void useFieldAccessors()
  {
    // I cannot predict the UUID value, but I can at
    // least exercise the accessor
    homer.getId();
    assertEquals("homer", homer.getFirstname());
    assertEquals("simpson", homer.getLastname());
    assertEquals("homer@simpson.com", homer.getEmail());
  }

  @Test
  public void useEquals()
  {
    assertEquals(homer, homer);
    // For a complete test, each individual field should be tested
    assertNotEquals(homer, dan);
    Object wrongObjectType = new Object();
    assertFalse(homer.equals(wrongObjectType));
  }

  @Test
  public void useHashCode()
  {
    // Adding the object to a hashed collection uses the hashCode() method
    HashSet<User> testSet = new HashSet<>();
    testSet.add(homer);
  }

  @Test
  public void useNoArgsConstructor()
  {
    // Not throwing an exception is a good-enough test
    new User();
  }
}