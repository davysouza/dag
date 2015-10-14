package persistence;

/**
 *
 * @author davy
 */
public class DAOException extends java.lang.Exception{

  public DAOException() {
  }

  public DAOException(String message) {
    super(message);
  }

  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }

  public DAOException(Throwable cause) {
    super(cause);
  }
  
}
