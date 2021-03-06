package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Optional;

import asg.cliche.Command;
import asg.cliche.Param;
import configuration.PacemakerAPIConfiguration;
import models.Activity;
import models.Message;
import models.User;
import models.UserDistance;
import parsers.AsciiTableParser;
import parsers.Parser;

public class PacemakerConsoleService
{
  private PacemakerAPI paceApi = new PacemakerAPI(PacemakerAPIConfiguration.PACEMAKER_API_URL);
  private Parser console = new AsciiTableParser();
  private User loggedInUser = null;

  public PacemakerConsoleService()
  {
  }

  // Starter Commands

  @Command(description = "Register: Create an account for a new user")
  public void register(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
      @Param(name = "email") String email, @Param(name = "password") String password)
  {
    console.renderUser(paceApi.createUser(firstName, lastName, email, password));
  }

  @Command(description = "List Users: List all users emails, first and last names")
  public void listUsers()
  {
    console.renderUsers(paceApi.getUsers());
  }

  @Command(description = "Login: Log in a registered user in to pacemaker")
  public void login(@Param(name = "email") String email, @Param(name = "password") String password)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUserByEmail(email));
    if (user.isPresent())
    {
      if (user.get().password.equals(password))
      {
        loggedInUser = user.get();
        console.println("Logged in " + loggedInUser.email);
        console.println("ok");
      }
      else
      {
        console.println("Error on login");
      }
    }
  }

  @Command(description = "Logout: Logout current user")
  public void logout()
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (user.isPresent())
    {
      console.println("Logging out " + loggedInUser.email);
      loggedInUser = null;
    }
    console.println("ok");
  }

  @Command(description = "Add activity: create and add an activity for the logged in user")
  public void addActivity(@Param(name = "type") String type, @Param(name = "location") String location,
      @Param(name = "distance") double distance)
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (user.isPresent())
    {
      console.renderActivity(paceApi.createActivity(user.get().id, type, location, distance));
    }
  }

  @Command(description = "List Activities: List all activities for logged in user")
  public void listActivities()
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (user.isPresent())
    {
      console.renderActivities(paceApi.getActivities(user.get().id, ""));
    }
  }

  // Baseline Commands

  @Command(description = "Add location: Append location to an activity")
  public void addLocation(@Param(name = "activity-id") String id, @Param(name = "longitude") double longitude,
      @Param(name = "latitude") double latitude)
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (user.isPresent())
    {
      Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(loggedInUser.getId(), id));
      if (activity.isPresent())
      {
        paceApi.addLocation(loggedInUser.getId(), activity.get().id, latitude, longitude);
        console.println("ok");
      }
      else
      {
        console.println("not found");
      }
    }
    else
    {
      console.println("logged in user not found");
    }
  }

  @Command(description = "ActivityReport: List all activities for logged in user, sorted alphabetically by type")
  public void activityReport()
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (user.isPresent())
    {
      console.renderActivities(paceApi.getActivities(user.get().id, "type"));
    }
  }

  @Command(description = "Activity Report: List all activities for logged in user by type. Sorted longest to shortest distance")
  public void activityReport(@Param(name = "byType: type") String type)
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (user.isPresent())
    {
      Collection<Activity> usersActivities = paceApi.getActivities(user.get().id, "distance");
      List<Activity> returnedActivities = usersActivities.stream().filter(a -> a.type.equals(type))
          .collect(Collectors.toList());
      console.renderActivities(returnedActivities);
    }
  }

  @Command(description = "List all locations for a specific activity")
  public void listActivityLocations(@Param(name = "activity-id") String id)
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (user.isPresent())
    {
      Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(loggedInUser.getId(), id));
      if (activity.isPresent())
      {
        console.renderLocations(activity.get().route);
      }
    }
  }

  @Command(description = "Follow Friend: Follow a specific friend")
  public void follow(@Param(name = "email") String email)
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (!user.isPresent())
    {
      console.println("logged in user not found");
      return;
    }
    Optional<User> friend = Optional.fromNullable(paceApi.getUserByEmail(email));
    if (!friend.isPresent())
    {
      console.println("friend not found");
      return;
    }
    paceApi.followFriend(user.get().getId(), friend.get().getId());
    console.println("ok");
  }

  @Command(description = "List Friends: List all of the friends of the logged in user")
  public void listFriends()
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (!user.isPresent())
    {
      console.println("logged in user not found");
      return;
    }
    console.renderUsers(paceApi.listFriends(user.get().getId()));
  }

  @Command(description = "Friend Activity Report: List all activities of specific friend, sorted alphabetically by type)")
  public void friendActivityReport(@Param(name = "email") String email)
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (!user.isPresent())
    {
      console.println("logged in user not found");
      return;
    }
    Optional<List<User>> friends = Optional.fromNullable(paceApi.listFriends(user.get().getId()));
    if (!friends.isPresent())
    {
      console.println("friends not found");
      return;
    }
    List<User> friend = friends.get().stream().filter(f -> f.email.equals(email)).collect(Collectors.toList());
    if (friend.size() == 0)
    {
      console.println("friend not found");
      return;
    }
    String id = friend.get(0).id;
    Collection<Activity> usersActivities = paceApi.getActivities(id, "type");
    console.renderActivities(usersActivities);
  }

  // Good Commands

  @Command(description = "Unfollow Friends: Stop following a friend")
  public void unfollowFriend()
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (!user.isPresent())
    {
      console.println("logged in user not found");
      return;
    }
    paceApi.unfollowFriends(user.get().id);
    console.println("ok");
  }

  @Command(description = "Message Friend: send a message to a friend")
  public void messageFriend(@Param(name = "email") String email, @Param(name = "message") String message)
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (!user.isPresent())
    {
      console.println("logged in user not found");
      return;
    }
    String userId = user.get().getId();
    Optional<List<User>> friends = Optional.fromNullable(paceApi.listFriends(userId));
    if (!friends.isPresent())
    {
      console.println("friends not found");
      return;
    }
    List<User> friendsToMessage = friends.get();
    if (!email.equals(""))
    {
      friendsToMessage = friendsToMessage.stream().filter(f -> f.email.equals(email)).collect(Collectors.toList());
    }
    if (friendsToMessage.size() == 0)
    {
      console.println("friend not found");
      return;
    }
    friendsToMessage.forEach(friend -> paceApi.messageFriend(userId, friend.getId(), new Message(message)));
    console.println("ok");
  }

  @Command(description = "List Messages: List all messages for the logged in user")
  public void listMessages()
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (!user.isPresent())
    {
      console.println("logged in user not found");
      return;
    }
    console.renderMessages(paceApi.listMessages(user.get().id));
  }

  @Command(description = "Distance Leader Board: list summary distances of all friends, sorted longest to shortest")
  public void distanceLeaderBoard()
  {
    distanceLeaderBoard("", "");
  }

  // Excellent Commands

  @Command(description = "Distance Leader Board: distance leader board refined by type")
  public void distanceLeaderBoardByType(@Param(name = "byType: type") String type)
  {
    distanceLeaderBoard(type, "");
  }

  @Command(description = "Message All Friends: send a message to all friends")
  public void messageAllFriends(@Param(name = "message") String message)
  {
    messageFriend("", message);
  }

  @Command(description = "Location Leader Board: list sorted summary distances of all friends in named location")
  public void locationLeaderBoard(@Param(name = "location") String location)
  {
    distanceLeaderBoard("", location);
  }

  public void distanceLeaderBoard(String type, String location)
  {
    Optional<User> user = Optional.fromNullable(loggedInUser);
    if (!user.isPresent())
    {
      console.println("logged in user not found");
      return;
    }
    Optional<List<User>> friends = Optional.fromNullable(paceApi.listFriends(user.get().getId()));
    if (!friends.isPresent())
    {
      console.println("friends not found");
      return;
    }
    List<UserDistance> userDistances = new ArrayList<>();
    for (User friend : friends.get())
    {
      List<Activity> activities = paceApi.getActivities(friend.id, "");
      if (!type.equals(""))
      {
        activities = activities.stream().filter(a -> a.type.equals(type)).collect(Collectors.toList());
      }
      if (!location.equals(""))
      {
        activities = activities.stream().filter(a -> a.location.equals(location)).collect(Collectors.toList());
      }
      double distance = activities.stream().mapToDouble(i -> i.distance).sum();
      UserDistance userDistance = new UserDistance(friend, distance);
      userDistances.add(userDistance);
    }
    // Reverse Sort -s compare backwards
    userDistances.sort((a1, a2) -> new Double(a2.distance).compareTo(a1.distance));
    console.renderUserDistance(userDistances);
  }

  // Outstanding Commands

}