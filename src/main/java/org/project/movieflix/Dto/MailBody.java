package org.project.movieflix.Dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text, Integer otp) {

}
