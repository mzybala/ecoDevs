CURRENT_DATE=$(date '+%Y%m%d%H%M%S');

function buildImage() {
  docker build . --tag mateuszganko/eco-devs:2
}

case $1 in
   build)
     buildImage
     ;;
esac

echo 'exiting...'
sleep 5

