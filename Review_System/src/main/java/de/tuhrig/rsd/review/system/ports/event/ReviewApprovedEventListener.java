package de.tuhrig.rsd.review.system.ports.event;

import de.tuhrig.rsd.review.system.application.ReviewCheckingResultService;
import de.tuhrig.rsd.review.system.domain.ReviewId;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewApprovedEventListener {

    private ReviewCheckingResultService reviewCheckingResultService;

    @JmsListener(
            destination = "Consumer.review_system.VirtualTopic.Events",
            selector = "_type = 'REVIEW_APPROVED_EVENT'"
    )
    public void onEvent(ReviewApprovedEvent reviewApprovedEvent) {
        ReviewId reviewId = reviewApprovedEvent.getReviewId();
        reviewCheckingResultService.approve(reviewId);
    }
}