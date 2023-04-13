#docker exec -it paas-solr-1 solr delete -c tecadigitale_delivery
#docker exec -it paas-solr-1 solr delete -c tecadigitale
ID_ISTANZA=`sudo docker ps | grep -i solr | awk '{print $1}'`
docker exec -it $ID_ISTANZA solr create_core -c tecadigitale -d /opt/solr/server/solr/configsets/tecadigitale
docker exec -it $ID_ISTANZA solr create_core -c tecadigitale_delivery -d /opt/solr/server/solr/configsets/tecadigitale_delivery