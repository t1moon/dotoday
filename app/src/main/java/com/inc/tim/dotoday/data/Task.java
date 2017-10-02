package com.inc.tim.dotoday.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
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

    /* CATEGORY: 0 - Фокус, 1 - Цель, 2 - Делегирование, 3 - Мусор */
    @Property(nameInDb = "category")
    private int category;

    @Keep
    public Task(Long id, String title, String description, int importance, int category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.importance = importance;
        this.category = category;
    }

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
}
