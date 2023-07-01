package com.bs.perform.repositories;

import com.bs.perform.models.venue.Venue;

public interface VenueRepository {

    void createVenue(Venue venue);

    void updateVenue(String id, Venue venue);

    Venue getVenueById(String id);
}
