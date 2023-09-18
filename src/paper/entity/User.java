package paper.entity;

public class User {
  String name;
  String key;
  String type;

  public User(String Name, String Key, String Type) {
    this.name = Name;
    this.key = Key;
    this.type = Type;
  }

  public String getName() {
    return this.name;
  }

  public String getKey() {
    return this.key;
  }

  public String getType() {
    return this.type;
  }
}
