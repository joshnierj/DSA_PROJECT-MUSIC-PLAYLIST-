import java.util.Scanner;

public class Playlist2 {

    // Node Class
    static class Song {
        int songNumber;
        String songName;
        int duration;
        Song prev, next;

        Song(int songNumber, String songName, int songDuration) {
            this.songNumber = songNumber;
            this.songName = songName;
            this.duration = songDuration;
            this.prev = null;
            this.next = null;
        }
    }

    static class MusicPlaylist {

        static final int MAX_SONGS = 10;

        Song head, tail, current;
        int size = 0;

        // Loads 6 hard-coded songs at startup
        void loadSampleData() {
            int[] numbers = {101, 102, 103, 104, 105, 106};
            String[] names = {
                "Blinding Lights - The Weeknd",
                "Shape of You - Ed Sheeran",
                "Levitating - Dua Lipa",
                "Bad Guy - Billie Eilish",
                "Perfect - Ed Sheeran",
                "Stay - The Kid LAROI & Justin Bieber"
            };
            int[] durations = {200, 234, 203, 194, 263, 141};

            for (int i = 0; i < numbers.length; i++) {
                Song s = new Song(numbers[i], names[i], durations[i]);

                if (head == null) {
                    head = tail = current = s;   // first song is also the current song
                } else {
                    tail.next = s;
                    s.prev = tail;
                    tail = s;
                }
                size++;
            }
        }

        // 1. Add Song
        void addSong(int songNumber, String songName, int songDuration) {

            // Full playlist
            if (size >= MAX_SONGS) {
                System.out.println("Playlist is full! Maximum of " + MAX_SONGS + " songs reached.");
                return;
            }

            if (songNumber <= 0) {
                System.out.println("Song Number must be greater than zero!");
                return;
            }
            if (songName == null || songName.trim().isEmpty()) {
                System.out.println("Song name cannot be empty!");
                return;
            }
            if (songDuration <= 0) {
                System.out.println("Duration must be greater than zero!");
                return;
            }

            // Check for duplicate Song Number
            Song temp = head;
            while (temp != null) {
                if (temp.songNumber == songNumber) {
                    System.out.println("Song Number already exists!");
                    return;
                }
                temp = temp.next;
            }

            Song newSong = new Song(songNumber, songName.trim(), songDuration);

            if (head == null) {
                head = tail = newSong;
                current = newSong;
            } else {
                tail.next = newSong;
                newSong.prev = tail;
                tail = newSong;
            }

            size++;
            System.out.println("Song added successfully!");
        }

        // 2. Delete Song
        public void deleteSong(int songNumber) {

            // Case 1: playlist is empty
            if (head == null) {
                System.out.println("Playlist is empty!");
                return;
            }

            // Search for the song with this song number
            Song temp = head;
            while (temp != null && temp.songNumber != songNumber) {
                temp = temp.next;
            }

            // Case 2: song number not found
            if (temp == null) {
                System.out.println("Song Number not found!");
                return;
            }

            // If the song being deleted is the current song, move current to a neighbor
            if (temp == current) {
                current = (temp.next != null) ? temp.next : temp.prev;
            }

            // Case 3: it's the only song
            if (temp == head && temp == tail) {
                head = tail = null;
            }
            // Case 4: deleting the first song
            else if (temp == head) {
                head = head.next;
                head.prev = null;
            }
            // Case 5: deleting the last song
            else if (temp == tail) {
                tail = tail.prev;
                tail.next = null;
            }
            // Case 6: deleting a song in the middle
            else {
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
            }

            size--;
            System.out.println("Song deleted successfully!");
        }

        // 3. Search Song
        public void searchSong(int songNumber) {
            if (head == null) {
                System.out.println("Playlist is empty!");
                return;
            }

            Song temp = head;
            while (temp != null) {
                if (temp.songNumber == songNumber) {
                    System.out.println("Song Found!");
                    System.out.println("Number: " + temp.songNumber);
                    System.out.println("Name: " + temp.songName);
                    System.out.println("Duration: " + temp.duration + " seconds");
                    return;
                }
                temp = temp.next;
            }

            System.out.println("Song not found!");
        }

        // 4. Display Playlist
        public void displayPlaylist() {

            if (head == null) {
                System.out.println("  Playlist is empty!");
                return;
            }

            System.out.println("  HEAD");
            System.out.println("   |");
            System.out.println("   v");

            Song temp = head;
            int position = 1;

            while (temp != null) {
                System.out.println("  (" + position + ") [" + temp.songNumber + "] "
                        + temp.songName + " (" + temp.duration + "s)");

                if (temp.next != null) {
                    System.out.println("     <-->");
                }

                temp = temp.next;
                position++;
            }

            System.out.println("   |");
            System.out.println("   v");
            System.out.println("  TAIL -> NULL");
        }

        // 5. Play Song
        public void playSong(int songNumber) {

            if (head == null) {
                System.out.println("Playlist is empty!");
                return;
            }

            Song temp = head;
            while (temp != null) {
                if (temp.songNumber == songNumber) {
                    current = temp; // Next/Previous start from this song
                    System.out.println("\nNow Playing:");
                    System.out.println("Song Number : " + temp.songNumber);
                    System.out.println("Song Name   : " + temp.songName);
                    System.out.println("Duration    : " + temp.duration);
                    return;
                }
                temp = temp.next;
            }

            System.out.println("Song Number not found!");
        }

