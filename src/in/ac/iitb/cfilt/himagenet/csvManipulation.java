package in.ac.iitb.cfilt.himagenet;


import in.ac.iitb.cfilt.common.io.UTFReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;

public class csvManipulation {
	
	public static void main(String args[])throws Exception{
		System.out.println("Parsing File");
		fileParser();
	}
	
	private static void fileParser() throws IOException, ClassNotFoundException, SQLException {

		UTFReader reader = new UTFReader("english_hindi_id_mapping.csv");
		UTFReader reader1 = new UTFReader("english_synset_data.csv");
		File filedir = new File("wordnetMapping");
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filedir),"UTF-8"));
		String line = null;
		reader.open();
		while ((line = reader.readLine()) != null) {
			reader1.open();
			String elements[] = line.split(",");
			int length=elements.length;
			int hid=Integer.parseInt(elements[0]);
			int eid=Integer.parseInt(elements[2]);
			String Category=elements[3];
			String line1 = null;
			String word = null;
			String glossEg = null;
			int compeid = 0;
			while((line1 = reader1.readLine())!=null){
				String items[] = line1.split("\",\"");
				String id = items[0].replace("\"", "");
				compeid = Integer.parseInt(id);
				if(compeid==eid){
					word = items[2].replace("_","%20");
					String words[] = word.split(",");
					glossEg = items[3].replace("\"", "");
					String glossEgs[] = glossEg.split(";");
					glossEg=glossEg.replace(glossEgs[0],"");
					glossEg=glossEg.replace(";","");
					glossEg=glossEg.replaceAll("^ ","");
					out.append("\""+hid+"\",\""+eid+"\",\""+words[0]+"\",\""+word+"\",\""+glossEgs[0]+"\",\""+glossEg+"\"\n");
					reader1.close();
					break;
				}
			}
		}
		out.flush();
		out.close();
	}
}
