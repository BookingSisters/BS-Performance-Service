package com.bs.perform.repositories;

import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.venue.Venue;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;


@Repository
@RequiredArgsConstructor
@Slf4j
public class VenueRepositoryImpl implements VenueRepository {

    private final DynamoDbTable<Venue> venueTable;

    @Override
    public void createVenue(final Venue venue) {

        venueTable.putItem(venue);
    }

    @Override
    public void updateVenue(final String id, final Venue venue) {

        Venue venueForUpdate = venueTable.getItem(Key.builder().partitionValue(id).build());
        if (venue == null) {
            throw new ResourceNotFoundException(id);
        }

        BeanUtils.copyProperties(venue, venueForUpdate, "id");
        venueForUpdate.setLastModifiedDate(LocalDateTime.now());

        venueTable.updateItem(venueForUpdate);
    }

    @Override
    public Venue getVenueById(final String id) {

        Venue venue = venueTable.getItem(Key.builder().partitionValue(id).build());
        if (venue == null) {
            throw new ResourceNotFoundException(id);
        }

        return venue;
    }
}
