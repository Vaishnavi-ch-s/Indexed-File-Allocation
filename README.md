# 📂 Indexed File Allocation Simulator (Java Swing GUI)
This is a **Java Swing-based GUI application** that simulates **Indexed File Allocation**, a memory management method used in Operating Systems. The simulation visually allocates memory blocks for files and displays block metadata interactively.
---
## 📌 Features
- Accepts user input for:
  - File name
  - Number of data blocks required
- Automatically allocates:
  - 1 index block (🔵 blue)
  - N data blocks (🟢 green)
- Validations:
  - Duplicate file name check
  - Checks for sufficient free blocks
-  Interactive GUI:
  - Displays 100 memory blocks in a 10x10 grid
  - Click on an index block to **view its file details** (index & data blocks)
- Color Palette:
  - White = Free block
  - Blue = Index block
  - Green = Data block
---
## Tools Used
- Language: **Java**
- GUI Library: **Swing (javax.swing)**
- IDE: **Visual Studio Code**
---
## Files
- `IndexedAllocationSimulation.java` → Java source code with full GUI logic
- `README.md` → Project documentation
---
## Concepts Used
- Operating System:
  - Indexed file allocation
  - Block-based memory simulation
- Java:
  - Swing (GUI components)
  - HashMap, ArrayList, EventListeners
  - Object-Oriented Design (Classes for File, Block)
- UX:
  - Visual memory model
  - Color-coded user feedback
---
## Author
**Vaishnavi Chatrati**  
Final Year CSE Student  
Built using Java + Swing in Visual Studio Code
