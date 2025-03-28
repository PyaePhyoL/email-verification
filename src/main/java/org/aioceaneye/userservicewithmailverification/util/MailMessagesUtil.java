package org.aioceaneye.userservicewithmailverification.util;


public class MailMessagesUtil {

    public static String getVerificationMail(String code) {
        return """
                
                """;
    }

    public static String getApproveMail() {
        return """
            <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <title>Account Approved - AiOceanEye</title>
                    <style>
                        /* Reset styles */
                        body { margin: 0; padding: 0; font-family: Arial, sans-serif; line-height: 1.6; }
                        img { border: none; max-width: 100%; }
                       \s
                        /* Main styles */
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background-color: #1a365d; padding: 20px; text-align: center; }
                        .content { padding: 30px 20px; background-color: #f7fafc; }
                        .button {\s
                            display: inline-block;\s
                            padding: 12px 24px;\s
                            background-color: #2b6cb0;\s
                            color: white !important;\s
                            text-decoration: none;\s
                            border-radius: 4px;\s
                            margin: 15px 0;\s
                        }
                        .footer {\s
                            background-color: #1a365d;\s
                            color: #ffffff;\s
                            padding: 20px;\s
                            text-align: center;\s
                            font-size: 12px;\s
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <!-- Header -->
                        <div class="header">
                            <img src="[Your-Logo-URL]" alt="AIOCEANEYE" style="height: 40px;">
                        </div>
            
                        <!-- Content -->
                        <div class="content">
                            <h2 style="color: #2d3748; margin-top: 0;">Welcome Aboard! 🛫</h2>
                           \s
                            <p>Dear [User's Name],</p>
                           \s
                            <p>We're pleased to inform you that your account with <strong>Flight Management System</strong> has been successfully verified and approved by our administration team.</p>
                           \s
                            <p style="text-align: center; margin: 25px 0;">
                                <a href="[Login-URL]" class="button">Access Your Account Now</a>
                            </p>
            
                            <p><strong>Next Steps:</strong></p>
                            <ul>
                                <li>Use your registered email address and password to log in</li>
                                <li>Complete your profile information</li>
                                <li>Explore our flight management features</li>
                            </ul>
            
                            <p><strong>Need Help?</strong><br>
                            Contact our support team at <a href="mailto:support@aioceaneye.com">mailto:support@aioceaneye.com</a> or call +1 (555) 123-4567</p>
                        </div>
            
                        <!-- Footer -->
                        <div class="footer">
                            <p>© 2025 AiOceanEye. All rights reserved.</p>
                            <p>Follow us:\s
                                <a href="[Twitter-URL]" style="color: #a0aec0; text-decoration: none;">Twitter</a> |\s
                                <a href="[LinkedIn-URL]" style="color: #a0aec0; text-decoration: none;">LinkedIn</a>
                            </p>
                            <p style="font-size: 10px; color: #cbd5e0;">
                                This is an automated message. Please do not reply directly to this email.
                            </p>
                        </div>
                    </div>
                </body>
                </html>
            """;
    }


}
