package ru.edu.pgtk.library.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
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
    
    @Column(name = "pub_description", length = 255)
    private String description;
    
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
}
