import java.util.*;
import java.io.*;

class LibraryApp {

    // ---------------- BOOK CLASS ----------------
    static class Book implements Comparable<Book> {
        int id;
        String name;
        String author;
        String category;
        boolean issued;

        Book(int id, String name, String author, String category, boolean issued) {
            this.id = id;
            this.name = name;
            this.author = author;
            this.category = category;
            this.issued = issued;
        }

        void show() {
            System.out.println("Book ID: " + id + " | " + name + " | " + author +
                    " | " + category + " | Issued: " + issued);
        }

        @Override
        public int compareTo(Book b) {
            return this.name.compareToIgnoreCase(b.name);
        }
    }

    // ---------------- MEMBER CLASS ----------------
    static class Member {
        int id;
        String name;
        String email;
        List<Integer> borrowedBooks = new ArrayList<>();

        Member(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        void show() {
            System.out.println("Member ID: " + id + " | " + name + " | Email: " + email);
        }
    }

    // ---------------- COLLECTIONS ----------------
    static Map<Integer, Book> bookList = new HashMap<>();
    static Map<Integer, Member> memberList = new HashMap<>();

    static File bookFile = new File("books.txt");
    static File memberFile = new File("members.txt");

    // ---------------- LOAD DATA ----------------
    static void loadData() {
        try {
            if (bookFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(bookFile));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    int id = Integer.parseInt(data[0]);
                    bookList.put(id, new Book(id, data[1], data[2], data[3], Boolean.parseBoolean(data[4])));
                }
                br.close();
            }

            if (memberFile.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(memberFile));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    int id = Integer.parseInt(data[0]);
                    Member m = new Member(id, data[1], data[2]);
                    for (int i = 3; i < data.length; i++) {
                        m.borrowedBooks.add(Integer.parseInt(data[i]));
                    }
                    memberList.put(id, m);
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    // ---------------- SAVE DATA ----------------
    static void saveData() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(bookFile));
            for (Book b : bookList.values()) {
                bw.write(b.id + "," + b.name + "," + b.author + "," + b.category + "," + b.issued);
                bw.newLine();
            }
            bw.close();

            BufferedWriter bw2 = new BufferedWriter(new FileWriter(memberFile));
            for (Member m : memberList.values()) {
                bw2.write(m.id + "," + m.name + "," + m.email);
                for (int bookId : m.borrowedBooks) bw2.write("," + bookId);
                bw2.newLine();
            }
            bw2.close();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // ---------------- OPERATIONS ----------------
    static void addBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Book Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        if (bookList.containsKey(id)) {
            System.out.println("Book ID already exists!");
            return;
        }

        bookList.put(id, new Book(id, name, author, category, false));
        saveData();
        System.out.println("Book added successfully!");
    }

    static void addMember(Scanner sc) {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Member Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        if (memberList.containsKey(id)) {
            System.out.println("Member ID already exists!");
            return;
        }

        memberList.put(id, new Member(id, name, email));
        saveData();
        System.out.println("Member added successfully!");
    }

    static void borrowBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int mid = sc.nextInt();

        if (!bookList.containsKey(bid)) {
            System.out.println("Book ID not found!");
            return;
        }
        if (!memberList.containsKey(mid)) {
            System.out.println("Member ID not found!");
            return;
        }

        Book b = bookList.get(bid);
        Member m = memberList.get(mid);

        if (b.issued) {
            System.out.println("Book already borrowed!");
            return;
        }

        b.issued = true;
        m.borrowedBooks.add(bid);
        saveData();
        System.out.println("Book borrowed successfully!");
    }

    static void returnBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int mid = sc.nextInt();

        if (!bookList.containsKey(bid) || !memberList.containsKey(mid)) {
            System.out.println("Invalid Book or Member ID!");
            return;
        }

        Book b = bookList.get(bid);
        Member m = memberList.get(mid);

        if (!b.issued || !m.borrowedBooks.contains(bid)) {
            System.out.println("This book was not borrowed by this member!");
            return;
        }

        b.issued = false;
        m.borrowedBooks.remove(Integer.valueOf(bid));
        saveData();

        System.out.println("Book returned successfully!");
    }

    static void searchBooks(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter keyword to search: ");
        String key = sc.nextLine().toLowerCase();

        boolean found = false;
        for (Book b : bookList.values()) {
            if (b.name.toLowerCase().contains(key) ||
                    b.author.toLowerCase().contains(key) ||
                    b.category.toLowerCase().contains(key)) {
                b.show();
                found = true;
            }
        }
        if (!found) System.out.println("No matching books found!");
    }

    static void sortBooks() {
        List<Book> list = new ArrayList<>(bookList.values());
        Collections.sort(list);
        System.out.println("--- Sorted Books ---");
        for (Book b : list) b.show();
    }

    // ---------------- MAIN MENU ----------------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        loadData();

        while (true) {
            System.out.println("\n--- Welcome to City Library ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Show Sorted Books");
            System.out.println("7. Exit");
            System.out.print("Choose: ");

            int choice;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else {
                sc.nextLine();
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (choice) {
                case 1: addBook(sc); break;
                case 2: addMember(sc); break;
                case 3: borrowBook(sc); break;
                case 4: returnBook(sc); break;
                case 5: searchBooks(sc); break;
                case 6: sortBooks(); break;
                case 7:
                    saveData();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
