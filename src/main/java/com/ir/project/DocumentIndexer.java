package com.ir.project;


import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Map;


/**
 * Created by ronak
 */
public class DocumentIndexer {


    public Map<String,List<Integer>> indexFiles(String path) {

        Map<String,List<Integer>> postings=null;
        try {

            File file = new File(path);
            IndexReader reader = DirectoryReader.open(FSDirectory.open(file.toPath()));

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
            postings =obj.getIndex();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return postings;
    }
}
