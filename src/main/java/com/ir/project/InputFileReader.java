package com.ir.project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ronak
 */
public class InputFileReader {

    public static void main(String[] args){

        String path=args[0];
        String outputFile=args[1];
        String inputFile=args[2];
        InputFileReader obj=new InputFileReader();
        obj.executeInputQueries(path,inputFile,outputFile);

    }

    public List<List<String>> readInputFile(String fileName){
        BufferedReader br;
        String sCurrentLine;
        List<List<String>> list=new ArrayList<List<String>>();
        List<String> lst;
        try {
             br=new BufferedReader(new FileReader(fileName));
            while ((sCurrentLine = br.readLine()) != null) {
              lst=new ArrayList<String>();
              for(String str:sCurrentLine.split(" ")){
                  lst.add(str);
              }
                list.add(lst);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void executeInputQueries(String path,String inputFile, String outputFile){
        List<List<String>> listOfQueries=readInputFile(inputFile);
        DocumentIndexer indexer=new DocumentIndexer();
        Map<String,List<Integer>> postings=indexer.indexFiles(path);
        FileWriter writer=null;
        QueryProcessor queryProcessor=new QueryProcessor();
        try {
            writer=new FileWriter(outputFile);
            StringBuilder sb=new StringBuilder();
            for(List<String> list:listOfQueries){
                String terms=list.toString().substring(1,list.toString().length()-2).replaceAll(",","");
                for(int i=0;i<list.size();i++){
                    writer.write("GetPostings"+"\n");
                    writer.write(list.get(i)+"\n");
                    writer.write("Postings list: ");
                    List<Integer> postingList=postings.get(list.get(i));
                    if(postingList!=null){
                        for(Integer docId:postingList){
                            sb.append(docId+" ");
                        }
                        writer.write(sb.toString()+"\n");
                    }
                    sb.setLength(0);
                }
                writer.write("TaatAnd"+"\n");
                writer.write(terms+"\n");
                StringBuilder sb_list=new StringBuilder();
                if(postings!=null){
                    List<Integer> taatAndList=queryProcessor.executeTAATAndQuery(list,postings);
                    if(taatAndList!=null){
                        for(Integer id:taatAndList){
                            sb_list.append(id+" ");
                        }
                        if(sb_list.length()==0){
                            writer.write("Results: "+"empty"+"\n");
                        }else{
                            writer.write("Results: "+sb_list.toString()+"\n");
                        }
                        writer.write("Number of documents in results: "+taatAndList.size()+"\n");
                        writer.write("Number of comparisons: "+queryProcessor.getComparisoncount()[0]+"\n");
                    }
                }




                writer.write("TaatOr"+"\n");
                writer.write(terms+"\n");
                sb_list.setLength(0);
                if(postings!=null){
                    List<Integer> taatOrList=queryProcessor.executeTAATORQuery(list,postings);
                    if(taatOrList!=null){
                        for(Integer id:taatOrList){
                            sb_list.append(id+" ");
                        }
                        if(sb_list.length()==0){
                            writer.write("Results: "+"empty"+"\n");
                        }else{
                            writer.write("Results: "+sb_list.toString()+"\n");
                        }
                        writer.write("Number of documents in results: "+taatOrList.size()+"\n");
                        writer.write("Number of comparisons: "+queryProcessor.getComparisoncount()[1]+"\n");
                    }

                }



                writer.write("DaatAnd"+"\n");
                writer.write(terms+"\n");
                sb_list.setLength(0);

                if(postings!=null){
                    List<Integer> daatAndList=queryProcessor.executeDAATAndQuery(list,postings);
                    if(daatAndList!=null){
                        for(Integer id:daatAndList){
                            sb_list.append(id+" ");
                        }
                        if(sb_list.length()==0){
                            writer.write("Results: "+"empty"+"\n");
                        }else{
                            writer.write("Results: "+sb_list.toString()+"\n");
                        }

                        writer.write("Number of documents in results: "+daatAndList.size()+"\n");
                        writer.write("Number of comparisons: "+queryProcessor.getComparisoncount()[2]+"\n");
                    }
                }



                writer.write("DaatOr"+"\n");
                writer.write(terms+"\n");
                sb_list.setLength(0);
                if(postings!=null){
                    List<Integer> daatOrList=queryProcessor.executeDAATOrQuery(list,postings);
                    if(daatOrList!=null){
                        for(Integer id:daatOrList){
                            sb_list.append(id+" ");
                        }
                        if(sb_list.length()==0){
                            writer.write("Results: "+"empty"+"\n");
                        }else{
                            writer.write("Results: "+sb_list.toString()+"\n");
                        }
                        writer.write("Number of documents in results: "+daatOrList.size()+"\n");
                        writer.write("Number of comparisons: "+queryProcessor.getComparisoncount()[3]+"\n");
                    }

                }


                sb.setLength(0);
                sb_list.setLength(0);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
