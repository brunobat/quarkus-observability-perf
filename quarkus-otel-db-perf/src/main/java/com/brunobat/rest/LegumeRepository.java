package com.brunobat.rest;

import com.brunobat.rest.data.LegumeItem;
import com.brunobat.rest.model.Legume;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.stream.Stream;

@Repository
public interface LegumeRepository extends CrudRepository<Legume, Long> {

	// In Jakarta Data 1.1, we will be able to replace this with:
	// @Find(Legume.class)
	@Query("SELECT h.id, h.name, h.description FROM Legume h")
	Stream<LegumeItem> listLegumes(PageRequest pageRequest);

}
