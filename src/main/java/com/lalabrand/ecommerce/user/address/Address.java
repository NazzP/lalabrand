package com.lalabrand.ecommerce.user.address;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.enums.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "country", nullable = false, columnDefinition = "ENUM('UA', 'PL', 'DE', 'US', 'UK')")
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "zip", nullable = false, length = 10)
    private String zip;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "address1", nullable = false, length = 50)
    private String address1;

    @Column(name = "address2", nullable = false, length = 50)
    private String address2;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
