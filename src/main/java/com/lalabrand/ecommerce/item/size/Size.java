package com.lalabrand.ecommerce.item.size;

import com.lalabrand.ecommerce.item.enums.SizeType;
import com.lalabrand.ecommerce.item.item_info.ItemInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "ENUM('SHOES','CLOTHES')")
    private SizeType sizeType;

    @Column(name = "value", nullable = false, length = 40)
    private String value;

    @ManyToMany(mappedBy = "sizes")
    private Set<ItemInfo> items = new LinkedHashSet<>();
}
