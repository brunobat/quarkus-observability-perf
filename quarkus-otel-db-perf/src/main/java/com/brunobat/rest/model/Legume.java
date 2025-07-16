package com.brunobat.rest.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Cacheable
public class Legume {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    public Legume(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Legume() {
    }

    public static LegumeBuilder builder() {
        return new LegumeBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Legume(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ")";
    }

    public static class LegumeBuilder {
        private Long id;
        private String name;
        private String description;

        LegumeBuilder() {
        }

        public LegumeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LegumeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LegumeBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Legume build() {
            return new Legume(this.id, this.name, this.description);
        }

        public String toString() {
            return "Legume.LegumeBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ")";
        }
    }
}