package com.ir.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ronak on 10/8/2016.
 */
public class QueryProcessor {


    public List<Integer> executerTAATandQuery(List<String> listOfTerms,Map<String,List<Integer>> postings){
        List<Integer> result=null;
        List<Integer> list=postings.get(listOfTerms.get(0));
        for(int c=1;c<listOfTerms.size();c++){
            String term=listOfTerms.get(c);
            List<Integer> newList=postings.get(term);
            int i=0;
            int j=0;
            result=new ArrayList<Integer>();
            while(i<list.size() && j<newList.size()){
                int doc1=list.get(i);
                int doc2=newList.get(j);
                if(doc1==doc2){
                    result.add(doc1);
                    i++;
                    j++;
                }else if(list.get(i)>newList.get(j)){
                        j++;
                }else{
                    i++;
                }
            }
            list=result;
        }
        return list;
    }
}
