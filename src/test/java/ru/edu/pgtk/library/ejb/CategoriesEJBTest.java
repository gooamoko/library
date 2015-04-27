package ru.edu.pgtk.library.ejb;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import ru.edu.pgtk.library.entity.Category;

public class CategoriesEJBTest {

  private static EJBContainer container;
  private static CategoriesEJB instance;
  private final static String EXCEPTION = "Unexpected exception with message ";

  @BeforeClass
  public static void setUpClass() throws NamingException {
    Map<String, Object> properties = new HashMap<>();
    properties.put(EJBContainer.MODULES, new File("target/classes"));
    properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "glassfish");
    properties.put(EJBContainer.APP_NAME, "library");
    container = EJBContainer.createEJBContainer(properties);
    instance = (CategoriesEJB) container.getContext().lookup("java:global/library/classes/CategoriesEJB");
  }

  @AfterClass
  public static void tearDownClass() {
    if (null != container) {
      container.close();
    }
  }

  @Test(expected = EJBException.class)
  public void testGetWrong() throws Exception {
    System.out.println("Метод get (неправильный параметр)");
    int id = 0;
    Category result = instance.get(id);
    assertNull(result);
    fail("The test case is a prototype.");
  }

  @Test
  public void testFetchAll() throws Exception {
    try {
      System.out.println("Метод fetchAll");
      List<Category> result = instance.fetchAll();
      assertNotNull(result);
      assertTrue(result.size() > 0);
      assertTrue(result.get(0) instanceof Category);
    } catch (Exception e) {
      fail(EXCEPTION + e.getMessage());
    }
  }

  @Ignore
  @Test
  public void testFindByName() throws Exception {
    try {
      System.out.println("Метод findByName");
      String name = "testCategory";
      List<Category> result = instance.findByName(name);
      assertNotNull(result);
      assertTrue(result.size() > 0);
      assertTrue(result.get(0) instanceof Category);
    } catch (Exception e) {
      fail(EXCEPTION + e.getMessage());
    }
  }

  @Test
  public void testSaveUpdateAndDelete() throws Exception {
    try {
      System.out.println("Метод save (добавление записи)");
      Category item = new Category();
      String name = "Test";
      item.setName(name);
      Category result = instance.save(item);
      assertNotNull(result);
      assertEquals(result.getName(), name);
      System.out.println("Метод save (изменение записи)");
      name = "New Test";
      result.setName(name);
      item = instance.save(result);
      assertNotNull(item);
      assertEquals(item.getName(), name);
      System.out.println("Метод delete (корректный параметр)");
      instance.delete(item);
      System.out.println("Метод delete (некорректный параметр)");
      instance.delete(result);
    } catch (Exception e) {
      fail(EXCEPTION + e.getMessage());
    }
  }
}
