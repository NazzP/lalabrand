package com.lalabrand.ecommerce.item.item_info;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.item.enums.ColorEnum;
import com.lalabrand.ecommerce.item.size.Size;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_info")
public class ItemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
    private Item item;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "color", nullable = false, columnDefinition = "ENUM('WHITE', 'BLACK', 'GREY', 'YELLOW', 'RED', 'BLUE', 'GREEN', 'BROWN', 'PINK', 'ORANGE', 'PURPLE')")
    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    @Column(name = "image")
    private String image;

    @ManyToMany
    @JoinTable(
            name = "items_sizes",
            joinColumns = @JoinColumn(name = "item_info_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    private Set<Size> sizes = new LinkedHashSet<>();

    @Column(name = "is_color_available", nullable = false)
    private Boolean isColorAvailable = false;

}
