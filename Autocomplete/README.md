# Trie Autocomplete (Ranked by Word Frequency)

A simple command line autocomplete project built in Python.

This project suggests words based on a typed prefix (for example, typing `th`), then **ranks the suggestions by how common the words are** using a separate frequency file.

This project works like a basic search bar autocomplete:

1. The user types a few letters
2. The program finds all words that begin with those letters
3. The program shows the most commonly used words first

So instead of just finding matches, it also tries to return the **most useful matches first**.

---

## Why I Built This

I built this project to practice implementing a real feature (autocomplete) from first principles rather than relying on built in search libraries.

It demonstrates:

- data structure design (Trie)
- search logic and traversal
- ranking and sorting logic
- file loading and command-line interaction

---

## How It Works

### 1) Load a word list
The program reads words from `Common.txt` and stores them in a Trie.

A Trie is a tree-like structure where words with the same starting letters share the same path.

Example:
- `car`
- `card`
- `care`

These all share the same beginning (`c -> a -> r`), so searching by prefix becomes efficient.

---

### 2) Load a ranking file
The program reads `Common rank.txt`, which contains words ordered from **most frequent** to **least frequent**.

This gives the program a ranking map like:

- `the` → rank 1
- `and` → rank 2
- `zebra` → much lower rank

That rank is later used to sort autocomplete suggestions.

---

### 3) Find the prefix in the Trie
When a user types a prefix (e.g. `ap`), the program walks through the Trie one letter at a time.

- If that prefix path exists, it continues
- If not, it returns no suggestions

---

### 4) Collect all matching words
Once the prefix node is found, the program explores everything below that node and collects complete words.

This ensures every suggestion actually starts with the user’s input.

---

### 5) Sort suggestions by frequency
After collecting matches, the program sorts them by the ranking file so the **most common words appear first**.

If a word is not found in the ranking file, it is placed later.

---

