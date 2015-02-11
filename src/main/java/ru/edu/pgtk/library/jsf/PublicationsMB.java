package ru.edu.pgtk.library.jsf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

  public void download(Publication item) throws IOException {
    // Get the FacesContext
    FacesContext facesContext = FacesContext.getCurrentInstance();
    // Get HTTP response
    ExternalContext ec = facesContext.getExternalContext();
    // Set response headers
    ec.responseReset();   // Reset the response in the first place
    ec.setResponseContentType(item.getContentType());  // Set only the content type
    // Установка данного заголовка будет иннициировать процесс скачки файла вместо его отображения в браузере.
    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + item.getFileName() + "\"");
    try (OutputStream responseOutputStream = ec.getResponseOutputStream()) {
      responseOutputStream.write(item.getData());
      responseOutputStream.flush();
    } catch (IOException e) {
      addMessage(e);
    }
    facesContext.responseComplete();
  }

  @Override
  public void add() {
    if (null != session) {
      User user = session.getUser();
      if (null != user) {
        item = new Publication();
        item.setUser(user);
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
        item.setData(IOUtils.toByteArray(data.getInputStream()));
        item.setContentType(data.getContentType());
        item.setFileName(data.getSubmittedFileName());
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
