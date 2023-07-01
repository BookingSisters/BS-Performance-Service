package com.bs.perform.dtos.response;

import com.bs.perform.models.venue.Seat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VenueGetResponseDto {

    private String id;
    private String name;
    private String location;
    private List<Seat> seatList;
}
