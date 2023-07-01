package com.bs.perform.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.models.venue.Seat;
import com.bs.perform.models.venue.Venue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@ExtendWith(MockitoExtension.class)
class VenueRepositoryImplTest {

    @InjectMocks
    private VenueRepositoryImpl venueRepository;

    @Mock
    private DynamoDbTable<Venue> venueDynamoDbTable;

    Venue venue;

    @BeforeEach
    public void setUp(){
        venue = getVenue();
    }

    @Test
    @DisplayName("공연장 생성 테스트")
    void createVenueTest() {

        doNothing().when(venueDynamoDbTable).putItem(venue);

        venueRepository.createVenue(venue);

        verify(venueDynamoDbTable, times(1)).putItem(venue);
    }

    @Test
    @DisplayName("공연 업데이트 테스트")
    void updatePerformanceTest() {

        String id = venue.getId();
        doReturn(venue).when(venueDynamoDbTable).getItem(Key.builder().partitionValue(id).build());
        doReturn(venue).when(venueDynamoDbTable).updateItem(venue);

        venueRepository.updateVenue(id, venue);

        verify(venueDynamoDbTable, times(1)).updateItem(venue);
    }

    @Test
    @DisplayName("공연장 아이디로 조회 테스트")
    void getVenueByIdTest() {

        String id = venue.getId();
        doReturn(venue).when(venueDynamoDbTable).getItem(Key.builder().partitionValue(id).build());

        Venue resultVenue = venueRepository.getVenueById(id);

        assertThat(resultVenue).isEqualTo(venue);
    }

    private Venue getVenue() {
        List<Seat> seatList = new ArrayList<>();
        seatList.add(Seat.builder().row('A').col(1).build());
        seatList.add(Seat.builder().row('A').col(2).build());
        seatList.add(Seat.builder().row('A').col(3).build());

        return Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .seatList(seatList)
            .build();
    }
}