JFLAGS = -g
JC = javac
JAR_C = jar cmf manifestClient.txt tut01.jar

default: all

all: compile jar_client 

jar_client: ./
	$(JAR_C) $^

compile: DFASimulator.java Parser.java Scanner.java Token.java
	$(JC) $^

.PHONY: clean
clean:
	$(RM) *.jar
	$(RM) *.class


