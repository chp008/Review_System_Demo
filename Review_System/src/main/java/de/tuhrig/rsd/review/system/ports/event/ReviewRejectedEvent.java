package de.tuhrig.rsd.review.system.ports.event;

import de.tuhrig.rsd.review.system.domain.ReviewId;
import lombok.Data;

@Data
public class ReviewRejectedEvent {
    private ReviewId reviewId;
}