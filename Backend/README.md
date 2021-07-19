-- run on docker (-v add volume to google credentials, -e add env variable to credential)
docker run -p 8080:8080 -v ~/.config/gcloud:/var/lib/jetty/.config/gcloud -e GOOGLE_APPLICATION_CREDENTIALS=/var/lib/jetty/.config/gcloud/application_default_credentials.json myapp


mvn clean package
docker build -t project-insight-be .  
docker run -p 8080:8080 project-insight-be


local development
mvn jetty:run
