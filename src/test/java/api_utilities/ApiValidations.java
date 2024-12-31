package api_utilities;

import helpers.EmailUtility;

public class ApiValidations {

    public void sendSuccessMail(String apiName, String apiURL, int statusCode){

        String subject= "Api Success Notification: " + apiName;
        String body= "The API call to "+ apiURL+ " was successful.\n" +
                     "Status Code: " + statusCode + "\n" + "Everything worked as expected.\n\n" +
                     "Best regards,\nYour Automated System";
        
        EmailUtility.sendEmail(subject, body);
    }

    public void sendFailedMail(String apiName, String apiURL, int statusCode, String errorMessage){

        String subject= "Api Failed Notification: " + apiName;
        String body =   "The API call to " + apiURL + " has failed.\n" +
                        "Status Code: " + statusCode + "\n" + 
                        "Error Message: " + errorMessage + "\n\n" + 
                        "Please investigate the issue.\n\n" +
                        "Best regards,\nYour Automated System";
        
        EmailUtility.sendEmail(subject, body);
    }
    
}
