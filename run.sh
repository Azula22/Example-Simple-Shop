#!/usr/bin/env bash

sbt assembly &&
cd ./target/scala-2.12/ &&
java -jar app.jar