# Trie Autocomplete (Python)

A simple command-line autocomplete project built from scratch in Python using a **Trie** (prefix tree).

This project loads a word list from a text file (`Common.txt`) and lets the user type a prefix to get matching word suggestions in the terminal. The implementation stores each node’s children in a **sorted linked list**, which is a nice low-level design choice that shows data structure fundamentals beyond using Python dictionaries.

---

## How it works

- You type a few letters (for example: `app`)
- The program looks through its word list
- It returns all words that start with those letters (for example: `apple`, `apply`, `application`)

Instead of scanning the full word list every time, it organizes words in a tree structure so it can jump directly to the relevant branch.

---

## Why I Built It

This project was built to practice:

- **core data structures** (Trie / prefix tree)
- **linked list-based node storage**
- **search logic and traversal**
- **clean command-line interaction**

It demonstrates that I can implement a common feature (autocomplete) from first principles, rather than relying on built-in libraries. 

---
