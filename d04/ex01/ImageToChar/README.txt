# To Compile
mkdir -p target
javac -sourcepath src/java -d target src/java/edu/school21/printer/app/App.java
cp -a src/resources target
jar cfmv target/images-to-chars-printer.jar src/manifest.txt -C target .

# To Run
java -jar target/images-to-chars-printer.jar --white=. --black=0