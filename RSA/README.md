# How it works
RSA works by creating a **matched pair of keys**:

- a **public key** (safe to share)
- a **private key** (kept secret)

The public key is used to encrypt data, and the private key is used to decrypt it.

### Step 1) Generate strong random numbers
The program gets randomness from the operating system and converts it into numbers. Python documents `os.urandom()` as returning bytes suitable for cryptographic use from an OS-specific randomness source. :contentReference[oaicite:0]{index=0}

### Step 2) Create two large prime numbers
The program generates two large random odd numbers and tests whether they are probably prime using the **Miller–Rabin** primality test (repeated 40 times in this implementation).

Why this matters:
- RSA depends on using prime numbers
- If the numbers are not prime, the keys will be invalid or weak

### Step 3) Build the public key
The two prime numbers are multiplied together:

- `n = p × q`

This `n` becomes part of the public key.

The code also uses a standard public exponent (`e = 65537`), which is a common practical choice.

### Step 4) Build the private key
The program computes a matching private value `d` using the Extended Euclidean Algorithm (modular inverse).

This is the key part of RSA:
- `d` is mathematically chosen so it **reverses** what `e` does

That is why encryption and decryption work as a pair.

### Step 5) Test the keypair
The demo encrypts a sample integer message, then decrypts it immediately:

- `m -> c -> m2`

If `m2 == m`, the round trip succeeded, which confirms the generated keys work correctly.

---
