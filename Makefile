default: run

run: *.class
	java TaskManager

*.class:
	javac *.java

clean:
	rm *.class
