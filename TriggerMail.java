package com.ims.lib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;

public class TriggerMail {
	public void Email_Authentication(String reportPath){
		String host="Intemail.rxcorp.com";  
		final String user="madhura.G@in.imshealth.com"; 
		byte[] decodedBytes = Base64.decodeBase64("");
		final String password=new String(decodedBytes);
				
		
		String s1[]=new String[2];
//		s1[0]="ynanjaiah@in.imshealth.com";
		s1[0]="Madhura.G@in.imshealth.com";
		//s1[2]="@in.imshealth.com";
		System.out.println(s1.length);
		for (int i=0;i<s1.length;i++)
		{
			//Get the session object  
			Properties props = new Properties();  
			props.put("mail.smtp.host",host);  

			Session session = Session.getDefaultInstance(props,  
					new javax.mail.Authenticator() 
			{  
				protected PasswordAuthentication getPasswordAuthentication()
				{  
					return new PasswordAuthentication(user,password);  
				}  
			});  

			//Compose the message  
			try 
			{  
				MimeMessage message = new MimeMessage(session);  
				message.setFrom(new InternetAddress(user));  
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(s1[i]));  
				message.setSubject("DNA app validation: automation test execution report");  
				// message.setText("Status of the reports");  

				/*------------------------------------------------------------------------------------------------*/

				//// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText("PFA automation test execution report for DNA application");
				//  messageBodyPart.setText("Automatic mail triggered during the Spain scripts execution");
				// Create a multipart message
				Multipart multipart = new MimeMultipart();

				//// Set text message part
				multipart.addBodyPart(messageBodyPart);
				//Part two is attachment
				messageBodyPart = new MimeBodyPart();
				//String filename = System.getProperty("user.dir")+"/Reports/Extentreport.html";
			    String filename=reportPath;
				String imagename="Database access error screenshot";
				DateFormat df = new SimpleDateFormat("dd.MMM.yyyy-HH.mm.ss");
				Date today = Calendar.getInstance().getTime();
				String runTime = df.format(today);
				//System.out.println(df.format(today));
				
				runTime="Automation Execution Summary" + runTime;
						
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(runTime+".html");
				multipart.addBodyPart(messageBodyPart);

				//// Send the complete message parts
				message.setContent(multipart);
				/*-----------------------------------------------------------------------------------------------------*/     
				//send the message  
				Transport.send(message);  

				System.out.println("Mail Sent Succesfully!");  

				/*Log4j declarations*/
				// logger.info("Testing Team Scripts Comment(runMail method) - 'Error seen in reports and mail sent successfully..!' ");
			} catch (MessagingException e)
			{
				e.printStackTrace();
			}  

		}

	}

}
