# sbnteca-remota

Applicazioni per la teca remota

1. sbnteca-remota-system: backend
2. sbnteca-remota-console: frontend
3. sbnteca-remota-metaoaicat: OAI Provider

## Build


```

mvn initialize install spring-boot:build-image

```

## Docker

Vedi paas.

## Configurazione

### Configurazione audio preview

```
    audio.ffmpeg.fade:false         # abilita il fade
    audio.ffmpeg.fade-seconds:2     # durata del fade 
    audio.ffmpeg.cut.seconds:30     # durata del taglio in secondi
    audio.ffmpeg.cut.start:0        # start del taglio
```

Extra feature: cut on runtime:

```    
    audio.cutter.active: true           # se applicare un cut
    audio.cutter.usages: 3,4            # su quali usage applicare il cut 
    audio.fullview.ips: 192.168.1.0/24  # IPs eclusi dal cut  
```

### Configurazione "cambia usage" per IP

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





