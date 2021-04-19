#!/usr/bin/env bash

rm -rf target

mvn clean package

java -jar target/beatstep-converter-1.0.0.jar
