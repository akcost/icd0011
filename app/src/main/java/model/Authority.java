package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Embeddable
@NoArgsConstructor
public class Authority {

    @NonNull
    @Column(name = "authority")
    private String userAuthority;

}
