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

    @Generated(hash = 80882395)
    public Task(Long id, String title) {
        this.id = id;
        this.title = title;
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

}
