#!/bin/bash

# 1. Build the project
npm run build

# 2. Prepare standalone folder
cp -r public .next/standalone/
cp -r .next/static .next/standalone/.next/

# 3. Start the server
cd .next/standalone
node server.js
