package com.brunobat.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Legume {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    public Legume(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Legume() {
    }

    public static LegumeBuilder builder() {
        return new LegumeBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(String id) {
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Legume)) return false;
        final Legume other = (Legume) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Legume;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    public static class LegumeBuilder {
        private String id;
        private String name;
        private String description;

        LegumeBuilder() {
        }

        public LegumeBuilder id(String id) {
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