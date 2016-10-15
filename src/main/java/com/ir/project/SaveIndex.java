package com.ir.project;

import org.apache.lucene.index.*;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.util.BytesRef;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ronak
 */
public class SaveIndex {

    private Map<String,List<Integer>>  postings=new HashMap<String, List<Integer>>();


    public void saveIndexInMemory(IndexReader reader, String field_name){
        BytesRef term = null;
        Terms terms = null;
        try {
            terms = MultiFields.getTerms(reader, field_name);
            TermsEnum termsEnum = terms.iterator();
            int max=0;
            while ((term = termsEnum.next()) != null) {
                List<Integer> list=new ArrayList<Integer>();
                PostingsEnum postingEnum = MultiFields.getTermDocsEnum(reader, field_name, term);

                int doc = postingEnum.nextDoc();
                while (doc != DocIdSetIterator.NO_MORE_DOCS) {
                    if(doc>max){
                        max=doc;
                    }
                    list.add(doc);
                    doc = postingEnum.nextDoc();
                }
                postings.put(term.utf8ToString(),list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Map<String,List<Integer>> getIndex(){
        return postings;
    }


}
