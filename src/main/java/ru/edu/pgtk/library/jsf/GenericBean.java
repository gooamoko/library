package ru.edu.pgtk.library.jsf;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Шаблон для управляемых бинов, реализующий 2 трети функционала.
 *
 * @param <T> Класс, с которым работает управляемый бин.
 */
public abstract class GenericBean<T> {

  protected transient T item;
  protected boolean edit;
  protected boolean delete;
  protected boolean details;
  protected boolean error;

  @PostConstruct
  protected void resetState() {
    edit = false;
    delete = false;
    details = false;
    error = false;
    item = null;
  }

  public abstract void add();
  
  protected void addMessage(final Exception e) {
    FacesContext context = FacesContext.getCurrentInstance();
    String message = "Exception class " + e.getClass().getName() + " with message " + e.getMessage();
    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "Error"));
  }

  protected void addMessage(final String message) {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "Error"));
  }

  public T getItem() {
    return item;
  }

  public boolean isEdit() {
    return edit;
  }

  public boolean isDelete() {
    return delete;
  }

  public boolean isBrowse() {
    return !(error || edit || delete || details);
  }

  public boolean isDetails() {
    return details;
  }

  public boolean isError() {
    return error;
  }

  public void cancel() {
    resetState();
  }

  public void delete(final T item) {
    this.item = item;
    delete = true;
  }

  public void edit(final T item) {
    this.item = item;
    edit = true;
  }

  public void details(final T item) {
    this.item = item;
    details = true;
  }
}
