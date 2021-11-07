# To Compile
curl -O https://repo1.maven.org/maven2/com/beust/jcommander/1.81/jcommander-1.81.jar -O https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar &&
mkdir -p lib &&
mv jcommander-1.81.jar lib/ &&
mv JCDP-4.0.2.jar lib/ &&
mkdir -p target &&
jar xvf lib/jcommander-1.81.jar com/ &&
jar xvf lib/JCDP-4.0.2.jar com/ &&
mv com/ target/ &&
cp -a src/resources target &&
javac -sourcepath src/java -cp "lib/JCDP-4.0.2.jar:lib/jcommander-1.81.jar" -d target src/java/edu/school21/printer/app/App.java &&
jar cfmv target/images-to-chars-printer.jar src/manifest.txt -C target .

# To Run
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN

# Для компиляции в Windows использовать ; при перечеслении библиотек в команде javac
