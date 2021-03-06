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

cd ../increase-web
mvn install 
check_errs $? "error in increase-web"
cd ../increase-gatherer
mvn clean appengine:update
check_errs $? "error in increase-gatherer"

curl http://increase-gatherer.appspot.com/player/send