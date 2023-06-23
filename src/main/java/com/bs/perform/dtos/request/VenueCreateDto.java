package com.bs.perform.dtos.request;

import com.bs.perform.models.Venue;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name field must not be empty")
    private String name;

    @NotBlank(message = "Location field must not be empty")
    private String location;

    public Venue toEntity() {
        return Venue.builder()
            .name(this.name)
            .location(this.location)
            .build();
    }

}
