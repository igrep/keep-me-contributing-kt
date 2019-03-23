#!/bin/bash

set -eux

bash ./gradlew browser:runDceKotlinJs
mkdir -p public/dist/
cp browser/dist/*.graphql browser/dist/*.js public/dist/
cp -r browser/{css,img,index.html} public
netlify deploy --prod --dir=public
