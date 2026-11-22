import java.util.Scanner;

public class Main {

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

        Song head, tail, current;

        // Loads starting data so every menu option can be tested right away
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
                    head = tail = current = s;
                } else {
                    tail.next = s;
                    s.prev = tail;
                    tail = s;
                }
            }
        }

        // 1. Add Song
        void addSong(int songNumber, String songName, int songDuration) {

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
                head = tail = current = newSong;
            } else {
                tail.next = newSong;
                newSong.prev = tail;
                tail = newSong;
            }

            System.out.println("Song added successfully!");
        }

        // 2. Delete Song
        public void deleteSong(int songNumber) {

            if (head == null) {
                System.out.println("Playlist is empty!");
                return;
            }

            Song temp = head;
            while (temp != null && temp.songNumber != songNumber) {
                temp = temp.next;
            }

            if (temp == null) {
                System.out.println("Song Number not found!");
                return;
            }

            // If deleting the current song, move current to a neighbor
            if (temp == current) {
                current = (temp.next != null) ? temp.next : temp.prev;
            }

            if (temp == head && temp == tail) {
                head = tail = null;
            } else if (temp == head) {
                head = head.next;
                head.prev = null;
            } else if (temp == tail) {
                tail = tail.prev;
                tail.next = null;
            } else {
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
            }

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

        // 4. Display Playlist (visible structure)
        public void displayPlaylist() {

            if (head == null) {
                System.out.println("\n  HEAD -> NULL");
                System.out.println("  TAIL -> NULL");
                System.out.println("  (Playlist is empty)");
                return;
            }

            // Part 1: chain overview with arrows between nodes
            System.out.println("\n  Structure (doubly linked list):");
            StringBuilder chain = new StringBuilder("  NULL <- ");
            Song temp = head;
            while (temp != null) {
                chain.append("[").append(temp.songNumber).append("]");
                if (temp.next != null) {
                    chain.append(" <--> ");
                }
                temp = temp.next;
            }
            chain.append(" -> NULL");
            System.out.println(chain);
            System.out.println("  HEAD = [" + head.songNumber + "]   TAIL = [" + tail.songNumber + "]"
                    + (current != null ? "   CURRENT = [" + current.songNumber + "]" : ""));

            // Part 2: detailed vertical view with markers
            System.out.println("\n  HEAD");
            System.out.println("   |");
            System.out.println("   v");

            temp = head;
            int position = 1;

            while (temp != null) {
                StringBuilder markers = new StringBuilder();
                if (temp == head)    markers.append(" <-- HEAD");
                if (temp == tail)    markers.append(" <-- TAIL");
                if (temp == current) markers.append(" <-- CURRENT");

                System.out.println("  (" + position + ") [" + temp.songNumber + "] "
                        + temp.songName + " (" + temp.duration + "s)" + markers);

                if (temp.next != null) {
                    System.out.println("       ^ |");
                    System.out.println("  prev | | next");
                    System.out.println("       | v");
                }

                temp = temp.next;
                position++;
            }

            System.out.println("   |");
            System.out.println("   v");
            System.out.println("  TAIL -> next = NULL");
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
                    System.out.println("Duration    : " + temp.duration + " seconds");
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
            int count = 0;
            Song temp = head;
            while (temp != null) {
                count++;
                temp = temp.next;
            }

            System.out.println("\nTotal songs in the playlist: " + count);
        }
    }

    // ---------- Complexity printer ----------

    static void showComplexity(String operation, String complexity, String reason) {
        System.out.println("--------------------------------");
        System.out.println("Operation : " + operation);
        System.out.println("Time      : " + complexity);
        System.out.println("Reason    : " + reason);
        System.out.println("--------------------------------");
    }

    // ---------- Safe input helpers ----------

    // Keeps asking until the user types a valid whole number.
    // Returns null only if input has ended (e.g., Ctrl+D), so the program exits cleanly.
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

    // Keeps asking until the user types non-blank text.
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
            System.out.println("\n================================");
            System.out.println("         MUSIC PLAYLIST");
            System.out.println("================================");
            System.out.println("[1] Add Song");
            System.out.println("[2] Delete Song");
            System.out.println("[3] Search Song");
            System.out.println("[4] Display Playlist");
            System.out.println("[5] Play Song");
            System.out.println("[6] Next Song");
            System.out.println("[7] Previous Song");
            System.out.println("[8] Count Total Songs");
            System.out.println("[9] Exit");
            System.out.println("================================");

            Integer choice = readInt(sc, "Enter your choice: ");
            if (choice == null) break; // input stream closed

            switch (choice) {
                case 1: {
                    Integer songNumber = readPositiveInt(sc, "Enter Song Number: ");
                    if (songNumber == null) { running = false; break; }
                    String songName = readText(sc, "Enter Song Name & Artist Name: ");
                    if (songName == null) { running = false; break; }
                    Integer songDuration = readPositiveInt(sc, "Enter Song Duration (seconds): ");
                    if (songDuration == null) { running = false; break; }
                    playlist.addSong(songNumber, songName, songDuration);
                    showComplexity("Add Song", "O(n)",
                            "scans the list for a duplicate Song Number; linking at the tail is O(1)");
                    break;
                }

                case 2: {
                    Integer deleteNumber = readInt(sc, "Enter Song Number to delete: ");
                    if (deleteNumber == null) { running = false; break; }
                    playlist.deleteSong(deleteNumber);
                    showComplexity("Delete Song", "O(n)",
                            "searches for the song; unlinking the node is O(1)");
                    break;
                }

                case 3: {
                    Integer searchNumber = readInt(sc, "Enter Song Number to search: ");
                    if (searchNumber == null) { running = false; break; }
                    playlist.searchSong(searchNumber);
                    showComplexity("Search Song", "O(n)",
                            "linear search from head (best case O(1) if it is the first song)");
                    break;
                }

                case 4:
                    playlist.displayPlaylist();
                    showComplexity("Display Playlist", "O(n)",
                            "visits every node once");
                    break;

                case 5: {
                    Integer playNumber = readInt(sc, "Enter Song Number to play: ");
                    if (playNumber == null) { running = false; break; }
                    playlist.playSong(playNumber);
                    showComplexity("Play Song", "O(n)",
                            "linear search for the song; setting current is O(1)");
                    break;
                }

                case 6:
                    playlist.nextSong();
                    showComplexity("Next Song", "O(1)",
                            "follows one next pointer");
                    break;

                case 7:
                    playlist.previousSong();
                    showComplexity("Previous Song", "O(1)",
                            "follows one prev pointer");
                    break;

                case 8:
                    playlist.countTotalSongs();
                    showComplexity("Count Total Songs", "O(n)",
                            "walks the whole list (a stored counter would make it O(1))");
                    break;

                case 9:
                    System.out.println("Exiting playlist. Goodbye!");
                    showComplexity("Exit", "O(1)", "no traversal needed");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a number from 1 to 9.");
            }
        }

        sc.close();
    }
}