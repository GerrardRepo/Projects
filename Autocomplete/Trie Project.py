class Child:
    def __init__(self, ch, node, nxt=None):
        self.ch = ch
        self.node = node
        self.nxt = nxt

class Node:
    def __init__(self):
        self.end = False
        self.head = None 

    def get(self, ch):
        cur = self.head
        while cur:
            if cur.ch == ch:
                return cur.node
            if cur.ch > ch: 
                return None
            cur = cur.nxt
        return None

    def add(self, ch):
        prev, cur = None, self.head
        while cur and cur.ch < ch:
            prev, cur = cur, cur.nxt
        if cur and cur.ch == ch:
            return cur.node

        new_node = Node()
        new_child = Child(ch, new_node, cur)
        if prev is None:
            self.head = new_child
        else:
            prev.nxt = new_child
        return new_node

    def children(self):
        cur = self.head
        while cur:
            yield cur.ch, cur.node
            cur = cur.nxt

class Trie:
    def __init__(self):
        self.root = Node()

    def insert(self, word):
        n = self.root
        for ch in word:
            n = n.add(ch)
        n.end = True

    def _walk(self, prefix):
        n = self.root
        for ch in prefix:
            n = n.get(ch)
            if n is None:
                return None
        return n

    def autocomplete(self, prefix):
        start = self._walk(prefix)
        if start is None:
            return []

        res = []
        stack = [(start, prefix)]

        while stack and len(res) < float("inf"):
            n, s = stack.pop()
            if n.end:
                res.append(s)
            kids = list(n.children())
            for ch, child in reversed(kids):
                stack.append((child, s + ch))

        return res
    
    def load_words(trie, path="Common.txt"):
        f = open(path, "r", encoding="utf-8")
        for line in f:
            w = line.strip()
            if w:
                trie.insert(w)
        f.close()


t = Trie()
t.load_words()
while True:
    p = input("prefix (blank to quit): ")
    if not p:
        break
    print(t.autocomplete(p))