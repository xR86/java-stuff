
public abstract class Persons {

  protected String name;
  protected String email;

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * isFree() checks if the person has any projects allocated
   * @return
   */
  protected abstract int isFree();

}