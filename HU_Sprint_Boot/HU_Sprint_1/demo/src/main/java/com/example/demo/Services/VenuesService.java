package com.example.demo.Services;

import com.example.demo.Models.Event;
import com.example.demo.Models.Venues;
import com.example.demo.Repositories.VenuesRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VenuesService {
    private final VenuesRepository repository;

    public VenuesService(VenuesRepository repository) {
        this.repository = repository;
    }

    public List<Venues> findAll() {
        return repository.findAll();
    }

    public Venues findById(Long id) {
        return repository.findById(id);
    }

    public Venues create(Venues venues) {
        if (venues.getName() == null || venues.getName().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }
        if (venues.getId() == null ){
            throw new RuntimeException("debe de contener un id Unico");
        }
        else if(venues.getAddress() == null || venues.getAddress().isEmpty()) {
            throw new RuntimeException("La Descripcion es obligatorio");
        }
        repository.save(venues);
        return venues;
    }

    public boolean delete(Long id) {

        if (id == null){
            throw new RuntimeException("debe de contener un id Unico");
        }
        return repository.delete(id);
    }

    public boolean update(Long id, Venues updatedVenues) {
        if (updatedVenues.getName() == null || updatedVenues.getName().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio para actualizar");
        }
        if (updatedVenues.getId() == null || updatedVenues.getId() == updatedVenues.getId()){
            throw new RuntimeException("debe de contener un id Unico para actualizar");
        }
        else if(updatedVenues.getAddress() == null || updatedVenues.getAddress().isEmpty()) {
            throw new RuntimeException("La Descripcion es obligatorio para actualizar");
        }
        Venues resultado = repository.update(id, updatedVenues);
        return (resultado != null);
    }


}
