package com.inc.tim.dotoday.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Timur on 22-Sep-17.
 */

@Entity(nameInDb = "task")
public class Task {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "description")
    private String description;

    @Property(nameInDb = "importance")
    private int importance; // from 0 to 100

    @Generated(hash = 925408834)
    public Task(Long id, String title, String description, int importance) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.importance = importance;
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
}
