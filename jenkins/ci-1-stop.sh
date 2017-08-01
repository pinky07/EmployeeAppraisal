#!/bin/sh
#

#
# Function/Procedures
#

cleanUpDocker() {
	echo 'Looking for dangling images...' 
	DANGLING_IMAGES=`docker images -f dangling=true`
	
	if test -n "$DANGLING_IMAGES";
	then
		echo 'Dangling images found'

		echo 'Attempting to remove dangling images...'
		docker rmi $DANGLING_IMAGES
		echo 'Successfully removed dangling images'

	else
		echo 'No dangling images were found'
	fi
}

removeContainerAndImage() {
	echo 'Looking for containers running based on' $1 '...'
	CONTAINERS_RUNNING=`docker ps -a | grep "$1" | awk '{print $1}'`

	if test -n "$CONTAINERS_RUNNING";
	then

		echo 'Stopping the containers found...'
		CONTAINERS_STOPPED=`echo $CONTAINERS_RUNNING | xargs docker stop`

		if test -n "$CONTAINERS_STOPPED";
		then

			echo "Removing the stopped containers..."
			CONTAINERS_REMOVED=`echo $CONTAINERS_STOPPED | xargs docker rm`

			if test -n "$CONTAINERS_STOPPED";
			then
				echo "Successful"
			else
				echo "ERROR: Couldn't remove running containers!"
			fi
		else
			echo "ERROR: Couldn't stop running containers!"
		fi
	else
		echo 'No containers were found'
	fi

	echo 'Looking for an image named' $1 '...'	
	IMAGE=`docker images | grep "$1" | awk '{print $3}'`

	if test -n "$IMAGE";
	then

		echo 'Removing the image found...'
		IMAGE_REMOVED=`echo $IMAGE | xargs docker rmi`

		if test -n "$IMAGE_REMOVED";
		then 
			echo "Successful"
		else 
			echo "ERROR: Couldn't remove the image found"
		fi
	else 
		echo "No image was found"
	fi
}

#
# Actual script
#

echo 'Cleaning up Docker...'
cleanUpDocker
echo 'Successful'

echo 'Removing previous image and containers'
removeContainerAndImage 'com.gft.employee-appraisal:latest' # Stop application
echo 'Successful'

echo 'Removing previous image and containers'
removeContainerAndImage 'postgres:alpine' #  Stop db
echo 'Successful'

#
# References:
#   - https://www.shellscript.sh/functions.html
#   - https://www.digitalocean.com/community/tutorials/how-to-remove-docker-images-containers-and-volumes