package com.tic.service;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Claim Service. Store claims in the DB
 * @author Phillip Kruger (phillip.kruger@gmail.com)
 */
@ApplicationScoped
public class ClaimService {

    private final Map<Long,Claim> db = new HashMap<>();
    
    public List<Claim> getClaims() { 
        return new ArrayList<Claim>(db.values());
    }
    
    
    
    
    
    // For now we hardcore some initital claim. Maybe this should be in a folder with a txt/json file ?
    @PostConstruct
    public void init(){
        
        // dummy claim 1
        Claim claim1 = new Claim();
        claim1.id = 1L;
        
        ClaimCustomerInput email1 = new ClaimCustomerInput();
        email1.from = "Koos van der Merwe<koos.vandermerwe@gmail.com>";
        email1.subject = "Report of Car Accident Involving Policy No. 123456789";
        email1.sendTime = LocalDateTime.now();
        email1.body = """
                      Dear Sir or Madam,
                      
                      I hope this email finds you well. I am writing to inform you of a regrettable incident involving my vehicle, which is insured under Policy No. 123456789 with The Insurance Company.
                      
                      On the morning of March 26th, at approximately 8:45 AM, I was involved in a car accident at the intersection of 5th Avenue and Maple Street. While I was driving southbound on 5th Avenue, adhering to the traffic rules and speed limits, another vehicle unexpectedly ran the red light at Maple Street and collided with the passenger side of my car. 
                      Thankfully, there were no injuries to either party involved, but my vehicle sustained significant damage.
                      
                      I have already filed a report with the local police department, and I have a copy of the report, along with photographs of the damage to my vehicle and the scene of the accident. I would be more than willing to provide these documents, or any other information you might need, to facilitate the claims process.
                      
                      I understand that accidents are stressful for all parties involved, and I appreciate the support and guidance The Insurance Company can provide during this time. Could you please advise me on the next steps in the claims process? I am eager to resolve this matter efficiently and am hopeful for a smooth resolution.
                      
                      Thank you very much for your time and assistance. I look forward to your prompt response.
                      
                      Warm regards,
                      
                      Koos van der Merwe
                      0833906895
                      koos.vandermerwe@gmail.com
                      """;
        email1.images = List.of("/demo/email1.png");
        claim1.claimCustomerInput = email1;
        
        claim1.claimSummary = generateClaimSummary(claim1.id, email1);
        
        db.put(claim1.id, claim1);
        
        
        // dummy claim 2
        
        Claim claim2 = new Claim();
        claim2.id = 2L;
        
        ClaimCustomerInput email2 = new ClaimCustomerInput();
        email2.from = "Phillip Kruger<phillip.kruger@gmail.com>";
        email2.subject = "Immediate Action Required: Car Accident Claim - Policy #230743239";
        email2.sendTime = LocalDateTime.now();
        email2.body = """
                      To Whom It May Concern at The Insurance Company,
                      
                      I am writing, yet again, to deal with an issue that frankly shouldn't be as complicated as you make it. On March 25th, 2024, around 2:45 PM, I was involved in a car accident - not that it seems to matter to your company unless I write in and spell it out.
                      
                      For your records, since I assume you'll need them, my policy number is 123456789. The accident took place at the intersection of Main Street and Second Avenue, where another driver clearly ignored the stop sign and hit my car. I expect you to handle this efficiently, given the premiums I've been paying.
                      
                      I've done the legwork of getting a police report and taking pictures of the damage, which I assume you'll want to see. It's about time for some actual service, so I'd appreciate it if you could expedite this process and not drag your feet, as has been my experience in the past.
                      
                      What's the next step? And I mean immediately. I expect a detailed response on how you're going to resolve this without causing me further inconvenience.
                      
                      Sincerely,
                      
                      Phillip Kruger
                      0473620721
                      phillip.kruger@gmail.com
                      """;
        email2.images = List.of("/demo/email2.png");
        claim2.claimCustomerInput = email2;
        
        claim2.claimSummary = generateClaimSummary(claim2.id, email2);
        
        db.put(claim2.id, claim2);
        
    }
    
    private ClaimSummary generateClaimSummary(Long claimId, ClaimCustomerInput claimCustomerInput){
        // TODO: Let AI generate the summary
        // For now I hardcode
        
        
        ClaimSummary claimSummary = new ClaimSummary();
        if(claimId==1L){
            claimSummary.subject = "Car Accident Report - Policy Number: 123456789";
            claimSummary.policyNumber = "123456789";
            claimSummary.timeOfEvent = LocalDateTime.of(2024, Month.MARCH, 26, 8, 45);
        }else if(claimId==2L){
            claimSummary.subject = "Immediate Action Required: Car Accident Claim - Policy #230743239";
            claimSummary.policyNumber = "230743239";
            claimSummary.timeOfEvent = LocalDateTime.of(2024, Month.MARCH, 25, 14, 45);
        }else{
            
        }
        
        claimSummary.customerSentiment = "Here the sentiment";
        claimSummary.locationOfEvent = "Here the location";
        
        
        claimSummary.summary = """
                               Here the summary.
                               
                               The AI engine should create this.
                               
                               Bla bla bla
                               """;
        claimSummary.images = List.of("/demo/email" + claimId + ".png");
        
        return claimSummary;
    }
}
