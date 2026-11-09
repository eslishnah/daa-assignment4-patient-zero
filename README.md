# Assignment 4. Patient Zero

- Student ID: 252342
- Name: Sandzhar Fazyl
- Group: SE-2536

## Run

Use JDK 17 or newer. From the project root, run:

```text
java tools/Check.java
java -cp out Main
```

`Check` compiles the project and checks the `tiny`, `small`, and `dense` examples. `Main` reads the four files in `data/` and writes the lab console to `output/outbreak.html`.

## Program output

```text
Mutations: 96
Shared core: 994
Marker hits: 5
First hit: 4858
Border length: 3
Shared words: 401
Tests needed: 27
Cheapest testing: 103
Longest chain: 23
```

## Questions

**Mission 1.** The cell `d[i][j]` is the fewest edits needed to turn the first `i` letters of A into the first `j` letters of B. I take the minimum of the cell above plus a deletion, the cell to the left plus an insertion, and the diagonal plus one if the letters differ. The first row and column give the cost of building or removing a prefix one letter at a time.

**Mission 2.** A common subsequence keeps the letters in order and can skip letters, while a common substring has to be one continuous block in each strain. In my data there are 462 equal letters at the same index, but the shared core has length 994 because an insertion or deletion can shift a matching stretch to another index.

**Mission 3.** The prefix function gives the length of the longest proper prefix that is also a suffix for each prefix of the marker. On a mismatch KMP reuses a shorter suffix of what has already matched and keeps the text pointer in place until it can continue forward. My marker `CAGATCAG` has a border of length 3 because its longest proper prefix that also ends the marker is `CAG`.

**Mission 4.** Two different windows can produce the same hash, so I compare their letters before counting a match. When many different windows share a hash, an A window can make the program check many candidates from B and spend up to `k` letter comparisons on each candidate.

**Mission 5.** The two states are the cheapest cost in a subtree when its root is tested and when its root is skipped. If the root is tested, I pay its cost and take the cheaper state for each child. If it is skipped, I take the tested state for every child. The person with the most links can be expensive, for example a star with a center costing 9 and two leaves costing 1 each is cheaper to cover by testing both leaves.

## AI use

I used ChatGPT (Codex) to plan the assignment, implement the Java algorithms, run the checks and benchmark, prepare the lab console screenshot, and draft this report.
