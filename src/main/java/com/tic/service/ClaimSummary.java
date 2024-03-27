package com.tic.service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represent a Claim as summarized by AI  
 * @author Phillip Kruger (phillip.kruger@gmail.com)
 */
public class ClaimSummary {
    public String policyNumber;
    public String subject;
    public String customerSentiment;
    public LocalDateTime timeOfEvent;
    public String locationOfEvent;
    public String summary;
    public List<String> images;
}
