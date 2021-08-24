# To Compile (Windows)
mkdir -p target
javac -sourcepath src\java -d target src\java\edu\school21\printer\app\App.java

# To Run (Windows)
java -cp target edu.school21.printer.app.App --white={white char} --black={black char} path_to_image