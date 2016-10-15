package com.ir.project;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by ronak
 */
@SuppressWarnings("Duplicates")
public class QueryProcessor {

    private int[] comparisonCounts=new int[4];
    public List<Integer> executeTAATAndQuery(List<String> listOfTerms,Map<String,List<Integer>> postings){
        List<Integer> result=null;
        List<Integer> list=postings.get(listOfTerms.get(0));
        int numberOfComparisons=0;
        for(int c=1;c<listOfTerms.size();c++){
            String term=listOfTerms.get(c);

            List<Integer> newList=postings.get(term);
            int i=0;
            int j=0;
            result=new ArrayList<Integer>();
            while(i<list.size() && j<newList.size()){
                numberOfComparisons++;
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
        comparisonCounts[0]=numberOfComparisons;
        return list;
    }

    public List<Integer> executeTAATORQuery(List<String> listOfTerms,Map<String,List<Integer>> postings){
        List<Integer> result=null;
        List<Integer> list=postings.get(listOfTerms.get(0));
        int numberOfComparisons=0;
        for(int c=1;c<listOfTerms.size();c++){
            String term=listOfTerms.get(c);
            List<Integer> newList=postings.get(term);
            int i=0;
            int j=0;
            result=new ArrayList<Integer>();
            while(i<list.size() && j<newList.size()){
                numberOfComparisons++;
                int a=list.get(i);
                int b=newList.get(j);
                if(list.get(i)>newList.get(j)){
                    result.add(newList.get(j));
                    j++;
                }else if(list.get(i)<newList.get(j)){

                    result.add(list.get(i));
                    i++;
                }else{
                    result.add(newList.get(j));
                    i++;
                    j++;
                }
            }
            while(i<list.size()){
                 result.add(list.get(i));
                  i++;
            }
            while(j<newList.size()){
                result.add(newList.get(j));
                j++;
            }
            list=result;
        }
        comparisonCounts[1]=numberOfComparisons;
        return list;
    }

    public List<Integer> executeDAATAndQuery(List<String> listOfTerms,Map<String,List<Integer>> postings){

        int minListSize=Integer.MAX_VALUE;
        List<Integer> postingsList;
        List<List<Integer>> list=new ArrayList<List<Integer>>();
        int index=0;
        int count=0;
        List<Integer> result=new ArrayList<Integer>();
        int noOfComparisons=0;

        for(String term:listOfTerms){
            postingsList=postings.get(term);
            list.add(postingsList);
            if(postingsList.size()<minListSize){
                minListSize=postingsList.size();
                index=count;
            }
            count++;
        }
        int[] pointers=new int[listOfTerms.size()];

        while(pointers[index]<minListSize){
            int[] arr=new int[pointers.length];
            int i=0;
            int max=-1;
            int max_index=0;
            boolean isSame=true;
            for(List<Integer> li:list){
                if(pointers[i]>=li.size()){
                    break;
                }
                arr[i]=li.get(pointers[i]);
                if(arr[i]>max){
                    max=arr[i];
                    max_index=i;
                }
                i++;
            }
            for(int j=0;j<i;j++){
                if(j!=max_index){
                    if(arr[j]<arr[max_index]){
                        isSame=false;
                        pointers[j]+=1;
                        noOfComparisons++;
                    }
                }
            }
            if(isSame){
                if(max!=-1){
                    result.add(max);
                }
                for(int m=0;m<pointers.length;m++){
                    pointers[m]+=1;
                }
            }
        }
        comparisonCounts[2]=noOfComparisons;
        Collections.sort(result);
        return result;
    }

    public List<Integer> executeDAATOrQuery(List<String> listOfTerms,Map<String,List<Integer>> postings){
        int minListSize=Integer.MAX_VALUE;
        List<Integer> postingsList;
        List<List<Integer>> list=new ArrayList<List<Integer>>();
        int index=0;
        int count=0;
        List<Integer> result=new ArrayList<Integer>();
        int noOfComparisons=0;
        boolean isComplete=false;

        for(String term:listOfTerms){
            postingsList=postings.get(term);
            list.add(postingsList);
            if(postingsList.size()<minListSize){
                minListSize=postingsList.size();
                index=count;
            }
            count++;
        }
        int[] pointers=new int[listOfTerms.size()];

        while(pointers[index]<minListSize){
            if(isComplete){
                break;
            }
            int[] arr=new int[pointers.length];
            int i=0;
            int max=-1;
            int max_index=0;
            boolean isSame=true;

            for(List<Integer> li:list){
                if(pointers[i]>=li.size()){
                    isComplete=true;
                    break;
                }
                arr[i]=li.get(pointers[i]);
                if(arr[i]>max){
                    max=arr[i];
                    max_index=i;
                }

                i++;
            }
            if(!isComplete){
                for(int j=0;j<pointers.length;j++){
                    noOfComparisons++;
                    if(arr[j]!=max){
                        pointers[j]+=1;
                        result.add(arr[j]);
                        isSame=false;
                    }
                }
                if(isSame){
                    if(max!=-1){
                        for(int m=0;m<pointers.length;m++){
                            pointers[m]+=1;
                        }
                    }
                }
            }

        }
       for(int i=0;i<pointers.length;i++){
            while(pointers[i]<list.get(i).size()){
                result.add(list.get(i).get(pointers[i]));
                pointers[i]+=1;
            }
       }
        comparisonCounts[3]=noOfComparisons;
        Collections.sort(result);
        return result;
    }

    public int[] getComparisoncount(){
            return comparisonCounts;
    }
}