        // 6. Next Song
        public void nextSong() {
            if (head == null || current == null) {
                System.out.println("Playlist is empty!");
                return;
            }

            if (current.next == null) {
                System.out.println("You are already at the last song: " + current.songName);
                return;
            }

            current = current.next;
            System.out.println("Now playing next song: [" + current.songNumber + "] "
                    + current.songName + " (" + current.duration + "s)");
        }

        // 7. Previous Song
        public void previousSong() {
            if (head == null || current == null) {
                System.out.println("Playlist is empty!");
                return;
            }

            if (current.prev == null) {
                System.out.println("You are already at the first song: " + current.songName);
                return;
            }

            current = current.prev;
            System.out.println("Now playing previous song: [" + current.songNumber + "] "
                    + current.songName + " (" + current.duration + "s)");
        }

        // 8. Count Total Songs
        public void countTotalSongs() {
            if (head == null) {
                System.out.println("\nPlaylist is empty! Total songs: 0");
                return;
            }

            int count = 0;
            Song temp = head;
            while (temp != null) {
                count++;
                temp = temp.next;
            }

            System.out.println("\nTotal songs in the playlist: " + count);
        }
    }

    // Prints only the time complexity
    static void showComplexity(String complexity) {
        System.out.println("  Time complexity: " + complexity);
    }

    // ---------- Safe input helpers ----------

    // Keeps asking until a valid whole number is typed.
    // Returns null only if input has ended (e.g., Ctrl+D).
    static Integer readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!sc.hasNextLine()) {
                return null;
            }
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a whole number.");
                continue;
            }
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! '" + line + "' is not a valid whole number.");
            }
        }
    }

    // Keeps asking until non-blank text is typed.
    static String readText(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!sc.hasNextLine()) {
                return null;
            }
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            return line;
        }
    }

    // Keeps asking until the number is greater than zero.
    static Integer readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            Integer value = readInt(sc, prompt);
            if (value == null) return null;
            if (value > 0) return value;
            System.out.println("Value must be greater than zero. Please try again.");
        }
    }

    // ---------- Main ----------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MusicPlaylist playlist = new MusicPlaylist();
        playlist.loadSampleData();
        System.out.println("Loaded 6 sample songs at startup.");
        boolean running = true;

        while (running) {
            System.out.println("----------------------------------------------------");
            System.out.println("         MUSIC PLAYLIST by Jared & Friends ");
            System.out.println("----------------------------------------------------");
            System.out.println("Add Song");
            System.out.println("Delete Song");
            System.out.println("Search Song");
            System.out.println("Display Playlist");
            System.out.println("Play Song");
            System.out.println("Next Song");
            System.out.println("Previous Song");
            System.out.println("Count Total Songs");
            System.out.println("Exit");
            System.out.println("----------------------------------------------------");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextLine()) {
                break; // input stream closed
            }
            String choice = sc.nextLine().trim().toLowerCase();

            switch (choice) {
                case "add song": {
                    Integer songNumber = readPositiveInt(sc, "Enter Song Number: ");
                    if (songNumber == null) { running = false; break; }
                    String songName = readText(sc, "Enter Song Name & Artist Name: ");
                    if (songName == null) { running = false; break; }
                    Integer songDuration = readPositiveInt(sc, "Enter Song Duration (seconds): ");
                    if (songDuration == null) { running = false; break; }
                    playlist.addSong(songNumber, songName, songDuration);
                    showComplexity("O(n)");
                    break;
                }

                case "delete song": {
                    Integer deleteNumber = readInt(sc, "Enter Song Number to delete: ");
                    if (deleteNumber == null) { running = false; break; }
                    playlist.deleteSong(deleteNumber);
                    showComplexity("O(n)");
                    break;
                }

                case "search song": {
                    Integer searchNumber = readInt(sc, "Enter Song Number to search: ");
                    if (searchNumber == null) { running = false; break; }
                    playlist.searchSong(searchNumber);
                    showComplexity("O(n)");
                    break;
                }

                case "display playlist":
                    playlist.displayPlaylist();
                    showComplexity("O(n)");
                    break;

                case "play song": {
                    Integer playNumber = readInt(sc, "Enter Song Number to play: ");
                    if (playNumber == null) { running = false; break; }
                    playlist.playSong(playNumber);
                    showComplexity("O(n)");
                    break;
                }

                case "next song":
                    playlist.nextSong();
                    showComplexity("O(1)");
                    break;

                case "previous song":
                    playlist.previousSong();
                    showComplexity("O(1)");
                    break;

                case "count total songs":
                    playlist.countTotalSongs();
                    showComplexity("O(n)");
                    break;

                case "exit":
                    System.out.println("Exiting playlist. Goodbye!");
                    showComplexity("O(1)");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please type one of the menu options exactly as shown.");
            }
        }

        sc.close();
    }
}
