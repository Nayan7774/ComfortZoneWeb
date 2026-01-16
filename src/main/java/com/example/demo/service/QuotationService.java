package com.example.demo.service;


import org.springframework.stereotype.Service;

import com.example.demo.repository.QuotationRepository;

@Service
public class QuotationService {
	 private final QuotationRepository quotationRepository;
	    private final MailService mailService; // use your existing service

	    public QuotationService(QuotationRepository quotationRepository, MailService mailService) {
	        this.quotationRepository = quotationRepository;
	        this.mailService = mailService;
	    }

		/*
		 * public QuotationRequest saveQuotation(QuotationRequest quotation) {
		 * QuotationRequest saved = quotationRepository.save(quotation);
		 * 
		 * // Send email to admin using your existing mail service
		 * sendAdminNotification(saved);
		 * 
		 * return saved; }
		 */

		/*
		 * private void sendAdminNotification(QuotationRequest q) { String subject =
		 * "New Quotation Submitted"; String body =
		 * "A new quotation has been submitted:\n\n" + "Property Type: " +
		 * q.getPropertyType() + "\n" + "City: " + q.getCity() + "\n" + "Unit Type: " +
		 * q.getUnitType() + "\n" + "Name: " + q.getName() + "\n" + "Mobile: " +
		 * q.getMobile() + "\n" + "Email: " + q.getEmail() + "\n" + "Submitted At: " +
		 * q.getCreatedAt();
		 * 
		 * // Use your MailService mailService.sendMail("nayanmahajan2002@gmail.com",
		 * subject, body); }
		 */
}