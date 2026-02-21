class TrieNode:
    def __init__(self):
        self.children = {}
        self.is_word = False    # True if a full word ends here


class TrieAutocomplete:
    def __init__(self):
        self.root = TrieNode()
        self.word_rank = {}

    def add_word(self, word):
        word = word.strip().lower()
        if not word:
            return

        node = self.root
        for ch in word:
            if ch not in node.children:
                node.children[ch] = TrieNode()
            node = node.children[ch]

        node.is_word = True

    def find_prefix_node(self, prefix):
        node = self.root
        for ch in prefix:
            if ch not in node.children:
                return None
            node = node.children[ch]
        return node

    def collect_words(self, start_node, prefix):
        results = []
        stack = [(start_node, prefix)]

        while stack:
            node, current_word = stack.pop()

            if node.is_word:
                results.append(current_word)

            # Reverse sort so popping from stack gives A to Z order
            for ch in sorted(node.children.keys(), reverse=True):
                stack.append((node.children[ch], current_word + ch))

        return results

    def autocomplete(self, prefix, limit=10):
        prefix = prefix.strip().lower()
        if not prefix:
            return []

        start_node = self.find_prefix_node(prefix)
        if start_node is None:
            return []

        matches = self.collect_words(start_node, prefix)

        # Sort by frequency rank first, then alphabetically
        # Words not found in ranking file go to the end
        INF = float("inf")
        matches.sort(key=lambda w: (self.word_rank.get(w, INF), w))

        if limit is not None:
            return matches[:limit]
        return matches

    def load_words_file(self, path="Common.txt"):
        #Load words
        with open(path, "r", encoding="utf-8") as f:
            for line in f:
                self.add_word(line)

    def load_frequency_ranking(self, path="Common rank.txt"):
        #Load rank, smaller number == higher frequency
        self.word_rank.clear()
        with open(path, "r", encoding="utf-8") as f:
            for index, line in enumerate(f):
                word = line.strip().lower()
                if word and word not in self.word_rank:
                    self.word_rank[word] = index


def main():
    autocomplete = TrieAutocomplete()
    autocomplete.load_words_file("Common.txt")
    autocomplete.load_frequency_ranking("Common rank.txt")

    while True:
        prefix = input("Type a prefix (blank to quit): ").strip()
        if not prefix:
            break

        suggestions = autocomplete.autocomplete(prefix, limit=10)

        if not suggestions:
            print("Invalid word, please try again.\n")
            continue

        print("Suggestions (most common first):")
        for i, word in enumerate(suggestions, start=1):
            rank = autocomplete.word_rank.get(word)
            rank_text = f"rank {rank + 1}" if rank is not None else "unranked"
            print(f"{i}. {word} ({rank_text})")
        print()


if __name__ == "__main__":
    main()