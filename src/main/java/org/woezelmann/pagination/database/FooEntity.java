package org.woezelmann.pagination.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "foo")
public class FooEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "text")
    private String text;

}
