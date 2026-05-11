package com.example.demo.Repositories;


import com.example.demo.Models.Venues;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository

public class VenuesRepository implements GenericRepository<Venues, Long>{

    private final List<Venues> venues = new ArrayList<>();

    @Override
    public List<Venues> findAll() {
        return venues;
    }
    @Override
    public Venues findById(Long id) {
        return venues.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Venues venuess) {
        venues.add(venuess);

    }

    @Override
    public boolean delete(Long id) {
        return venues.removeIf(e -> e.getId().equals(id));
    }

    @Override
    public Venues update(Long id, Venues venues) {
        Venues existing = this.findById(id);

        if (existing != null) {
            existing.setName(venues.getName());
            existing.setAddress(venues.getAddress());
            return existing;
        }

        return null;
    }
}
