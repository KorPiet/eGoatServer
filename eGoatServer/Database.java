package eGoatServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Database {
	public List<DatabaseLine> files;
	private BufferedReader in;
	Database() throws IOException{
		File yourFile = new File("src/eGoatServer/txt/files.txt");
		files = new ArrayList<DatabaseLine>();
		String line = null;
		in = new BufferedReader(new FileReader(yourFile));
		while((line = in.readLine()) != null) {
			files.add(new DatabaseLine(line));
		}
	}
	
	public void addLine(String line) {
		files.add(new DatabaseLine(line));
	}
	
	public void toFile() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("files", "UTF-8");
		for(DatabaseLine a : files)
			writer.println(a.toString());
		writer.close();
	}
	
}
