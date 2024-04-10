package com.lalabrand.ecommerce.user.wishlist;

import com.lalabrand.ecommerce.item.Item;
import com.lalabrand.ecommerce.utils.CommonUtils;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final Logger logger = LoggerFactory.getLogger(WishlistService.class);

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Optional<WishlistDTO> findWishlistByUserId(String userId) {
        try {
            Optional<Wishlist> wishlist = wishlistRepository.findWishlistByUserId(userId);
            if (wishlist.isPresent() && wishlist.get().getItems() != null && !wishlist.get().getItems().isEmpty()) {
                logger.info("Wishlist found for user with ID: {}", userId);
                return wishlist.map(WishlistDTO::fromEntity);
            } else {
                logger.info("No wishlist with items found for user with ID: {}", userId);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Failed to get wishlist for user with ID: {}", userId, e);
            throw new EntityNotFoundException("Failed to find wishlist for user with ID: " + userId, e);
        }
    }

    public WishlistDTO addItemToWishlist(String itemId, String userId) {
        if (CommonUtils.isIdInvalid(itemId) || CommonUtils.isIdInvalid(userId)) {
            logger.error("ItemID: " + itemId + " or userId: " + userId + "is invalid");
            throw new IllegalArgumentException("itemId or userId is invalid");
        }
        Optional<Wishlist> existWishlist = wishlistRepository.findWishlistByUserId(userId);
        if (existWishlist.isEmpty() || existWishlist.get().getItems() == null) {
            return WishlistDTO.fromEntity(wishlistRepository.save(new Wishlist(userId, Collections.singleton(itemId))));
        }
        if (existWishlist.get().getItems().stream().anyMatch(item -> item.getId().equals(itemId))) {
            logger.error("Item with id: " + itemId + " already exist" + " in user cart ( user id:" + userId + " )");
            throw new IllegalArgumentException("An item with this ID is already in the wishlist");
        }
        existWishlist.get().getItems().add(new Item(itemId));
        WishlistDTO wishlist = WishlistDTO.fromEntity(wishlistRepository.save(existWishlist.get()));
        logger.debug("Item with id: " + itemId + "successfully added to wishlist");
        return wishlist;
    }

    public WishlistDTO addItemsToWishlist(Set<String> itemsIds, String userId) {
        if (CommonUtils.isIdsInvalid(itemsIds) || CommonUtils.isIdInvalid(userId)) {
            logger.error("One of item ids " + itemsIds + " is not valid");
            throw new IllegalArgumentException("One of itemsIds or userId is invalid");
        }
        Optional<Wishlist> existWishlist = wishlistRepository.findWishlistByUserId(userId);
        if (existWishlist.isEmpty() || existWishlist.get().getItems() == null) {
            return WishlistDTO.fromEntity(wishlistRepository.save(new Wishlist(userId, itemsIds)));
        }
        if (existWishlist.get().getItems().stream()
                .anyMatch(item -> itemsIds.contains(item.getId()))) {
            logger.error("One of item ids " + itemsIds + " is already in the wishlist");
            throw new IllegalArgumentException("One of the items with this ID is already in the wishlist");
        }
        existWishlist.get().getItems().addAll(itemsIds.stream().map(Item::new).toList());
        logger.debug("Items with ids: " + itemsIds + "successfully added to wishlist");
        return WishlistDTO.fromEntity(wishlistRepository.save(existWishlist.get()));
    }
}
