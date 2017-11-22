package controllers;

import io.javalin.Javalin;

public class RestMain
{

  public static void main(String[] args) throws Exception
  {
    Javalin app = Javalin.start(7000);
    PacemakerRestService service = new PacemakerRestService();
    configRoutes(app, service);
  }

  static void configRoutes(Javalin app, PacemakerRestService service)
  {

    app.get("/users", ctx -> {
      service.listUsers(ctx);
    });

    app.post("/users", ctx -> {
      service.createUser(ctx);
    });

    app.get("/users/:id", ctx -> {
      service.listUser(ctx);
    });

    app.get("/users/:id/activities", ctx -> {
      service.getActivities(ctx);
    });

    app.post("/users/:id/activities", ctx -> {
      service.createActivity(ctx);
    });
  }
}