package ua.servlets.ChatJEE.ChatClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class GetThread extends Thread {
	public GetThread(int sessionId) {
		this.sessionId = sessionId;
	}

	public void changeN(int n) {
		this.n = n;
	}

	private int n;
	private final int sessionId;

	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				Thread.sleep(1000);
				URL url = new URL("http://localhost:8888/get?from=" + n +"&id=" + sessionId);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();

				InputStream is = http.getInputStream();
				try {
					int sz = is.available();
					if (sz > 0) {
						byte[] buf = new byte[is.available()];
						is.read(buf);

						Gson gson = new GsonBuilder().create();
						Message[] list = gson.fromJson(new String(buf), Message[].class);

						for (Message m : list) {
							System.out.println(m);
							n++;
						}
					}
				} finally {
					is.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}
}

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			int sessionId;
			String login;
			do {
				System.out.println("Enter login: ");
				login = scanner.nextLine();
				sessionId = getSessionID(login);
			} while (sessionId<0);
			enterRoom(String.valueOf(sessionId),"ROOT");


			GetThread th = new GetThread(sessionId);
			th.setDaemon(true);
			th.start();

			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty())	break;

				Message m = new Message();

				//If you write "to nick" client will send private message
				if(text.toLowerCase().startsWith("to ")) {
					m.setTo(text.substring(3));
					text = scanner.nextLine();
					if (text.isEmpty())	break;
				}

				if(text.toLowerCase().startsWith("enter ")) {
					enterRoom(String.valueOf(sessionId),text.substring(6));
					th.changeN(0);
					text = scanner.nextLine();
					if (text.isEmpty())	break;
				}
				m.setText(text);
				m.setFrom(String.valueOf(sessionId));

				try {
					int res = m.send("http://localhost:8888/add");
					if (res != 200) {
						System.out.println("HTTP error: " + res);
						return;
					}
				} catch (IOException ex) {
					System.out.println("Error: " + ex.getMessage());
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private static void enterRoom(String id, String room) throws IOException {
		URL url = new URL("http://localhost:8888/enterroom?id=" + id + "&room=" + room);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
//		conn.setDoInput(false);

		conn.getInputStream();
	}

	private static int getSessionID(String login) throws IOException {
		URL url = new URL("http://localhost:8888/login?login=" + login);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");

		InputStream is = conn.getInputStream();
		try {
			int sz = is.available();
			if (sz > 0) {
				byte[] buf = new byte[is.available()];
				is.read(buf);

				String read = new String(buf);
				int result = Integer.parseInt(read);
				return result;

			}
		} finally {
			is.close();
		}
		return -1;
	}
}
