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


mvn clean install 
check_errs $? "error in increase-web"

cd increase-appengine
mvn appengine:update
check_errs $? "error in increase-appengine"
cd ..

cd increase-gatherer
mvn clean appengine:update
check_errs $? "error in increase-gatherer"

echo "curl http://increase-gather.appspot.com/player/send"
curl http://increase-gather.appspot.com/player/send
