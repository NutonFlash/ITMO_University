SOURCES = $(shell find src -type f -name "*.java")
CLASSES = $(patsubst src/%.java,out/%.class,$(SOURCES))
ANTLR_SOURCES = $(shell find src -type f -name "*.g4")
ANTLR_RESULTS = $(patsubst %.g4,%Parser.java,$(ANTLR_SOURCES))
ANTLR = $(SHAREDLIBDIR)/antlr-4.7.1-complete.jar
MAINCLASS = Main

all: $(ANTLR_RESULTS) $(CLASSES)

run:
	java -cp out:${ANTLR} ${MAINCLASS}

clean:
	rm -rf out
	rm -f src/parser/*.java

%Parser.java: %.g4
	java -jar ${ANTLR} ${ANTLR_SOURCES}

out/%.class: src/%.java out
	javac -cp src:${ANTLR} ${SOURCES} -d out

out:
	mkdir -p out