package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.io.Serializable;

import com.google.common.base.Objects;

public class UserDistance implements Serializable
{

  public String name;
  public double distance;

  public UserDistance()
  {
  }

  public String getName()
  {
    return name;
  }

  public double getDistance()
  {
    return distance;
  }

  public UserDistance(String name, double distance)
  {
    this.name = name;
    this.distance = distance;
  }

  public UserDistance(IUserName userName, double distance)
  {
    this.name = userName.getName();
    this.distance = distance;
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof UserDistance)
    {
      final UserDistance other = (UserDistance) obj;
      return Objects.equal(name, other.name) && Objects.equal(distance, other.distance);
    }
    else
    {
      return false;
    }
  }

  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(name).addValue(distance).toString();
  }

  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.name, this.distance);
  }
}