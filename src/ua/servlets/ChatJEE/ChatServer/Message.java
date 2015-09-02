package ua.servlets.ChatJEE.ChatServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@XmlRootElement
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date date = new Date();
	private String from;
	private String to;
	private String text;
	
	public String toJSON() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	
	public static Message fromJSON(String s) {
		Gson gson = new GsonBuilder().create();
		Message mes = gson.fromJson(s, Message.class);
//		mes.setFrom(SessionList.getInstance().getLoginByID(mes.getFrom()));
		return mes;
	}

	public String toXML() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(this.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		OutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(this, os);
		return os.toString();
	}

	public static Message fromXML(String s) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Message.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		InputStream inputStream = new ByteArrayInputStream(s.getBytes());
		return (Message) unmarshaller.unmarshal(inputStream);
	}

	@Override
	public String toString() {
		return new StringBuilder().append("[").append(date.toString())
				.append(", From: ").append(from).append(", To: ").append(to)
				.append("] ").append(text).toString();
	}

	public int send(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
	
		OutputStream os = conn.getOutputStream();
		try {
			String json = toXML();
			os.write(json.getBytes());
			
			return conn.getResponseCode();
		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
			os.close();
		}
		return 0;
	}

	@XmlElement
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@XmlElement
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@XmlElement
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@XmlElement
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
