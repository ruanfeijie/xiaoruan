package mytest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Testmail {

	// �����ʼ����˺�
    public static String ownEmailAccount = "1914006564@qq.com";
    // �����ʼ�������------����Ȩ��
    public static String ownEmailPassword = "ndmqszlehblrdede";
    // �����ʼ���smtp ������ ��ַ
    public static String myEmailSMTPHost = "smtp.qq.com";
    // �����ʼ��Է�������
    public static String receiveMailAccount = "15757333432@163.com";

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        // �����ʼ�������õ�Э��smtp
        prop.setProperty("mail.transport.protocol", "smtp");
        // ���÷������ʼ���������smtp��ַ
        // ���������׵�����smtp��������ַΪ��
        prop.setProperty("mail.smtp.host", myEmailSMTPHost);
        // ������֤����
        prop.setProperty("mail.smtp.auth", "true");

        // SMTP �������Ķ˿� (�� SSL ���ӵĶ˿�һ��Ĭ��Ϊ 25, ���Բ����, ��������� SSL ����,
        // ��Ҫ��Ϊ��Ӧ����� SMTP �������Ķ˿�, ����ɲ鿴��Ӧ�������İ���,
        // QQ�����SMTP(SLL)�˿�Ϊ465��587, ������������ȥ�鿴)

        final String smtpPort = "465";
        prop.setProperty("mail.smtp.port", smtpPort);
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // ��������ػ�������������
        Session session = Session.getInstance(prop);
        // �Ự����debugģʽ
        session.setDebug(true);
        // �����ʼ�����
        Message message = createSimpleMail(session);

        Transport trans = session.getTransport();
        // �����ʼ�������
        trans.connect(ownEmailAccount, ownEmailPassword);
        // ������Ϣ
        trans.sendMessage(message, message.getAllRecipients());
        // �ر�����
        trans.close();
    }

    /**  
    * @Title: createSimpleMail  
    * @Description: �����ʼ�����
    * @author: chengpeng 
    * @param @param session
    * @param @return
    * @param @throws Exception    �趨�ļ�  
    * @return Message    ��������  
    * @throws  
    */
    public static Message createSimpleMail(Session session) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // ���÷����ʼ���ַ,param1 �����͵�ַ param2 �����͵�����(�����) param3 �������Ʊ��뷽ʽ
        message.setFrom(new InternetAddress("1914006564@qq.com", "С��", "utf-8"));
        // �����ռ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailAccount, "����", "utf-8"));
        // To: �����ռ��ˣ���ѡ��
//        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com", "USER_DD", "UTF-8"));
        // Cc: ���ͣ���ѡ��
        message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(receiveMailAccount, "����", "utf-8"));
        // Bcc: ���ͣ���ѡ��
//        message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", "UTF-8"));
        // �����ʼ�����
        message.setSubject("����ת���ʼ�");
        // �����ʼ�����
//        message.setContent("�簲,����   ���������", "text/html;charset=utf-8");
//        // ���÷���ʱ��
//        message.setSentDate(new Date());
//        // ���ø�������
//        message.setText("D:\\my.log");
        // ��������ı༭����
        // ����
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("aa".toString(),"text/html;charset=UTF-8");
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent("bb".toString(),"text/html;charset=UTF-8");
        // ����������Ϣ
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(messageBodyPart1);
        // ��Ӹ���
        MimeBodyPart attachment = null;
        File filePath = new File("D:\\test/my.log");
        attachment = new MimeBodyPart();
        //���ݸ����ļ������ļ�����Դ
        final DataSource ds = new FileDataSource(filePath);
        attachment.setDataHandler(new DataHandler(ds));
        //Ϊ���������ļ���
        attachment.setFileName(ds.getName());
        multipart.addBodyPart(attachment);
        message.setContent(multipart);
        message.saveChanges();
        // �����洴���Ķ���д�뱾��
        OutputStream out = new FileOutputStream("MyEmail.eml",false);
        message.writeTo(out);
        out.flush();
        out.close();
        return message;

    }

}
