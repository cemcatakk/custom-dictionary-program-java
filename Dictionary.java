import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
class Test{
    public static void main(String[] args) {
        if (args.length!=1){
            System.out.print("Invalid arguments.");
        }
        else{
            DictOperations dictOperations= new DictOperations(args[0]);
            Scanner sc = new Scanner(System.in);
            String cmd="";
            while(!cmd.equals("quit")){
                System.out.println("Enter command:");
                cmd = sc.nextLine();
                dictOperations.Command(cmd);
            }
        }
    }
}
class Word{
    //Word class will refer to every meaning found in our dictionary.
    // We will provide the necessary controls with the lesson, word and meaning features in it.
    // By overriding the Equals method, we will be able to compare the two words.
    // In the DictOperations class, the method named readFromfile creates a Word object by shredding the data in the file given as parameter with the '-' sign.
    // Then it adds these objects to the list where we keep our word information.
    // We send the commands given by the user to the Command method and determine which action will be taken.
    // Then we call the appropriate method according to the command entered.
    // After checking the correctness of the entered parameters, we execute the command.
    // Also, when the program is run for the first time, it prevents possible errors by checking whether the first argument is the filename.
    public Word(String lesson, String word, String meaning) {
        this.setLesson(lesson);
        this.setWord(word);
        this.setMeaning(meaning);
    }
    public Word(String[] arr) {
        //lesson-word-string
        this.setLesson(arr[0]);
        this.setWord(arr[1]);
        this.setMeaning(arr[2]);
    }
    public Word(String word) {
        this.setWord(word);
    }

    private String lesson;
    private String word;
    private String meaning;

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public String toString(){
        return lesson+"-"+word+"-"+meaning;
    }
}
class DictOperations {
    Scanner sc;
    ArrayList<Word> dict;
    String file;
    public DictOperations(){
        sc = new Scanner(System.in);
        dict = new ArrayList<Word>();
    }
    public DictOperations(String filename){
        sc = new Scanner(System.in);
        dict =readFromFile(filename);
        file=filename;
    }
    ArrayList<Word> readFromFile(String filename){
        //FILE PATTERN
        //lesson-word-meaning
        //lesson-word-meaning
        //..
        String line;
        BufferedReader bufferedReader;
        ArrayList<Word> dict = new ArrayList<Word>();
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            line = bufferedReader.readLine();
            for (int i=0;i==0;i=i){
                String[] split = line.split("-");
                Word control = new Word(split);
                if (dict.contains(control)){
                    throw new Exception();
                }
                else{
                    dict.add(new Word(split[0],split[1],split[2]));
                }
                line = bufferedReader.readLine();
                if(line==null)break;
            }
            /*
            while(line!=null){
                //a-b-c
                //split[0]=a
                //split[1]=b
                //split[2]=c
                 String[] split = line.split("-");
                 Word control = new Word(split);
                if (dict.contains(control)){
                    throw new Exception();
                }
                else{
                    dict.add(new Word(split[0],split[1],split[2]));
                }
                line = bufferedReader.readLine();
             }
             */
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("File could not be opened.");
            e.printStackTrace();
        }
        return dict;
    }
    void add(){
        String input;
        System.out.println("INPUT lesson-word-meaning");
        input=sc.nextLine();
        if (input.split("-").length==3){
            dict.add(new Word(input.split("-")));
        }
        else{
            System.out.print("Invalid input.");
            return;
        }
    }
    void update(){
        try{
            String input;
            System.out.print("Enter word to update:");
            input=sc.nextLine();
            if (dict.contains(new Word(input))){
                int index = dict.indexOf(new Word(input));
                System.out.println(dict.get(index).toString());
                System.out.print("Enter new meaning:");
                dict.get(index).setMeaning(sc.nextLine());
                System.out.println("Meaning updated.");
            }
            else throw new Exception();
        }catch (Exception e){
            System.out.print("Word could not found.");
            e.printStackTrace();
        }
    }
    void delete(){
        try{
            String input;
            System.out.print("Enter word to update:");
            input=sc.nextLine();
            if (dict.contains(new Word(input))){
                int index = dict.indexOf(new Word(input));
                System.out.println(dict.get(index).toString());
                System.out.print("Are you sure?(Y/N)");
                if (sc.nextLine().toLowerCase().equals("y")){
                    dict.remove(index);
                    System.out.println("Word removed.");
                }
            }
            else throw new Exception();
        }catch (Exception e){
            System.out.print("Word could not found.");
        }
    }
    void list(){
        System.out.print("---All words---\n");
        for (Word word:dict){
            System.out.printf("%-10s%-40s%-10s\n",word.getLesson(),word.getMeaning(),word.getWord());
            //System.out.println(word);
        }
    }
    void search(){
        try{
            String input;
            System.out.print("Enter word to search:");
            input=sc.nextLine();
            if (dict.contains(new Word(input))){
                int index = dict.indexOf(new Word(input));
                System.out.println(dict.get(index).toString());
            }
            else throw new Exception();
        }catch (Exception e){
            System.out.print("Word could not found.");
        }
    }
    void save(){
        try {
            FileWriter fileWriter = new FileWriter(file);
            /*
            for (Word word:dict){
                fileWriter.write(word.toString());
            }
            */
            int i=0;
            while(i<dict.size()){
                fileWriter.write(dict.get(i).toString());
                i++;
            }
            fileWriter.close();
            System.out.println("Data saved.");
        } catch (IOException e) {
            System.out.print("File could not be opened.");
        }
    }
    void count(){
        System.out.println(dict.size());
    }
    public void Command(String cmd){
        String check = cmd.toLowerCase();//ADD->add
        if (check.startsWith("add")){
            add();
        }
        else if(check.startsWith("update")){
            update();
        }
        else if(check.startsWith("delete")){
            delete();
        } else if (check.startsWith("list")) {
            list();
        }
        else if (check.startsWith("search")) {
            search();
        }
        else if (check.startsWith("save")){
            save();
        }
        else if (check.startsWith("count")){
            count();
        }
        else{
            System.out.print("Unknown command\n");
        }
    }
}

