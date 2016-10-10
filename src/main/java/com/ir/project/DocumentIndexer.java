package com.ir.project;


import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ronak on 9/29/2016.
 */
public class DocumentIndexer {


    public void readFiles() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("project.properties");
            prop.load(input);
            String indexLocation = prop.getProperty("index_location");
            File file = new File(indexLocation);

            IndexReader reader = DirectoryReader.open(FSDirectory.open(file.toPath()));
            FileWriter fr=new FileWriter("postings.txt");
            FileWriter fr1=new FileWriter("docs.txt");
         /*   for(int i=0;i<reader.maxDoc();i++){
                if(reader.document(i).get("text_fr")!=null){
                    fr1.write(i+"::"+reader.document(i).get("text_fr")+"\n");
                }
            }*/
            int count = 0;
            SaveIndex obj=new SaveIndex();
            obj.saveIndexInMemory(reader,"text_fr");
            obj.saveIndexInMemory(reader,"text_nl");
            obj.saveIndexInMemory(reader,"text_de");
            obj.saveIndexInMemory(reader,"text_ja");
            obj.saveIndexInMemory(reader,"text_ru");
            obj.saveIndexInMemory(reader,"text_pt");
            obj.saveIndexInMemory(reader,"text_es");
            obj.saveIndexInMemory(reader,"text_no");
            obj.saveIndexInMemory(reader,"text_it");
            obj.saveIndexInMemory(reader,"text_da");
            obj.saveIndexInMemory(reader,"text_sv");
            Map<String,List<Integer>> postings=obj.getPostings();
            JSONObject jsonObj=new JSONObject(postings);
            //fr.write(jsonObj.toString(10));
            List<String> list=new ArrayList<String>();

            list.add("czako");
            list.add("yokoya");
            list.add("zagorodniouk");
            list.add("vanesa");

            QueryProcessor obj1=new QueryProcessor();
            List<Integer> result=obj1.executerTAATandQuery(list,postings);
            for(Integer docId:result){
                System.out.println(docId);
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DocumentIndexer obj = new DocumentIndexer();
        obj.readFiles();
    }

}
