package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.PacemakerAPIConfiguration;
import models.Activity;
import models.User;

public class ActivityTest
{

  PacemakerAPI pacemaker = new PacemakerAPI(PacemakerAPIConfiguration.PACEMAKER_API_URL);
  User homer = new User("homer", "simpson", "homer@simpson.com", "secret");

  @Before
  public void setup()
  {
    pacemaker.deleteUsers();
    homer = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testCreateActivity()
  {
    Activity activity = new Activity("walk", "shop", 2.5);

    Activity returnedActivity = pacemaker.createActivity(homer.id, activity.type, activity.location, activity.distance);
    assertEquals(activity.type, returnedActivity.type);
    assertEquals(activity.location, returnedActivity.location);
    assertEquals(activity.distance, returnedActivity.distance, 0.001);
    assertNotNull(returnedActivity.id);
  }
}