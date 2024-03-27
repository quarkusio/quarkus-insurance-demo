package com.tic.service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represent a Claim as provided by the customer (something like an email)
 * @author Phillip Kruger (phillip.kruger@gmail.com)
 */
public class ClaimCustomerInput {
    public String subject;
    public String from;
    public LocalDateTime sendTime;
    public String body;
    public List<String> images;
}
