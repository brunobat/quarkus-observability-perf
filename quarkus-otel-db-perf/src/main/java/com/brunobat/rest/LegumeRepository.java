package com.brunobat.rest;

import com.brunobat.rest.data.LegumeItem;
import com.brunobat.rest.model.Legume;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.stream.Stream;

@ApplicationScoped
public class LegumeRepository implements PanacheRepository<Legume> {

    @Inject
    EntityManager manager;

    public Stream<LegumeItem> listLegumes(int pageIndex) {
        return find("SELECT h FROM Legume h")
                .withHint("org.hibernate.cacheable", "true")
                .project(LegumeItem.class)
                .page(pageIndex, 16).stream();
    }

}
