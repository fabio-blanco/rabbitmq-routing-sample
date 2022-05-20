#!/bin/bash

# Just run two consumer instances in parallel
java -jar consumer.jar warning error > /app/logs/logs_from_consumer.log &
java -jar consumer.jar info warning error
