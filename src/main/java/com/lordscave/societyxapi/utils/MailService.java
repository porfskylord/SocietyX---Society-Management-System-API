package com.lordscave.societyxapi.utils;

import com.lordscave.societyxapi.core.entity.*;
import com.lordscave.societyxapi.core.entity.enums.Role;
import com.lordscave.societyxapi.core.repository.ResidentRepo;
import com.lordscave.societyxapi.core.repository.SecurityRepo;
import com.lordscave.societyxapi.core.repository.VisitRepo;
import com.lordscave.societyxapi.core.security.JwtService;
import com.lordscave.societyxapi.security_module.dto.req.VIsitMailData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${auth.webpage.baseurl}")
    String baseUrl;

    @Autowired
    private SecurityRepo securityRepo;

    @Autowired
    private ResidentRepo residentRepo;

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private JwtService jwtService;


    @Async
    public CompletableFuture<Boolean> sendVerificationMail(String email, String token) {
        try{
            String verificationLink = baseUrl + "/auth/verify?token=" + token;
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setFrom(fromEmail);

            helper.setSubject("🔐 SocietyX Email Verification");

            String htmlContent = "<html><body>" +
                    "<h2>Welcome to SocietyX 👋</h2>" +
                    "<p>Click the button below to verify your email address:</p>" +
                    "<a href=\"" + verificationLink + "\" " +
                    "style=\"display: inline-block; padding: 12px 24px; " +
                    "font-size: 16px; background-color: #713BF7; color: white; " +
                    "text-decoration: none; border-radius: 6px; font-weight: bold;\">" +
                    "Verify Email</a>" +
                    "<p>If you did not sign up for SocietyX, you can ignore this email.</p>" +
                    "</body></html>";

            helper.setText(htmlContent,true);

            javaMailSender.send(message);

            log.info("Email sent to: " + email);

            return CompletableFuture.completedFuture(true);

        } catch (MessagingException e) {
            log.error("Failed to sent OTP to Email;" + email ,e);
            throw new RuntimeException("Failed to sent OTP to Email;" + email ,e);

        }

    }

    @Async
    public CompletableFuture<Boolean> sendResetPasswordMail(String email, String token) {
        try {
            String resetLink = baseUrl + "/auth/reset-password?token=" + token;
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setFrom(fromEmail);
            helper.setSubject("🔐 Reset Your Password - SocietyX");

            String htmlContent = "<html><body>" +
                    "<h2>Password Reset Request</h2>" +
                    "<p>We received a request to reset your password. Click the button below to proceed:</p>" +
                    "<a href=\"" + resetLink + "\" " +
                    "style=\"display: inline-block; padding: 12px 24px; " +
                    "font-size: 16px; background-color: #FF5722; color: white; " +
                    "text-decoration: none; border-radius: 6px; font-weight: bold;\">" +
                    "Reset Password</a>" +
                    "<p>If you did not request this, you can ignore this email.</p>" +
                    "</body></html>";

            helper.setText(htmlContent, true);

            javaMailSender.send(message);

            log.info("Email sent to: " + email);

            return CompletableFuture.completedFuture(true);

        } catch (MessagingException e) {
            log.error("Failed to send password reset mail to: " + email, e);
            throw new RuntimeException("Failed to send password reset mail to: " + email, e);
        }
    }

    @Async
    public void notifyAllUsers(User user) {
        if (user.getRole() != Role.SECURITY && user.getRole() != Role.RESIDENT) {
            return;
        }

        Society society = user.getSociety();
        if (society == null || society.getUsers() == null) {
            return;
        }

        String joiningUserName;

        if(user.getRole() == Role.SECURITY) {
            Security security = securityRepo.findByUserId(user.getId()).orElse(null);
            if(security == null) {
                return;
            }
            joiningUserName = security.getFullName();
        }
        else {
            Resident resident = residentRepo.findByUserId(user.getId()).orElse(null);
            if(resident == null) {
                return;
            }
            joiningUserName = resident.getFullName();
        }


        String joiningUserRole = user.getRole().name();
        String subject = "New " + joiningUserRole + " Joined Your Society!";
        String message = "A new " + joiningUserRole.toLowerCase() + " named **" + joiningUserName + "** has joined your society: **" + society.getName() + "**.";


        List<String> recipientEmails = society.getUsers().stream()
                .filter(u -> !u.getId().equals(user.getId()) && u.getEmail() != null)
                .map(User::getEmail)
                .toList();


        for (String email : recipientEmails) {
            sendSimpleMail(email, subject, message);
        }
    }


    @Async
    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Async
    public void sendVisitMail(VIsitMailData visitMailData, String approveToken, String rejectToken) {
        try {

            String toEmail = visitMailData.getToEmail();
            String residentName = visitMailData.getResidentName();
            String visitorName = visitMailData.getVisitorName();
            String purpose = visitMailData.getPurpose();
            String phone = visitMailData.getContactNumber();
            LocalDateTime visitTime = visitMailData.getCreatedAt();

            String approveLink = baseUrl + "/visit/approve?token=" + approveToken;
            String rejectLink = baseUrl + "/visit/reject?token=" + rejectToken;

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setFrom(fromEmail);
            helper.setSubject("New Visit Request - SocietyX");

            String htmlBody = "<html><body style='font-family:sans-serif;'>"
                    + "<h3>Hi " + residentName + ",</h3>"
                    + "<p>You have a new visit request:</p>"
                    + "<ul>"
                    + "<li><strong>Visitor Name:</strong> " + visitorName + "</li>"
                    + "<li><strong>Phone:</strong> " + phone + "</li>"
                    + "<li><strong>Purpose:</strong> " + purpose + "</li>"
                    + "<li><strong>Requested Time:</strong> " + visitTime + "</li>"
                    + "</ul>"
                    + "<p>Please take action:</p>"
                    + "<p><i>This link will expire in 15 minutes.</i></p>"
                    + "<a href='" + approveLink + "' style='padding:10px 20px; background:#28a745; color:#fff; text-decoration:none; border-radius:5px;'>Approve</a>&nbsp;&nbsp;"
                    + "<a href='" + rejectLink + "' style='padding:10px 20px; background:#dc3545; color:#fff; text-decoration:none; border-radius:5px;'>Reject</a>"
                    + "<p><br/><i>This visit request will be valid until you take an action.</i></p>"
                    + "<hr/><p>SocietyX</p>"
                    + "</body></html>";

            helper.setText(htmlBody, true);

            javaMailSender.send(message);
            log.info("Visit approval mail sent to resident_module for visit: {}", visitMailData.getContactNumber());

        } catch (Exception e) {
            log.error("Failed to send visit notification email for visit: " + visitMailData.getContactNumber(), e);
            throw new RuntimeException("Failed to send visit notification email for visit: " + visitMailData.getContactNumber(), e);
        }


    }



    @Async
    public void notifyAssignedSecurity(Visit visit) {
        try {
            Security security = visit.getSecurity();
            if (security == null || security.getUser() == null) {
                log.warn("No security_module assigned for visit ID: {}", visit.getId());
                return;
            }

            User securityUser = security.getUser();
            String toEmail = securityUser.getEmail();

            String visitorName = visit.getName();
            String phone = visit.getContactNumber();
            String purpose = visit.getPurpose();
            String residentName = visit.getResident().getFullName();
            String flatNo = visit.getResident().getFlat().getFlatNo();
            String gateName = visit.getGate().getGateName();

            String subject = "Visitor Approved - SocietyX";
            String body = "<html><body>"
                    + "<h3>Visitor Approved for Entry</h3>"
                    + "<p><strong>Visitor Name:</strong> " + visitorName + "</p>"
                    + "<p><strong>Phone:</strong> " + phone + "</p>"
                    + "<p><strong>Purpose:</strong> " + purpose + "</p>"
                    + "<p><strong>Resident:</strong> " + residentName + " (Flat " + flatNo + ")</p>"
                    + "<p><strong>Gate:</strong> " + gateName + "</p>"
                    + "<p><i>Please allow the visitor to enter through your gate.</i></p>"
                    + "<hr><p>SocietyX</p>"
                    + "</body></html>";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setText(body, true);

            javaMailSender.send(message);
            log.info("Security guard notified for visit ID: {}", visit.getId());

        } catch (Exception e) {
            log.error("Failed to notify security_module for visit ID: " + visit.getId(), e);
        }
    }


    @Async
    public void sendVisitingPassToVisitor(VIsitMailData visitMailData) {
        try {
            Visit visit = visitRepo.findById(visitMailData.getVisitId()).orElseThrow(() ->
                    new IllegalArgumentException("Visit not found with ID: " + visitMailData.getVisitId()));

            if (visit.getPermitCode() == null || visit.getPermitCodeExpiry() == null) {
                log.error("Permit code is missing for visitId: {}", visitMailData.getVisitId());
                return;
            }

            String permitCode = visit.getPermitCode();
            String qrUrl = baseUrl + "/visit/validate?code=" + permitCode;

            byte[] qrImage = QRCodeGenerator.generateQRCode(qrUrl, 250, 250);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(visit.getEmail());
            helper.setFrom(fromEmail);
            helper.setSubject("Your Visiting Pass - SocietyX");

            String html = """
                            <html>
                              <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px;">
                                <table style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); padding: 30px;">
                                  <tr>
                                    <td style="text-align: center;">
                                      <h2 style="color: #2c3e50;">🛂 Visiting Pass</h2>
                                      <p style="color: #4b4b4b; font-size: 16px;">Show this at the gate for entry</p>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td style="padding: 10px 0;">
                                      <p><strong>👤 Visitor:</strong> %s</p>
                                      <p><strong>🏡 Resident:</strong> %s</p>
                                      <p><strong>🏠 Flat:</strong> %s</p>
                                      <p><strong>🎯 Purpose:</strong> %s</p>
                                      <p><strong>🔐 Permit Code:</strong> <span style="color: #2e86de;">%s</span></p>
                                      <p><strong>⏰ Expiry:</strong> %s</p>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td style="text-align: center; padding-top: 20px;">
                                      <p style="margin-bottom: 10px;"><strong>📷 Scan QR to validate permit</strong></p>
                                      <img src='cid:qrcode' style="width: 180px; height: 180px;" alt="QR Code"/>
                                    </td>
                                  </tr>
                                </table>
                                <p style="text-align:center; font-size: 12px; color: #aaa; margin-top: 20px;">This is an auto-generated pass. Do not reply.</p>
                              </body>
                            </html>
                            """.formatted(
                                            visit.getName(),
                                            visitMailData.getResidentName(),
                                            visitMailData.getFlatNo(),
                                            visit.getPurpose(),
                                            permitCode,
                                            visit.getPermitCodeExpiry()
                                    );

            helper.setText(html, true);
            helper.addInline("qrcode", new ByteArrayResource(qrImage), "image/png");

            log.info("Sending visiting pass email for visitId: {}", visitMailData.getVisitId());
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send visiting pass email for visitId: {}", visitMailData.getVisitId(), e);
        }
    }

}
