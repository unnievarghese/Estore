package com.example.Estore.Estore.io.Repositories.Review;

import com.example.Estore.Estore.io.Entity.Review.ReviewEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity,Long> {


    /**
     * Method to fetch reviews based on userId.
     * @param id
     * @return
     */

    @Transactional
    @Query(value =" SELECT * FROM review r WHERE r.user_id=?1",nativeQuery = true)
    Optional<ReviewEntity> findAllByUserId(long id);

    /**
     * Method to fetch review based on productId and userId.
     * @param productId
     * @param id
     * @return
     */
    @Query(value =" SELECT * FROM review r WHERE r.product_id=?1 AND r.user_id=?2",nativeQuery = true)
    Optional<ReviewEntity> findByProductIdAndUserId(Long productId, long id);

    /**
     * Method to check the review based on userId and productId
     * @param id
     * @param productId
     * @return
     */
    @Query(value = "SELECT * FROM review r WHERE r.user_id=?1 AND r.product_id=?2",nativeQuery = true)
    Optional<ReviewEntity> findAllByUserIdAndProductId(long id, Long productId);

    /**
     * Method to delete review based on reviewId and productId
     * @param reviewId
     * @param productId
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM review r WHERE r.review_id=?1 AND r.product_id=?2",nativeQuery = true)
    void deleteReview(Long reviewId, Long productId);

    /**
     * Method to fetch reviews based on productId
     * @param productId
     * @return
     */
    @Query(value = "SELECT * FROM review r WHERE  r.product_id=?1",nativeQuery = true)
    List<ReviewEntity> findByProductId(Long productId);

    /**
     * Method to fetch review based on userId
     * @param id
     * @return
     */
    @Query(value = "SELECT * FROM review r WHERE r.user_id=?1",nativeQuery = true)
    List<ReviewEntity> findReviewByUser(long id);



}
