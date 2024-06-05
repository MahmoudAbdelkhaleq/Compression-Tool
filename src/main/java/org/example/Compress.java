package org.example;

import org.example.HuffmanTree.HuffmanTree;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Scanner;

public class Compress {
    private File file;
    private HuffmanTree huffmanTree;
    final static String TEST_PATH = "src/main/java/org/example/tests/";
    private void compress(String fileName, String outputFileName) throws IOException {
        String text = readTextFile(fileName);
        BitSet compressedBits = computeCompressedText(text);
        writeCompressedFile(compressedBits, outputFileName);
    }
    private String readTextFile(String fileName) throws FileNotFoundException {
        file = new File(TEST_PATH+fileName);
        StringBuilder text = new StringBuilder();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            text.append(myReader.nextLine()+"\n");
        }
        text = new StringBuilder(text.toString().trim());
        myReader.close();
        return text.toString();
    }

    private BitSet computeCompressedText(String text){
        HashMap<Character, Integer> characterFrequencyMap = new HashMap<>();
        for(int i = 0;i<text.length();i++){
            char c = text.charAt(i);
            characterFrequencyMap.put(c,characterFrequencyMap.getOrDefault(c,0)+1);
        }
        huffmanTree = new HuffmanTree(characterFrequencyMap);
        HashMap<Character, String> characterCodeMap = huffmanTree.getCharacterCodeMap();
        StringBuilder compressedText = new StringBuilder();
        for(int i = 0;i<text.length();i++){
            compressedText.append(characterCodeMap.get(text.charAt(i)));
        }
        BitSet compressedBits = new BitSet(compressedText.length());
        int bitIndex = 0;
        for(Character c : compressedText.toString().toCharArray()) {
            if(c.equals('1')) {
                compressedBits.set(bitIndex);
            }
            bitIndex++;
        }
        return compressedBits;
    }

    private void writeCompressedFile(BitSet compressedBits, String outputFileName) throws IOException {
        ObjectOutputStream writer =
                new ObjectOutputStream(new FileOutputStream(TEST_PATH+outputFileName));
        writer.writeObject(huffmanTree.getCharacterCodeMap());
        writer.writeObject(compressedBits);
        System.out.println("File compressed successfully");
        System.out.println("Compressed file: "+outputFileName);
        writer.close();
    }

    public static void main(String[] args) {
        Compress compress = new Compress();
        try {
            //production code
//            if(args.length == 0)
//                throw new Exception("No file name provided");
//            if(args.length == 1)
//                throw new Exception("No output file name provided");
//            if(args.length > 2)
//                throw new Exception("Too many arguments");

//            compress.compress(args[0], args[1]);
            compress.compress("135-0.txt", "output.txt");
        }
        catch (Exception e){
            System.out.println(e.toString());
            System.exit(255);
        }
    }
}
