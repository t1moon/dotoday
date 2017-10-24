package com.inc.tim.dotoday.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity(nameInDb = "task_db")
public class Task {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "description")
    private String description;

    @Property(nameInDb = "importance")
    private int importance; // from 0 to 100

    /* CATEGORY: 0 - Фокус, 1 - Цель, 2 - Делегирование, 3 - Мусор */
    @Property(nameInDb = "category")
    private int category;

    @Property(nameInDb = "is_completed")
    private boolean is_completed;

    @Property(nameInDb = "is_deleted")
    private boolean is_deleted;

    @Property(nameInDb = "created")
    private Date created;
    @Generated(hash = 2072274838)
    public Task(Long id, String title, String description, int importance,
            int category, boolean is_completed, boolean is_deleted, Date created) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.importance = importance;
        this.category = category;
        this.is_completed = is_completed;
        this.is_deleted = is_deleted;
        this.created = created;
    }

    @Generated(hash = 733837707)
    public Task() {
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean is_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }

    public boolean is_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean getIs_completed() {
        return this.is_completed;
    }

    public boolean getIs_deleted() {
        return this.is_deleted;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
