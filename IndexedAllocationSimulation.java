import java.awt.*;// For GUI components
import java.util.ArrayList;               
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;             // For working with lists
import javax.swing.*;// For layout and color


public class IndexedAllocationSimulation extends JFrame {
    private static final int TOTAL_BLOCKS = 100;         // Total blocks in disk
    private JPanel diskPanel;                            // Panel to hold block buttons
    private Block[] blocks = new Block[TOTAL_BLOCKS];    // Array of blocks
    private Map<String, FileEntry> fileMap = new HashMap<>(); // Stores file metadata

    public IndexedAllocationSimulation() {
        setTitle("Indexed File Allocation");
        setSize(500, 600);                            // Window size
        setDefaultCloseOperation(EXIT_ON_CLOSE);     // Close operation
        setLayout(new BorderLayout());

        diskPanel = new JPanel();
        diskPanel.setLayout(new GridLayout(10, 10, 3, 3)); // 10x10 grid for 100 blocks
        initializeBlocks();                               // Initialize all blocks

        JButton addButton = new JButton("Add File");      // Button to add file
        addButton.addActionListener(e -> addFile());      // Click event to add file

        add(diskPanel, BorderLayout.CENTER);              // Add grid to center
        add(addButton, BorderLayout.SOUTH);               // Add button at bottom

        setVisible(true);                                 // Show window
    }

    // Create and display 100 blocks
    private void initializeBlocks() {
        for (int i = 0; i < TOTAL_BLOCKS; i++) {
            blocks[i] = new Block(i);              // Create new block
            diskPanel.add(blocks[i].button);       // Add its button to panel
        }
    }

    // Add a new file with index and data blocks
    private void addFile() {
        // Get file name
        String name = JOptionPane.showInputDialog(this, "Enter file name:");
        if (name == null || name.isEmpty() || fileMap.containsKey(name)) {
            JOptionPane.showMessageDialog(this, "Invalid or duplicate name!");
            return;
        }

        // Get file size (number of data blocks)
        int size;
        try {
            String input = JOptionPane.showInputDialog(this, "Enter number of data blocks:");
            size = Integer.parseInt(input);
            if (size <= 0)
                throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid number!");
            return;
        }

        // Check if enough free blocks are available (1 index + N data)
        List<Integer> free = getFreeBlocks();
        if (free.size() < size + 1) {
            JOptionPane.showMessageDialog(this, "Not enough free blocks!");
            return;
        }

        // Randomly shuffle free blocks
        Collections.shuffle(free);
        int indexBlock = free.remove(0);                    // Allocate one for index
        List<Integer> dataBlocks = free.subList(0, size);   // Allocate N data blocks

        // Color index block blue
        blocks[indexBlock].allocate(name + " (Index)", Color.BLUE);
        // Color data blocks green
        for (int blk : dataBlocks) {
            blocks[blk].allocate(name, Color.GREEN);
        }

        // Store file metadata
        fileMap.put(name, new FileEntry(indexBlock, new ArrayList<>(dataBlocks)));

        // Add click event on index block to show file info
        blocks[indexBlock].button.addActionListener(e -> showFileInfo(name));
    }

    // Return list of all unallocated block IDs
    private List<Integer> getFreeBlocks() {
        List<Integer> free = new ArrayList<>();
        for (int i = 0; i < TOTAL_BLOCKS; i++) {
            if (!blocks[i].allocated) {
                free.add(i);
            }
        }
        return free;
    }

    // Show a dialog with file info
    private void showFileInfo(String name) {
        FileEntry entry = fileMap.get(name);
        StringBuilder sb = new StringBuilder();
        sb.append("File Name: ").append(name)
          .append("\nIndex Block: ").append(entry.indexBlock)
          .append("\nData Blocks: ").append(entry.dataBlocks);
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    // Main method to run the program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IndexedAllocationSimulation());
    }

    // Inner class representing a block
    class Block {
        int id;
        boolean allocated = false;
        JButton button;

        Block(int id) {
            this.id = id;
            button = new JButton(String.valueOf(id));   // Show block ID
            button.setBackground(Color.WHITE);          // Initially white (free)
            button.setOpaque(true);
            button.setBorderPainted(false);
        }

        // Allocate the block with label and color
        void allocate(String label, Color color) {
            allocated = true;
            button.setText(label);             // Show file name
            button.setBackground(color);       // Change color
        }
    }

    // Class to store file's index and data block info
    class FileEntry {
        int indexBlock;
        List<Integer> dataBlocks;

        FileEntry(int indexBlock, List<Integer> dataBlocks) {
            this.indexBlock = indexBlock;
            this.dataBlocks = dataBlocks;
        }
    }
}
