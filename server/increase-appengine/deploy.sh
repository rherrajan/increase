#!/bin/sh

check_errs() {
  # Function. Parameter 1 is the return code
  # Para. 2 is text to display on failure.
  if [ "${1}" -ne "0" ]; then
    echo "ERROR # ${1} : ${2}"
    # as a bonus, make our script exit with the right error code.
    exit ${1}
  fi
}


cd ..
mvn clean install --projects increase-web
check_errs $? "error in increase-web"

cd increase-appengine
mvn clean appengine:update
check_errs $? "error in increase-appengine"

echo "http://increase-agents.appspot.com/player/nearby?lat=50651379&lng=87419097"
curl "http://increase-agents.appspot.com/player/nearby?lat=50651379&lng=87419097"
