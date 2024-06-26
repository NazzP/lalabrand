package com.lalabrand.ecommerce.user.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, String> {
    Optional<Wishlist> findWishlistByUserId(String userId);
}
