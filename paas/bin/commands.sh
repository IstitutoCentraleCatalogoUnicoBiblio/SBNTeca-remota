#!/bin/bash


function build(){
    _maven
    _docker
    cd ../app/skosloader && mvn install
    cd ../sbnmarc-mag && mvn install
    cd ../mets2mag && mvn install
    cd ../oaicat-modified && mvn install
    cd ../
    cd sbnteca-remota-parent && mvn spring-boot:build-image
}

function install() {
    _docker
    # set file .env
    if [ ! -f .env ]; then
        touch .env
    fi

#    if ! grep -q "MYSQL_ROOT_PASSWORD" .env; then
#        read -r -p ${bold}"Set MYSQL_ROOT_PASSWORD:${normal} " inputstr
#        echo "MYSQL_ROOT_PASSWORD=$inputstr" >> .env
#    fi
    source bin/initDirectory.sh
    echo -e "${bold}\nStart mysql for init ...\n${normal}"
    ${COMPOSE} --env-file .env -f ${COMPOSEFILE} --compatibility up -d db
    sleep 5
    create-solr-cores
}

function create-solr-cores(){
    echo -e "${bold}\nCreate cores for init ...\n${normal}"
    ${COMPOSE} --env-file .env -f ${COMPOSEFILE} --compatibility up -d solr
    sleep 5
    source bin/create_cores.sh
}

function start() {
    _docker
    _env

    # Start all containers
    echo -e "${bold}\nStart containers...\n${normal}"

    ${COMPOSE} --env-file .env -f ${COMPOSEFILE} --compatibility up -d

    echo -e "${bold}\nContainer state:\n${normal}"
    ${COMPOSE} --env-file .env -f ${COMPOSEFILE} ps
}


function stop() {
    _docker

    read -r -p "${bold}Are you sure? [y/N] ${normal}" response
    if [[ "$response" =~ ^([yY][eE][sS]|[yY])+$ ]]; then
        # Stop and remove all containers
        ${COMPOSE} --env-file .env -f ${COMPOSEFILE} down
    else
        exit 0
    fi
}

function delete-upgrade-cores() {
    _docker

    read -r -p "${bold}Are you sure? [y/N] ${normal}" response
    if [[ "$response" =~ ^([yY][eE][sS]|[yY])+$ ]]; then
        read -r -p "${bold}Are you really sure? [y/N] ${normal}" response
        if [[ "$response" =~ ^([yY][eE][sS]|[yY])+$ ]]; then
          ${COMPOSE} --env-file .env -f ${COMPOSEFILE} stop solr
#          rm -fr containerdata/solr/cores/data/tecadigitale/data/index
#          rm -fr containerdata/solr/cores/data/tecadigitale_delivery/data/index
          mv containerdata/solr/cores/data/tecadigitale/data/index containerdata/solr/cores/data/tecadigitale/data/index_old
          mv containerdata/solr/cores/data/tecadigitale_delivery/data/index containerdata/solr/cores/data/tecadigitale_delivery/data/index_old
          cp dockerdata/solr/configsets/tecadigitale/conf/schema.xml containerdata/solr/cores/data/tecadigitale/conf/schema.xml
          cp dockerdata/solr/configsets/tecadigitale_delivery/conf/schema.xml containerdata/solr/cores/data/tecadigitale_delivery/conf/schema.xml
          ${COMPOSE} --env-file .env -f ${COMPOSEFILE} start solr
          echo "Clear vfs directory, if needed."
        fi
    else
        exit 0
    fi
}


function reinitCores(){
    read -r -p "${bold}Are you REALLY sure to DELETE data from solr cores? [y/N] ${normal}" response
    if [[ "$response" =~ ^([yY][eE][sS]|[yY])+$ ]]; then
        docker exec -it sbnteca-remota-paas_solr_1 solr delete -c tecadigitale_delivery
        docker exec -it sbnteca-remota-paas_solr_1 solr delete -c tecadigitale
        docker exec -it sbnteca-remota-paas_solr_1 solr create_core -c tecadigitale -d /opt/solr/server/solr/configsets/tecadigitale
        docker exec -it sbnteca-remota-paas_solr_1 solr create_core -c tecadigitale_delivery -d /opt/solr/server/solr/configsets/tecadigitale_delivery
    else
        exit 0
    fi
}

function checkProdNegHostname(){
    if [ $(hostname) != "tdi" ]
    then
        echo -e "\nERROR: you are using a wrong file, it is for production.\n"
        exit 1
    fi
}

function checkProdPosHostname(){
    if [ $(hostname) == "tdi" ]
    then
        echo -e "\nERROR: you are using a wrong file, it is not for production.\n"
        exit 1
    fi
}

function updateSchemaConfig(){
    sudo cp dockerdata/solr/configsets/tecadigitale/conf/solrconfig.xml containerdata/solr/cores/data/tecadigitale/conf/
    sudo cp dockerdata/solr/configsets/tecadigitale_delivery/conf/solrconfig.xml containerdata/solr/cores/data/tecadigitale_delivery/conf/
    sudo cp dockerdata/solr/configsets/tecadigitale/conf/schema.xml containerdata/solr/cores/data/tecadigitale/conf/
    sudo cp dockerdata/solr/configsets/tecadigitale_delivery/conf/schema.xml containerdata/solr/cores/data/tecadigitale_delivery/conf/
    docker restart sbnteca-remota-paas_solr_1
}

function checkHelp(){
  if [  $# -eq 0 ]
  then
          echo -e "\nUsage: $0 [command]"
          echo -e "Command:"
          echo -e "   build"
          echo -e "   install"
#          echo -e "   push-images"
#          echo -e "   pull-images"
          echo -e "   start"
          echo -e "   stop"
          exit 1
  fi
}
