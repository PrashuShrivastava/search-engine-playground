# search-engine-playground
Mini search engine
# 📘 Mini Search Engine in Java  
*A beginner-friendly Search Engineer prep project built inside GitHub Codespaces.*

This project builds a fully working mini search engine from scratch using **pure Java**, designed to help prepare for **Search Engineer / Machine Coding rounds** (Licious, Swiggy, Meesho, Zomato, Uber IR, etc.).  
It runs entirely inside **GitHub Codespaces**, meaning no local installation is required.

---

## 🚀 Features Implemented

### ✔ Tokenization  
- Lowercases text  
- Removes punctuation  
- Splits into normalized tokens  

### ✔ Inverted Index  
Builds and stores:
token → {document IDs}

### ✔ Boolean Search  
- **AND search** → documents containing *all* query terms  
- **OR search** → documents containing *any* query term  
- Default search = AND

### ✔ Ranking Based on Term Frequency  
Documents with more matching query terms appear higher.  
This is the foundation for TF-IDF scoring.

### ✔ Interactive CLI  
Example queries:
and chicken mutton
or chicken fish
chicken mutton
exit

---

## 🧱 Project Structure

search-engine-playground/
├── pom.xml
└── src/
└── main/
└── java/
└── com/searchplayground/core/
├── Document.java
├── Tokenizer.java
├── InvertedIndex.java
├── SearchEngineApp.java

---

## 🛠 Requirements

You do NOT need to install Java locally.

All you need:

- A GitHub account  
- A browser  
- Codespaces enabled

Java + Maven come preinstalled in Codespaces.

---

## 🧑‍💻 Running the Project in GitHub Codespaces

### 1️⃣ Open Codespace  
1. Go to your GitHub repository  
2. Click **`Code` → `Codespaces` → `Create Codespace on main`**

This will open a cloud-based VS Code environment.

---

### 2️⃣ Build the Project  
Inside the Codespaces terminal:

```bash
mvn -q package
Expected output:

BUILD SUCCESS

3️⃣ Run the Search Engine
bash

java -cp target/search-engine-playground-1.0-SNAPSHOT.jar com.searchplayground.core.SearchEngineApp
You will see:

Type your search query.
 - AND search: and chicken mutton
 - OR search:  or chicken mutton
 - Default AND: chicken mutton
Type 'exit' to quit.
🧠 How It Works
🔹 1. Tokenizer
Cleans input text, lowercases, removes punctuation, splits into tokens.

🔹 2. Inverted Index
Maps each token to a set of documents containing that word.

Example:


chicken → {1,2,4}
mutton  → {2}
fish    → {3}
🔹 3. Boolean Retrieval
AND = intersection of posting lists

OR = union of posting lists

🔹 4. Ranking
Scores each document by counting how many query tokens it contains.

Example (for or chicken mutton):


Doc 2 → score 2
Doc 4 → score 2
Doc 1 → score 1
Sorted output = ranked by relevance.

📦 Example Run
Input:
python

or chicken mutton
Output:


Results (ranked by relevance):

Doc 2 (score 2): Fresh mutton and chicken available
Doc 4 (score 2): Boneless chicken breast and chicken wings
Doc 1 (score 1): Chicken biryani with spicy masala


🔮 Upcoming Enhancements

⬜ Stop Words
Remove “the, with, and, is” etc. → improved relevance.

⬜ Load Documents from Files
Index .txt files from a /data folder.

⬜ Prefix Search / Autocomplete
Implement using Trie.

⬜ TF-IDF Scoring
Realistic search relevance.

⬜ Fuzzy Search
Levenshtein distance (edit distance).

🎯 Ideal Interview Preparation For
This project demonstrates the core IR concepts needed for:

Search Engineer roles

Machine Coding rounds

Java Backend + IR interviews

Lucene / Elasticsearch fundamentals

Food-tech Search roles (Licious, Swiggy, Zomato, Meesho)

It shows:

Clean Java code

Indexing

Tokenization

Boolean search

Ranking logic

Console interactivity

IR system thinking

🤝 Contributions
Feel free to enhance:

Ranking algorithms

Tokenizer logic

Additional query types

Trie-based autocomplete

File-based indexing

Pull Requests welcome.

📄 License
MIT License — free to learn, modify, and use.