# sbnteca-remota

Applicazione per la gestione di una digital library con fruizione degli oggetti digitali e OAI provider tramite formati MAG e METS-SBN.

# Cartelle del repository

1. app: le applicazioni java
2. paas: files per l'esecuzione con docker-compose e script per build, install e start/stop


# Componenti della piattaforma teca remota

Nella cartella app:

1. sbnteca-remota-system: backend
2. sbnteca-remota-console: frontend
3. sbnteca-remota-metaoaicat: OAI Provider
4. mets2mag: libreria per la trasformazione di METS in MAG
5. sbnmarc-mag: libreria per la comunicazione con SBNMARC
6. skosloader: libreria per la gestione di file SKOS.
7. oaicat-modified: libreria per l'implemetnazione di un provider OAI-PMH


## Build

### Preparazione di una macchina Ubuntu 20

#### Installazione di Jdk e Maven
``` bash
sudo apt install maven # installa anche JDK 11

``` 

#### Installazione di docker
Installa docker secondo le istruzioni di https://docs.docker.com/engine/install/ubuntu/
``` bash
sudo apt-get update
sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
sudo mkdir -m 0755 -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo docker run hello-world
sudo groupadd docker
sudo usermod -aG docker $USER

``` 

#### Eseguire i comandi per il build

1. Script nella paas:
``` bash 
   ./teca-remota build
``` 
2. in alternativo:
``` bash
cd app
cd skosloader && mvn install 
cd sbnmarc-mag && mvn install 
cd oaicat-modified && mvn install 
cd mets2mag && mvn install 
# Creazione dei tre immagini docker
cd sbnteca-remota-parent && mvn spring-boot:build-image

```

## Esecuzione con docker-compose

### Configurazione

1. Crea file .env (può essere anche vuoto o mancante)

Variabili tramite file .env:

- HTTP_PROTOCOL_SITE: default https
- HTTP_PORT_SITE: default 80
- SERVER_BASE: default http://127.0.0.1
- MYSQL_ROOT_USERNAME: default root
- MYSQL_ROOT_PASSWORD: default admin
- DOCKER_HOST_URL: default http://172.17.0.1
- RESTART_OPTION: default no, always | no

Per esempio per l'installazione sotto http://127.0.0.1:
``` bash
HTTP_PROTOCOL_SITE=http
``` 

#### Configurazione audio preview

```
    audio.ffmpeg.fade: "false"      # abilita il fade
    audio.ffmpeg.fade-seconds:2     # durata del fade 
    audio.ffmpeg.cut.seconds:30     # durata del taglio in secondi
    audio.ffmpeg.cut.start:0        # start del taglio
```

Extra feature: cut on runtime:

```    
    audio.cutter.active: "true"         # se applicare un cut
    audio.cutter.usages: 3,4            # su quali usage applicare il cut 
    audio.fullview.ips: 192.168.1.0/24  # IPs eclusi dal cut  
```

#### Configurazione "cambia usage" per IP

whitelist contiene gli IP senza cambio usage, la blacklist contiene
quelli per cambio usage, usages.switch.changes indica quale cambio eseguire.

```
  usages.switch.active: "true"
  usages.switch.ips.whitelist: "172.22.0.0/24"
  usages.switch.ips.blacklist: "172.22.0.3"
  usages.switch.changes: "3->4" # ->0 significa Forbidden.
  usages.switch.notfoundException: "true" ("false", se non trovato rispondi con 3) or "true" -> 404
```

Nota: il proxy deve fare passare gli IP del client con il header

P.e. per Traefik:

```
      - --entryPoints.web.forwardedHeaders.insecure=true
```

o in modo più sicuro:

```
      - --entryPoints.web.forwardedHeaders.trustedIPs=127.0.0.1/32,192.168.1.1/32
```



### Installazione

1. script nella cartella paas:
``` bash 
   ./teca-remota install
   ./teca-remota start
``` 
3. in alternativa:
   1. cd paas
   2. bin/initDirectory.sh
   3. docker compose up -d
   4. bin/create_cores.sh
   5. controlla se l'immagine localhost/sbnteca-remota-system:2.0.0 è "up" con un container paas_tecadigitalesystem_1, altrimenti rieseguire: docker-compose up -d (la creazione del DB potrebbe essere troppo lento per il primo avvio.)

### Accesso

L'indirizzo (secondo la configurazione di SERVER_BASE) da digitare nel browser per raggiungere il progetto è:
 - http://127.0.0.1/console 

Credenziali d'accesso:
- admin/admin (preferibilmente da modificare dopo il primo accesso)


# Libreria redistribuita

- fork di oaicat dalla versione 1.5.59, vedi https://www.oclc.org/research/areas/data-science/oaicat.html, https://github.com/openpreserve/oaicat (Apache License, Version 2.0)
- native md5 library **sbnteca-remota-system/src/main/resources/lib/MD5.so** from https://github.com/TritonDataCenter/java-fast-md5 (GNU Lesser General Public License v2.1)


# Licenze di schemi XSD e XSL redistribuiti

- mets2mag/src/main/resources/mets/METSRights.xsd (http://creativecommons.org/publicdomain/zero/1.0/)
- mets2mag/src/main/resources/mets/mets.xsd (http://creativecommons.org/publicdomain/zero/1.0/)
- sbnteca-remota-metaoaicat/src/main/resources/static/metaoaicat/oai2-with-vars.xsl (GNU General Public License)


# Licenze delle librerie JavaScript

- Colorpicker plugin for Twitter Bootstrap v2012 (Apache License, Version 2.0)
- Bootstrap-Datetimepicker v2012 (The MIT License (MIT))
- Colorbox 1.6.4 (The MIT License (MIT))
- DataTable 1.10.11 (The MIT License (MIT))
- Dejavu 0.2.4 (Copyright (c) 2012 IndigoUnited, Free of Charge)
- Fileupload 5.42.3 (The MIT License (MIT))
- jquery.pnofify 1.2.2 (Triple license under the GPL, LGPL, and MPL)
- jquery-jtree 1.0-rc3 (The MIT License (MIT))
- jquery-ui 1.10.4 (The MIT License (MIT))
- jtree 3.3.2 (The MIT License (MIT))
- lodash 4.17.2 (The MIT License (MIT))
- modaal 0.2.11 (The MIT License (MIT))
- pace 1.0.0 (Copyright (c) 2013 HubSpot, Inc., Free of Charge)
- select2 3.2 (Apache License, Version 2.0)
- tiny_mce 3.4.6 (GNU LESSER GENERAL PUBLIC LICENSE)
- underscore 1.8.3 (The MIT License (MIT))
- Glizy (GNU LESSER GENERAL PUBLIC LICENSE)


# Licenza
"sbnteca-remota" è rilasciato con licenza [GNU AFFERO GENERAL PUBLIC LICENSE v3](LICENSE)
