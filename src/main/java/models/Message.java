package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.io.Serializable;

import com.google.common.base.Objects;

public class Message implements Serializable
{
  public String text;

  public Message()
  {
  }

  public String getText()
  {
    return text;
  }

  public Message(String text)
  {
    this.text = text;
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Message)
    {
      final Message other = (Message) obj;
      return Objects.equal(text, other.text);
    }
    else
    {
      return false;
    }
  }

  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(text).toString();
  }

  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.text);
  }
}