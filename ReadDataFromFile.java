package com.company;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

final class ReadDataFromFile {
    private Scanner scanner, sc;

    // initializes an input stream from the file.
    public ReadDataFromFile(File file) {
        if (file == null) throw new IllegalArgumentException("file does not exist.");
        try {
            File retFile = fileCleaner(file);
            FileInputStream fis = new FileInputStream(retFile);
            scanner = new Scanner(new BufferedInputStream(fis));
            sc = new Scanner(retFile);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("error with " + file, ioe);
        }
    }

    // cleans up the file from unnecessary information
    private File fileCleaner(File f) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(f);
        scanner = new Scanner(new BufferedInputStream(fis));
        sc = new Scanner(f);

        ArrayList<String> xd = new ArrayList<>();
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            xd.add(s);
        }

        PrintWriter writer = new PrintWriter(f);
        writer.print("");

        for (String s : xd) if (s.split("\\s+").length < 3) writer.print(s + "\n");
        writer.close();

        return f;
    }

    // reads, parses and returns the next token from input stream as int.
    public int readInt() {
        try {
            if (scanner.hasNext()) return scanner.nextInt();
            return 0;
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("The next token is \"" + token + "\"");
        }
    }

    // helper function in GraphG class, counts the lines and use them as number of vertices and edges
    public int countLines(File f) {
        int counter = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            while (br.readLine() != null) {counter++; }
        } catch (IOException e) { e.printStackTrace(); }

        return counter;
    }

    // reads and returns a string from the rest of input stream
    public String readFile() {
        if (!sc.hasNextLine()) return "";
        return sc.useDelimiter(Pattern.compile("\\A")).next();
    }

    // reads and returns an array of strings from the rest tokens from input stream.
    public String[] readArrayStrings() {
        String[] tokens = Pattern.compile("\\p{javaWhitespace}+").split(readFile());
        if (tokens.length == 0 || tokens[0].length() > 0) return tokens;

        String[] decapitokens = new String[tokens.length-1];
        for (int i=0; i<tokens.length-1; i++) decapitokens[i] = tokens[i+1];

        return decapitokens;
    }

    // converts array of strings to ints, finds max and min token from this array and returns them
    @SuppressWarnings("unchecked")
    public GetMaxMinVertex  findMaxAndMinVertex(File f) {
        String[] tokens = readArrayStrings();
        int[] values = new int[tokens.length];
        HashSet<Integer> vertices = new HashSet<>();

        for (int i=0; i<tokens.length; i++) values[i] = Integer.parseInt(tokens[i]);
        for (int i=0; i<tokens.length; i++) vertices.add(values[i]);  // counting nodes in file

        int countLines = countLines(f); // total edges
        int countVertices = vertices.size(); // total vertices
        return new GetMaxMinVertex(countVertices, Collections.min(vertices), countLines);
    }
}