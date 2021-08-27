# To Compile
mkdir -p target
jar xvf lib/jcommander-*.**.jar com/
jar xvf lib/JCDP-*.*.*.jar com/
mv com/ target/
cp -a src/resources target
javac -sourcepath src/java -cp "lib/JCDP-4.0.2.jar:lib/jcommander-1.81.jar" -d target src/java/edu/school21/printer/app/App.java
jar cfmv target/images-to-chars-printer.jar src/manifest.txt -C target .

# To Run
java -jar target/images-to-chars-printer.jar --white={white char or color} --black={black char or color}
