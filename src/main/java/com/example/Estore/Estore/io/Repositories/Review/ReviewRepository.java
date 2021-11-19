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





    @Transactional

    @Query(value =" SELECT * FROM review r WHERE r.user_id=?1",nativeQuery = true)
    Optional<ReviewEntity> findAllByUserId(long id);


    @Query(value =" SELECT * FROM review r WHERE r.product_id=?1 AND r.user_id=?2",nativeQuery = true)
    Optional<ReviewEntity> findByProductIdAndUserId(Long productId, long id);



    @Query(value = "SELECT * FROM review r WHERE r.user_id=?1 AND r.product_id=?2",nativeQuery = true)
    Optional<ReviewEntity> findAllByUserIdAndProductId(long id, Long productId);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM review r WHERE r.review_id=?1 AND r.product_id=?2",nativeQuery = true)
    void deleteReview(Long reviewId, Long productId);

    @Query(value = "SELECT * FROM review r WHERE  r.product_id=?1",nativeQuery = true)
    List<ReviewEntity> findByProductId(Long productId);
    @Query(value = "SELECT * FROM review r WHERE r.user_id=?1",nativeQuery = true)
    List<ReviewEntity> findReviewByUser(long id);


}
