package de.tuhrig.rsd.review.system.ports.event;

import de.tuhrig.rsd.common.application.EventPublisher;
import de.tuhrig.rsd.review.system.JmsUtil;
import de.tuhrig.rsd.review.system.application.ReviewCheckingResultService;
import de.tuhrig.rsd.review.system.domain.Review;
import de.tuhrig.rsd.review.system.domain.ReviewApprovedEvent;
import de.tuhrig.rsd.review.system.domain.ReviewFixtures;
import de.tuhrig.rsd.review.system.domain.ReviewRepository;
import de.tuhrig.rsd.review.system.domain.ReviewStatus;
import de.tuhrig.rsd.review.system.infrastructure.jms.JmsConfig;
import de.tuhrig.rsd.review.system.infrastructure.memory.ReviewStubRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        JmsConfig.class,
        ReviewApprovedEventListener.class,
        ReviewCheckingResultService.class,
        ReviewStubRepository.class
})
@TestPropertySource({
        "classpath:application.properties"
})
public class ReviewApprovedEventListenerTest {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void should_ApproveReview() {

        Review openFiveStarSmartphoneReview = ReviewFixtures.anInitialFiveStarSmartphoneReview();
        reviewRepository.save(openFiveStarSmartphoneReview);

        ReviewApprovedEvent event = new ReviewApprovedEvent();
        event.setReviewId(openFiveStarSmartphoneReview.getReviewId().getReviewId());
        eventPublisher.publish(event);

        JmsUtil jmsUtil = new JmsUtil(jmsTemplate);
        jmsUtil.waitForAll("Consumer.review_system.VirtualTopic.Events");

        Review review = reviewRepository.find(openFiveStarSmartphoneReview.getReviewId());
        assertThat(review.getReviewStatus()).isEqualTo(ReviewStatus.APPROVED);
    }
}