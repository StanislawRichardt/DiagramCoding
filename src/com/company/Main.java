package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<String[]> pairList = new ArrayList<>();
    static List<String[]> dictionary = new ArrayList<>();

    static BufferedReader bufferedReader = null;
    static String inputFilePath = "E:\\Repozytoria\\DiagramCoding\\example\\testFile.txt";
    static File inputFile = new File(inputFilePath);

    static BufferedWriter bufferedWriter = null;
    static String outputFilePath = "E:\\Repozytoria\\DiagramCoding\\example\\outputFile.txt";
    static File outputFile = new File(outputFilePath);

    static String data;
    static int fileLength = 0;
    static boolean isWhole=false;

    private static void inputFileUtility(){
        try {
            FileInputStream fileStream = new FileInputStream(inputFile);
            fileStream.getChannel().position(0);
            InputStreamReader input = new InputStreamReader(fileStream);
            bufferedReader = new BufferedReader(input);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private static void searchAndSave(String chars, List<String[]> list){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[1].equals(chars)) {
                int counter = Integer.parseInt(list.get(i)[0]) + 1;
                list.set(i, new String[]{Integer.toString(counter), chars});
                return;

            }
        }
        list.add(new String[]{"1",chars});
    }
    private static void probabilityCount(List<String[]> list){
        for (int i = 0; i < list.size(); i++) {
            double counter = Double.parseDouble(list.get(i)[0]) / fileLength;
            list.set(i, new String[]{Double.toString(counter), list.get(i)[1]});
        }
    }
    private static void dictionaryCreation(){
        for(int i=dictionary.size();i<256;i++)
        {
            int value= maxValue();
            dictionary.add(dictionary.size(), new String[]{pairList.get(value)[0], pairList.get(value)[1]});
            pairList.remove(value);
        }
        binaryCreation();
    }
    private static int maxValue() {
        int value = 0;

        for (int j = 0; j < pairList.size(); j++) {
            if (Double.parseDouble(pairList.get(j)[0]) > Double.parseDouble(pairList.get(value)[0])) {
                value = j;
            }
        }
        return value;
    }
    private static void dictionaryDisplay(){
        for (int i = 0; i < dictionary.size(); i++) {

            System.out.println(dictionary.get(i)[0]+'\t'+" | "+ dictionary.get(i)[1]+'\t');
        }
    }
    private static void outputFileUtility(){
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private static void dataGathering(){

        try {
            inputFileUtility();
            while ((data = bufferedReader.readLine()) != null) {

                fileLength += data.length();
                for(int j=0;j<data.length();j++)
                {
                    searchAndSave(Character.toString(data.charAt(j)), dictionary);
                }
            }
            probabilityCount(dictionary);

           inputFileUtility();

            while ((data = bufferedReader.readLine()) != null) {

                for(int j=2;j<data.length();j++)
                {
                    searchAndSave(data.substring(j-2,j), pairList);
                }
            }
            probabilityCount(pairList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void binaryCreation(){

        for(int i=0;i<dictionary.size();i++){
            String binary= Integer.toBinaryString(i);
            for(int j=binary.length();j<8;j++){
                binary= "0"+binary;
            }
            dictionary.set(i,new String[]{binary,dictionary.get(i)[1]});
        }
    }
    private static void diagramCoding(){
        outputFileUtility();

        try {
            bufferedWriter.write(" ");
            inputFileUtility();
            while ((data = bufferedReader.readLine()) != null) {

                for(int j=2;j<data.length();j++)
                {
                    bufferedWriter.append(checkPair(data.substring(j-2,j)));
                    if(isWhole)
                    {
                        j++;
                        isWhole=false;
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }


    }
    private static String checkPair(String chars){
        String firstChar="error";
        for(int i=0;i<dictionary.size();i++)
        {
            if(chars.equals(dictionary.get(i)[1]))
            {
                isWhole=true;
                //return dictionary.get(i)[0];
                return Integer.toString(i);

            }
            else if(chars.substring(0,1).equals(dictionary.get(i)[1]))
            {
                //firstChar= dictionary.get(i)[0];
                firstChar=Integer.toString(i);
            }
        }
        return firstChar;
    }
    public static void main(String[] args) {
	    dataGathering();
	    dictionaryCreation();
	    dictionaryDisplay();
	    diagramCoding();
    }
}
