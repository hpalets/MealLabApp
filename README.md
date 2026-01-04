# 🍽️ MealLab Application

Java εφαρμογή που καταναλώνει το **TheMealDB API** και επιτρέπει αναζήτηση συνταγών μέσω γραφικού περιβάλλοντος (**JavaFX**).

Η εργασία υλοποιήθηκε στο πλαίσιο του μεταπτυχιακού προγράμματος  
**«Πληροφοριακά Συστήματα και Υπηρεσίες»**.

------------------

## 🧩 Δομή Project

- **api** → HTTP κλήσεις, JSON parsing, επιχειρησιακή λογική  
- **gui** → Γραφικό περιβάλλον χρήστη (JavaFX)


## 🚀 Εκτέλεση

### 1️⃣ Build API

```bash
cd MealLabApp/api
mvn clean install
```

### 2️⃣ Start Gui
```bash
cd ../gui
mvn javafx:run
```

### Maven 
You have to install the Maven.zip in order to be able to run the "mvn" command
