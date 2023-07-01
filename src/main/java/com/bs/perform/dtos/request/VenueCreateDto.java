package com.bs.perform.dtos.request;

import com.bs.perform.models.venue.Seat;
import com.bs.perform.models.venue.Venue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class VenueCreateDto {

    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotEmpty
    private List<Seat> seatList;

    public Venue toEntity() {
        return Venue.builder()
            .name(this.name)
            .location(this.location)
            .seatList(this.seatList)
            .build();
    }

}
