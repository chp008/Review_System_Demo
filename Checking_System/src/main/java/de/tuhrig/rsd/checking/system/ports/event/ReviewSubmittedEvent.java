package de.tuhrig.rsd.checking.system.ports.event;

import de.tuhrig.rsd.checking.system.domain.ReviewId;
import lombok.Data;

@Data
public class ReviewSubmittedEvent {
    private ReviewId reviewId;
    private String subject;
    private String content;
}