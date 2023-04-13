#!/bin/bash

LOCALDIR=$(dirname "$0")
DOCKER=$(which docker)
MAVEN=$(which mvn)
COMPOSE=$(which docker-compose)
COMPOSE2="${DOCKER} compose"
GIT=$(which git)
CURL=$(which curl)
BREW=$(which brew)
NSS=$(which nss)
CERTUTIL=$(which certutil)
bold=$(tput bold)
normal=$(tput sgr0)


function _checkDirectory(){
    if [ ! -d $1 ]; then
        sudo mkdir -p $1
    fi
    sudo chmod 777 $1
}

function _docker() {
    if [ -z "${DOCKER}" ]; then
        echo -e "ERROR: docker command is required\n"
        exit 1
    fi
    if [ -z "${COMPOSE}" ]; then
        if [ -z "${COMPOSE2}" ]; then
          echo -e "ERROR: docker-compose command is required\n"
          exit 1
        else
          COMPOSE="${COMPOSE2}"
        fi
    fi
}

function _maven() {
    if [ -z "${MAVEN}" ]; then
        echo -e "ERROR: mvn command is required\n"
        exit 1
    fi
}

function _env() {
    if [ ! -f .env ]; then
	    echo -e "ERROR: must create .env file\n"
	    exit 1
    fi
}

function _cloneRepos() {
    echo -e "${bold}\nCloning repositories...${normal}\n"
    IFS="|"
    for i in ${REPO}; do
        IFS=" "
        read -a repository <<< $i
        if [ ! -d ../app/${repository[1]} ]
        then
                $GIT -C ../app/ clone ${repository[0]} ${repository[1]} ${repository[2]} ${repository[3]}
                if [ ! $? -eq 0 ]; then
                        echo -e "${bold}\nError to cloning $repository , check permission${normal}"
                        exit 1
                fi
        else
                echo -e "${bold}\nRepository ${repository[1]} already cloned, skipping${normal}"
        fi
    done
}

function _generateCerts() {
    if [[ "${OSTYPE}" == "darwin"* ]]; then
        if [ ! -f certs/wildcard.meta.localdev.pem ]; then
            echo -e "${bold}\nGenerate local SSL certificates\n${normal}"
            if [ -z "${NSS}" ]; then
                echo -e "Installing nss"
                ${BREW} install nss
            fi
            if [ -z "${WGET}" ]; then
                echo -e "Installing wget"
                ${BREW} install wget
            fi
            if [ ! -f certs/mkcert-v1.4.1-darwin-amd64 ]; then
                wget https://github.com/FiloSottile/mkcert/releases/download/v1.4.1/mkcert-v1.4.1-darwin-amd64 -P certs/
                chmod +x certs/mkcert-v1.4.1-darwin-amd64
            fi
            certs/mkcert-v1.4.1-darwin-amd64 -cert-file certs/wildcard.meta.localdev.pem -key-file certs/wildcard.meta.localdev-key.pem "*.meta.localdev"
            certs/mkcert-v1.4.1-darwin-amd64 -install
        else
            echo -e "${bold}\nCertificates already generated, skip\n${normal}"
        fi
    else [ "${OSTYPE}" == "linux-gnu" ];
        if [ ! -f certs/wildcard.meta.localdev.pem ]; then
            echo -e "${bold}\nGenerate local SSL certificates\n${normal}"
            if [ -z "${CERTUTIL}" ]; then
                echo -e "Installing certutil"
                sudo apt-get install -y libnss3-tools
            fi
            if [ -z "${WGET}" ]; then
                echo -e "Installing wget"
                sudo apt-get install wget
            fi
            if [ ! -f certs/mkcert-v1.4.1-linux-amd64 ]; then
                wget https://github.com/FiloSottile/mkcert/releases/download/v1.4.1/mkcert-v1.4.1-linux-amd64 -P certs/
                chmod +x certs/mkcert-v1.4.1-linux-amd64
            fi
            certs/mkcert-v1.4.1-linux-amd64 -cert-file certs/wildcard.meta.localdev.pem -key-file certs/wildcard.meta.localdev-key.pem "*.meta.localdev"
            certs/mkcert-v1.4.1-linux-amd64 -install
        else
            echo -e "${bold}\nCertificates already generated, skip\n${normal}"
        fi 
    fi
}

function _addAlias() {
    if [[ "${OSTYPE}" == "darwin"* ]]; then
        echo -e "\nAdd network alias for xdebug...\n"
        sudo ifconfig lo0 alias 10.254.254.254
    elif [[ "${OSTYPE}" == "linux-gnu" ]]; then
        echo -e "\nAdd network alias for xdebug...\n"
        sudo ifconfig lo:0 10.254.254.254 netmask 255.255.255.255 up
    fi
}

function _delAlias() {
    if [[ "${OSTYPE}" == "darwin"* ]]; then
        echo -e "\nRemove network alias...\n"
        sudo ifconfig lo0 10.254.254.254 remove 2>/dev/null
    elif [[ "${OSTYPE}" == "linux-gnu" ]]; then
        echo -e "\nRemove network alias...\n"
        sudo ifconfig lo:0 down 2>/dev/null
    fi
}
function _addHost() {
    for HOSTNAME in $(cat host-local); do
        sudo bin/manage-hosts.sh add ${HOSTNAME}
    done
}

function _delHost() {
    for HOSTNAME in $(cat host-local); do
        sudo bin/manage-hosts.sh remove ${HOSTNAME}
    done
}


