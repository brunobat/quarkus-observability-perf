package com.brunobat.rest.resource;


import com.brunobat.rest.LegumeRepository;
import com.brunobat.rest.data.LegumeItem;
import com.brunobat.rest.data.LegumeNew;
import com.brunobat.rest.model.Legume;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;
import static java.util.Arrays.asList;

@ApplicationScoped
public class LegumeResource implements LegumeApi {

    @Inject
    LegumeRepository repository;

    @Transactional
    public Response provision() {
        final LegumeNew carrot = LegumeNew.builder()
                .name("Carrot")
                .description("Root vegetable, usually orange")
                .build();
        final LegumeNew zucchini = LegumeNew.builder()
                .name("Zucchini")
                .description("Summer squash")
                .build();
        return Response.status(CREATED).entity(asList(
                addLegume(carrot),
                addLegume(zucchini))).build();
    }

    @Transactional
    public Response add(@Valid final LegumeNew legumeNew) {
        return Response.status(CREATED).entity(addLegume(legumeNew)).build();
    }

    @Transactional
    public Response delete(@NotEmpty final Long legumeId) {
        return repository.findByIdOptional(legumeId)
                .map(legume -> {
                    repository.delete(legume);
                    return Response.status(NO_CONTENT).build();
                })
                .orElse(Response.status(NOT_FOUND).build());
    }

    public List<LegumeItem> list(int pageIndex) {
//        log.info("someone asked for a list for index: " + pageIndex);
        return repository.listLegumes(pageIndex).toList();
    }

    public LegumeItem addLegume(final @Valid LegumeNew legumeNew) {
        final Legume legumeToAdd = Legume.builder()
                .name(legumeNew.getName())
                .description((legumeNew.getDescription()))
                .build();

        repository.persist(legumeToAdd);

        return getLegumeItem(legumeToAdd);
    }

    private LegumeItem getLegumeItem(final Legume addedLegume) {
        return LegumeItem.builder()
                .id(addedLegume.getId())
                .name(addedLegume.getName())
                .description(addedLegume.getDescription())
                .build();
    }
}
