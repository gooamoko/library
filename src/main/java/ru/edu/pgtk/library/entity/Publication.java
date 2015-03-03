package ru.edu.pgtk.library.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "publications")
public class Publication implements Serializable {
  @Id
  @Column(name = "pub_pcode")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "pub_name", nullable = false, length = 128)
  private String name;
  
  @Column(name = "pub_timestamp", nullable = false)
  @Temporal(javax.persistence.TemporalType.TIMESTAMP)
  private Date timestamp;

  @Column(name = "pub_description", length = 255)
  private String description;

  @Column(name = "pub_data", nullable = false)
  @Basic(fetch = FetchType.LAZY)
  @Lob
  private byte[] data;

  @Column(name = "pub_contenttype", nullable = false)
  private String contentType;

  @Column(name = "pub_filename", nullable = false, length = 255)
  private String fileName;

  @ManyToOne
  @JoinColumn(name = "pub_usrcode", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "pub_catcode", nullable = false)
  private Category category;

  @Transient
  private int categoryCode;

  @PostLoad
  private void updateCode() {
    if (null != category) {
      categoryCode = category.getId();
    }
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
    updateCode();
  }

  public int getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(int categoryCode) {
    this.categoryCode = categoryCode;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
