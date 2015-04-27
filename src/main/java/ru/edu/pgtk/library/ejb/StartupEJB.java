package ru.edu.pgtk.library.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import ru.edu.pgtk.library.entity.User;

/**
 * EJB для инициализации некоторых параметров. Например, этот бин будет
 * создавать пользователя, если ни одного пользователя в системе не
 * зарегистрировано.
 */
@Startup
@Singleton
public class StartupEJB {

  @EJB
  private UsersEJB persons;

  @PostConstruct
  private void setupApplication() {
    try {
      if (persons.fetchAdmins().isEmpty()) {
        User admin = new User();
        admin.setAdmin(true);
        admin.setFullName("Администратор проекта");
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setConfirm("admin");
        admin.updatePassword();
        persons.save(admin);
      }
    } catch (Exception e) {
      // Ничего не делаем
    }
  }

  @PreDestroy
  private void shutdownApplication() {
    // Может что-то и будет в дальнейшем, но пока ничего тут делать не надо
  }
}
