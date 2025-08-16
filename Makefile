.PHONY: build test

build:
    cd app && ./gradlew build

test:
    cd app && ./gradlew test
