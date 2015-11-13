package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MyUpgradeDomain.
 */
@Entity
@Table(name = "my_upgrade_domain")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "myupgradedomain")
public class MyUpgradeDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "my_date", nullable = false)
    private ZonedDateTime myDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getMyDate() {
        return myDate;
    }

    public void setMyDate(ZonedDateTime myDate) {
        this.myDate = myDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyUpgradeDomain myUpgradeDomain = (MyUpgradeDomain) o;

        if ( ! Objects.equals(id, myUpgradeDomain.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyUpgradeDomain{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", myDate='" + myDate + "'" +
            '}';
    }
}
