#Makefile For Assignment 2
#Philani Mhlongo
#02/09/2021
JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
DOCSDIR=docs

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= WordDictionary.class WordRecord.class Score.class WordApp.class Caught.class Update.class WordPanel.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

docs:
	javadoc -d $(DOCSDIR) $(SRCDIR)/*.java

clean:
	rm $(BINDIR)/*.class
	
run: $(CLASS_FILES)
	java -cp $(BINDIR)  WordApp 10 2 example_dict.txt
