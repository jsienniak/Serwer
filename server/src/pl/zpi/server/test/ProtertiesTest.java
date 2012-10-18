package pl.zpi.server.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class ProtertiesTest {
	public static void main(String args[]){
		Properties p = new Properties();
		p.setProperty("wartosc testowa", "15");
		p.setProperty("nowa wartosc", "33  r f łóżźć ");
		File f = new File("test.properties");
		try {
			FileOutputStream stream = new FileOutputStream(f);
			p.store(stream, "test prop");
			stream.close();
			
			f = new File("test.properties");
			p = new Properties();
			FileInputStream in = new FileInputStream(f);
			p.load(in);
			in.close();
			p.list(System.out);
		} catch (IOException e) {
			if (e instanceof FileNotFoundException){
				System.out.println("Nie znaleziono pliku");
			}else{
				System.out.println("IOException");
			}
		}

	}
}
