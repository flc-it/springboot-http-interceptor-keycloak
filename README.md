# springboot-http-interceptor-keycloak

## Présentation
Le projet *springboot-http-interceptor-keycloak* est la librairie pour la gestion et l'injection du token Keycloak dans les appels HTTP.

## Fonctionnalités

- Fournit l'intercepteur Oauth2 Keycloak pour appeler les autres API : **org.flcit.springboot.http.interceptor.keycloak.client.KeycloakOAuth2TokenInterceptor**

## Frameworks
- [Spring boot](https://spring.io/projects/spring-boot) [@2.7.18](https://docs.spring.io/spring-boot/docs/2.7.18/reference/html)

## Dependencies
- [FLC HTTP Client Rest](https://github.com/flc-it/springboot-http-client-rest)

## Intercepteur Oauth2

### La configuration de l'intercepteur Keycloak Oauth2 s'effectue via 3 properties

Url pour générer les tokens bearer :
```properties
oauth2.keycloak.client.url=${keycloak.auth-server-url}/realms/[REALM]/protocol/openid-connect/token
```
Nom du client dans le royaume des API :
```properties
oauth2.keycloak.client.client-id=@project.parent.artifactId@
```
Secret du client dans le royaume des API :
```properties
oauth2.keycloak.client.client-secret=c5895099-cf30-3612-d592-dd0d4jc959f6
```

### Exemple d'implémentation
```properties
oauth2.keycloak.client.url=https://keycloak/auth/realms/collaborateurs/protocol/openid-connect/token
oauth2.keycloak.client.client-id=flc-storage-api
oauth2.keycloak.client.client-secret=c5895099-cf30-3612-d592-dd0d4jc959f6
```

Injection de l'intercepteur dans un Rest Template :
```java
@Component
@ConfigurationProperties("flc.storage.service")
public class FlcStorageRestTemplate extends RestTemplate {

    @Autowired
    private KeycloakOAuth2TokenInterceptor oauth2TokenInterceptor;

    @Override
    protected void init() throws Exception {
        super.setInterceptors(Collections.singletonList(oauth2TokenInterceptor));
    }

}
```

### Informations importantes
Il faut toujours utiliser cet intercepteur pour appeler les autres API configurées en client credential :
- L'intercepteur peut être utilisé autant de fois que nécessaire.
- Il est généré si la propriété oauth2.keycloak.client.client-id est détectée.

## Projets dépendants
- [api](https://github.com/flc-it/api)