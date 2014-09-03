#!/usr/bin/env bash

# Run this after installing the SQL schema from jooq-sandbox.sql
mvn exec:java -Dexec.mainClass="org.jooq.util.GenerationTool" -Dexec.args="/library.xml"
