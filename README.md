# Question Answering System

## Opis Projektu

Question Answering System to aplikacja, która pozwala użytkownikom zadawać pytania przez przesłanie pliku audio (wav). 
System zapisuje plik audio na filesystemie, transkrybuje go do tekstu za pomocą usługi Azure Speech, 
a następnie używa usługi Azure Question Answering do uzyskania odpowiedzi na pytanie zawarte w transkrypcji. 
Transkrypcja oraz odpowiedź są zapisywane w bazie danych PostgreSQL i wyświetlane w GUI.

## Technologie

- **Backend**: Java, Spring Boot
- **Baza Danych**: PostgreSQL
- **Usługi Chmurowe**: Azure Speech, Azure Question Answering
- **Inne**: Maven, Docker

## Konfiguracja projektu

### Wymagania wstępne

- Java 17
- Apache Maven
- Docker (opcjonalnie do uruchomienia PostgreSQL)
- Konto Azure z subskrypcją dla usług Speech i Question Answering
- Keycloak 22.03

### Obługa aplikacji

1. Należy zainstalować wersję Javy 17.
2. Należy zainstalować DBMS PostgreSQL i stworzyć w nim bazę danych.
3. Należy zainstalować Keycloak w wersji 22.03.
4. Należy przejść do pliku resources/application.properties w aplikacji backendowej.
5. We wspomnianym pliku ustawiamy adres, port, nazwę naszej bazy danych oraz dane dostępowe username i password.
6. Należy uruchomić panel administracyjny Keycloak.
7. Tworzymy realm oraz dwa klienty - jeden dla aplikacji backendowej a drugi dla frontendowej.
8. Klient dla aplikacji backendowej powinien mieć włączone zaznaczone następujące ustawienia:
Client authentication: on
Authorization: on
Authentication flow -> Standard flow, Direct access grants, Implicit flow
Następnie w zakładce "Service accounts roles" musi mieć przypisaną rolę "realm-admin".
9. Klient dla aplikacji frontendowej zostawiamy z ustawieniami domyślnymi.
10. Tworzymy realm role o nazwie "user".
11. Tworzymy z poziomu panelu konto dla użytkownika będącego administratorem.
12. Wracamy do pliku application.properties i ustawiamy pozostałe ustawienia:
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://{keycloakHost}:{keycloakPort}/realms/qas
keycloak.admin.auth.url=http://{keycloakHost}:{keycloakPort}/realms/qas/protocol/openid-connect/token
keycloak.admin.auth.client.id={id klienta back-endowego}
keycloak.admin.auth.client.secret={sekret klienta back-endowego}
keycloak.admin.auth.username={username konta administratora, które utworzyliśmy ręcznie w panelu}
keycloak.admin.auth.password={hasło konta administratora, które utworzyliśmy ręcznie w panelu}
keycloak.admin.auth.granttype=client_credentials
keycloak.admin.users.url=http://{keycloakHost}:{keycloakPort}/admin/realms/qas/users
files.path={ścieżka absolutna do folderu w którym przechowywane będą pliki dźwiękowe}
13. Logujemy się do usługi Azure i tworzymy usługę mowy.
14. Uruchamiamy cmd i tworzymy zmienne środowiskowe:
setx SPEECH_KEY {klucz z usługi}
setx SPEECH_REGION {region w którym stworzyliśmy usługę}
15. W usłudze Azure tworzymy usługę odpowiedzi na pytania tworząc zmienną header:
    Ocp-Apim-Subscription-Key{klucz subskrypcji dla usługi}
16. Należy także dodać ten klucz dla aplication.properties projektu:
    azure.questionanswering.endpoint {punkt końcowy usługi}
    azure.questionanswering.api-key {klucz do korzystania z API usługi}

### Grupa
-Kamil Erbel
-Wojciech Szymański
-Karol Zięba
-Jakub Ziomek
