package parsers;

import java.util.Collection;
import java.util.List;

import models.Activity;
import models.Location;
import models.User;

public class Parser
{

  public void println(String s)
  {
    System.out.println(s);
  }

  public void renderUser(User user)
  {
    if (user == null)
    {
      return;
    }
    System.out.println(user.toString());
  }

  public void renderUsers(Collection<User> users)
  {
    if (users == null)
    {
      return;
    }
    System.out.println(users.toString());
  }

  public void renderActivity(Activity activities)
  {
    if (activities == null)
    {
      return;
    }
    System.out.println(activities.toString());
  }

  public void renderActivities(Collection<Activity> activities)
  {
    if (activities == null)
    {
      return;
    }
    System.out.println(activities.toString());
  }

  public void renderLocations(List<Location> locations)
  {
    if (locations == null)
    {
      return;
    }
    System.out.println(locations.toString());
  }
}