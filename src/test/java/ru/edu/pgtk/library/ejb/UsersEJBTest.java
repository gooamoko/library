package ru.edu.pgtk.library.ejb;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.edu.pgtk.library.entity.User;

public class UsersEJBTest {
  private static EJBContainer container;
  private static UsersEJB ejb;

  @BeforeClass
  public static void setUpClass() throws NamingException {
    Map<String, Object> properties = new HashMap<>();
    properties.put(EJBContainer.MODULES, new File("target/classes"));
    properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "glassfish");
    properties.put(EJBContainer.APP_NAME, "library");
    container = EJBContainer.createEJBContainer(properties);
    ejb = (UsersEJB) container.getContext().lookup("java:global/library/classes/UsersEJB");
  }

  @AfterClass
  public static void tearDownClass() {
    if (null != container) {
      container.close();
    }
  }
  
  @Test
  public void testAuthCorrect() throws Exception {
    System.out.println("get(admin. admin)");
    String login = "admin";
    User result = ejb.get(login, login);
    // Проверим, что метод не вернул null
    assertNotNull(result);
    // Проверим, что логин совпадает
    assertEquals(result.getLogin(), login);
  }

  @Test(expected = EJBException.class)
  public void testAuthLoginNull() throws Exception {
    System.out.println("get(null. admin)");
    String login = "admin";
    User result = ejb.get(null, login);
    // Тут мы оказаться не должны
    assertNotNull(result);
    fail("Exception expected, but not throwed");
  }

  @Test(expected = EJBException.class)
  public void testAuthLoginEmpty() throws Exception {
    System.out.println("get(''. admin)");
    String login = "admin";
    User result = ejb.get("", login);
    // Тут мы оказаться не должны
    assertNotNull(result);
    fail("Exception expected, but not throwed");
  }

  @Test(expected = EJBException.class)
  public void testAuthPasswordNull() throws Exception {
    System.out.println("get(admin. null)");
    String login = "admin";
    User result = ejb.get(login, null);
    // Тут мы оказаться не должны
    assertNotNull(result);
    fail("Exception expected, but not throwed");
  }

  @Test(expected = EJBException.class)
  public void testAuthPasswordEmpty() throws Exception {
    System.out.println("get(admin, '')");
    String login = "admin";
    User result = ejb.get(login, "");
    // Тут мы оказаться не должны
    assertNotNull(result);
    fail("Exception expected, but not throwed");
  }  
}
