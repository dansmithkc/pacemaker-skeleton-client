package controllers;

import static models.Fixtures.users;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.PacemakerAPIConfiguration;
import models.User;

public class UserTest
{

  PacemakerAPI pacemaker = new PacemakerAPI(PacemakerAPIConfiguration.PACEMAKER_API_URL);
  User homer = new User("homer", "simpson", "homer@simpson.com", "secret");

  @Before
  public void setup()
  {
    pacemaker.deleteUsers();
  }

  @After
  public void tearDown()
  {
  }

  @Test
  public void testCreateUser()
  {
    User user = pacemaker.createUser(homer.firstname, homer.lastname, homer.email, homer.password);
    assertEquals(user, homer);
    User user2 = pacemaker.getUserByEmail(homer.email);
    assertEquals(user2, homer);
  }

  @Test
  public void testCreateUsers()
  {
    users.forEach(user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
    Collection<User> returnedUsers = pacemaker.getUsers();
    assertEquals(users.size(), returnedUsers.size());
  }

  @Test
  public void testFollowFriend()
  {
    users.forEach(user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
    List<User> returnedUsers = pacemaker.getUsers();
    pacemaker.followFriend(returnedUsers.get(0).id, returnedUsers.get(1).id);
  }

  @Test
  public void testListFriendsNone()
  {
    users.forEach(user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
    List<User> returnedUsers = pacemaker.getUsers();
    List<User> friends = pacemaker.listFriends(returnedUsers.get(0).id);
    assertEquals(0, friends.size());
  }

  @Test
  public void testListFriendsOne()
  {
    users.forEach(user -> pacemaker.createUser(user.firstname, user.lastname, user.email, user.password));
    List<User> returnedUsers = pacemaker.getUsers();
    pacemaker.followFriend(returnedUsers.get(0).id, returnedUsers.get(1).id);
    List<User> friends = pacemaker.listFriends(returnedUsers.get(0).id);
    assertEquals(1, friends.size());
  }
}