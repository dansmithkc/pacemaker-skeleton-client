package controllers;

import static models.Fixtures.users;

import io.javalin.Context;
import models.Activity;
import models.Location;
import models.User;

public class PacemakerRestService
{

  PacemakerAPI pacemaker = new PacemakerAPI();

  PacemakerRestService()
  {
    users.forEach(user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
  }

  public void listUsers(Context ctx)
  {
    ctx.json(pacemaker.getUsers());
  }

  public void createUser(Context ctx)
  {
    User user = ctx.bodyAsClass(User.class);
    User newUser = pacemaker.createUser(user.firstname, user.lastname, user.email, user.password);
    ctx.json(newUser);
  }

  public void listUser(Context ctx)
  {
    String id = ctx.param("id");
    ctx.json(pacemaker.getUser(id));
  }

  public void getActivity(Context ctx)
  {
    String id = ctx.param("activityId");
    // TO DO - throw a 404 if user id is not valid for that activity
    Activity activity = pacemaker.getActivity(id);
    if (activity != null)
    {
      ctx.json(activity);
    }
    else
    {
      ctx.status(404);
    }
  }

  public void getActivities(Context ctx)
  {
    String id = ctx.param("id");
    User user = pacemaker.getUser(id);
    if (user != null)
    {
      ctx.json(user.activities.values());
    }
    else
    {
      ctx.status(404);
    }
  }

  public void getActivityLocations(Context ctx)
  {
    String id = ctx.param("activityId");
    Activity activity = pacemaker.getActivity(id);
    // TO DO - throw a 404 if user id is not valid for that activity
    if (activity != null)
    {
      ctx.json(activity.route);
    }
    else
    {
      ctx.status(404);
    }
  }

  public void addLocation(Context ctx)
  {
    String id = ctx.param("activityId");
    Activity activity = pacemaker.getActivity(id);
    // TO DO - throw a 404 if user id is not valid for that activity
    if (activity != null)
    {
      Location location = ctx.bodyAsClass(Location.class);
      activity.route.add(location);
      ctx.json(location);
    }
    else
    {
      ctx.status(404);
    }
  }

  public void createActivity(Context ctx)
  {
    String id = ctx.param("id");
    User user = pacemaker.getUser(id);
    if (user != null)
    {
      Activity activity = ctx.bodyAsClass(Activity.class);
      Activity newActivity = pacemaker.createActivity(id, activity.type, activity.location, activity.distance);
      ctx.json(newActivity);
    }
    else
    {
      ctx.status(404);
    }
  }
}