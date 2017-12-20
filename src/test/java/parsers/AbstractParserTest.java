package parsers;

import static models.Fixtures.activities;
import static models.Fixtures.locations;
import static models.Fixtures.users;

import java.util.ArrayList;

import org.junit.Test;

import models.Activity;
import models.Location;
import models.User;

public abstract class AbstractParserTest
{

  // This class only exercises the output
  // It does not have asserts

  Parser parser;

  @Test
  public void testPrintln()
  {
    parser.println("Hello World");
  }

  @Test
  public void testRenderUser()
  {
    parser.renderUser(users.get(0));
    parser.renderUser(null);
  }

  @Test
  public void testRenderUsers()
  {
    parser.renderUsers(users);
    parser.renderUsers(new ArrayList<User>());
    parser.renderUsers(null);
  }

  @Test
  public void testRenderActivity()
  {
    parser.renderActivity(activities.get(0));
    parser.renderActivity(null);
  }

  @Test
  public void testRenderActivities()
  {
    parser.renderActivities(activities);
    parser.renderActivities(new ArrayList<Activity>());
    parser.renderActivities(null);
  }

  @Test
  public void testRenderLocations()
  {
    parser.renderLocations(locations);
    parser.renderLocations(new ArrayList<Location>());
    parser.renderLocations(null);
  }

}