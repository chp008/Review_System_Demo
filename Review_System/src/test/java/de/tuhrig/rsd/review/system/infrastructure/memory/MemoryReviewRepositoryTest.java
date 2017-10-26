package de.tuhrig.rsd.review.system.infrastructure.memory;

import de.tuhrig.rsd.review.system.domain.Review;
import de.tuhrig.rsd.review.system.domain.ReviewFixtures;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MemoryReviewRepositoryTest {

    @Test
    public void should_SaveReview() throws Exception {
        MemoryReviewRepository memoryReviewRepository = new MemoryReviewRepository();
        Review review = ReviewFixtures.anOpenFiveStarSmartphoneReview();
        memoryReviewRepository.save(review);
        Review loaded = memoryReviewRepository.find(review.getReviewId());

        assertThat(loaded).isEqualTo(review);
    }
}