package controllers;

import static models.Fixtures.users;

import io.javalin.Context;
import models.Activity;
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