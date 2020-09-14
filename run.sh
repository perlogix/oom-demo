#!/bin/bash

DEPS=(docker find basename git)

verify_deps() {
  for e in ${DEPS[*]}; do
    CHECK=$(command -v "$e" 2>/dev/null)
    if [[ "$CHECK" == "" ]]; then
      echo "Looking for installed software, ${DEPS[*]}"
      echo "$e not found!" && echo
      exit 1
    fi
  done
}

set_version() {
  if [[ $VER == "" ]]; then
    VER="latest"
  fi
}

main() {

  verify_deps
  set_version

  REPO_NAME=$(basename "$(git rev-parse --show-toplevel)")

  case $1 in
    build)
      sudo docker build -t "$REPO_NAME":"$VER" .
      ;;

    run)
      sudo docker run --net=host -d --name="$REPO_NAME" --restart always "$REPO_NAME":"$VER"
      ;;

    destroy)
      sudo docker rm -f "$REPO_NAME"
      ;;

    clean)
      find . -name '*.DS_Store' -type f -delete
      find . -name '*.log' -type f -delete
      find . -name '*.sw[klmnop]' -type f -delete
      ;;

    *)
      BANNER="
      $0 build,run,destroy,clean: $1 incorrect argument

      VER='1.0.0' $0 build  # Build image 1.0.0 version

      VER='1.0.0' $0 run    # Run image 1.0.0 version

      $0 build              # Build latest version

      $0 run                # Run latest container

      $0 destroy            # Destroy running container

      $0 clean              # Clean project
      "
      echo "$BANNER"
      exit 1
      ;;

  esac
}

main "$1"