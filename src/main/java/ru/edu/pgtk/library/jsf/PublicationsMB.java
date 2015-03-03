package ru.edu.pgtk.library.jsf;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import ru.edu.pgtk.library.ejb.PublicationsEJB;
import ru.edu.pgtk.library.entity.Publication;
import ru.edu.pgtk.library.entity.User;

@ManagedBean(name = "publicationsMB")
@ViewScoped
public class PublicationsMB extends GenericBean<Publication> implements Serializable {

  @EJB
  private PublicationsEJB ejb;
  @ManagedProperty("#{sessionMB}")
  private SessionMB session;
  private Part data;

  public boolean isCanDownload() {
    if (null == item) {
      return false;
    }
    return !((null == item.getFileName()) || (item.getFileName().isEmpty()));
  }
  
  @Override
  public void add() {
    if (null != session) {
      User user = session.getUser();
      if (null != user) {
        item = new Publication();
        item.setUser(user);
        item.setFileName("");
        item.setContentType("");
        item.setTimestamp(new Date());
        edit = true;
      } else {
        addMessage("Вы не можете добавить публикацию!");
      }
    } else {
      addMessage("Проблема с получением данных из сессионного компонента!");
    }
  }
  
  public void confirmDelete() {
    try {
      if ((null != item) && delete) {
        ejb.delete(item);
        resetState();
      }
    } catch (Exception e) {
      addMessage(e);
    }
  }

  public void save() {
    try {
      if ((null != item) && edit) {
        if (null != data) {
          item.setData(IOUtils.toByteArray(data.getInputStream()));
          item.setContentType(data.getContentType());
          item.setFileName(data.getSubmittedFileName());
        }
        ejb.save(item);
        resetState();
      }
    } catch (Exception e) {
      addMessage(e);
    }
  }

  public Part getData() {
    return data;
  }

  public void setData(Part data) {
    this.data = data;
  }

  public void setSession(SessionMB session) {
    this.session = session;
  }
}
