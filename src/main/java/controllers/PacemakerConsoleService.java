package controllers;

import com.google.common.base.Optional;

import asg.cliche.Command;
import asg.cliche.Param;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import models.Activity;
import models.User;
import parsers.AsciiTableParser;
import parsers.Parser;

public class PacemakerConsoleService
{

  private PacemakerAPI paceApi = new PacemakerAPI();;
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
  }

  @Command(description = "List Users: List all users emails, first and last names")
  public void listUsers()
  {
  }

  @Command(description = "Login: Log in a registered user in to pacemaker")
  public void login(@Param(name = "email") String email, @Param(name = "password") String password)
  {
  }

  @Command(description = "Logout: Logout current user")
  public void logout()
  {
  }

  @Command(description = "Add activity: create and add an activity for the logged in user")
  public void addActivity(@Param(name = "type") String type, @Param(name = "location") String location,
      @Param(name = "distance") double distance)
  {
  }

  @Command(description = "List Activities: List all activities for logged in user")
  public void listActivities()
  {
  }

  // Baseline Commands

  @Command(description = "Add location: Append location to an activity")
  public void addLocation(@Param(name = "activity-id") String id, @Param(name = "longitude") double longitude,
      @Param(name = "latitude") double latitude)
  {
  }

  @Command(description = "ActivityReport: List all activities for logged in user, sorted alphabetically by type")
  public void activityReport()
  {
  }

  @Command(description = "Activity Report: List all activities for logged in user by type. Sorted longest to shortest distance")
  public void activityReport(@Param(name = "byType: type") String sortBy)
  {
  }

  @Command(description = "List all locations for a specific activity")
  public void listActivityLocations(@Param(name = "activity-id") String id)
  {
  }

  @Command(description = "Follow Friend: Follow a specific friend")
  public void follow(@Param(name = "email") String email)
  {
  }

  @Command(description = "List Friends: List all of the friends of the logged in user")
  public void listFriends()
  {
  }

  @Command(description = "Friend Activity Report: List all activities of specific friend, sorted alphabetically by type)")
  public void friendActivityReport(@Param(name = "email") String email)
  {
  }

  // Good Commands

  @Command(description = "Unfollow Friends: Stop following a friend")
  public void unfollowFriend()
  {
  }

  @Command(description = "Message Friend: send a message to a friend")
  public void messageFriend(@Param(name = "email") String email, @Param(name = "message") String message)
  {
  }

  @Command(description = "List Messages: List all messages for the logged in user")
  public void listMessages()
  {
  }

  @Command(description = "Distance Leader Board: list summary distances of all friends, sorted longest to shortest")
  public void distanceLeaderBoard()
  {
  }

  // Excellent Commands

  @Command(description = "Distance Leader Board: distance leader board refined by type")
  public void distanceLeaderBoardByType(@Param(name = "byType: type") String type)
  {
  }

  @Command(description = "Message All Friends: send a message to all friends")
  public void messageAllFriends(@Param(name = "message") String message)
  {
  }

  @Command(description = "Location Leader Board: list sorted summary distances of all friends in named location")
  public void locationLeaderBoard(@Param(name = "location") String message)
  {
  }

  // Outstanding Commands

  // Todo
}