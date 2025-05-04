# SzybkiPrzepis 🍲

SzybkiPrzepis to aplikacja internetowa stworzona w Spring Boot, która służy jako platforma do dzielenia się przepisami kulinarnymi. Celem projektu jest umożliwienie pasjonatom gotowania łatwego publikowania, odkrywania i oceniania przepisów w przyjaznym interfejsie.

## Kluczowe Funkcjonalności ✨

Aplikacja oferuje szeroki zakres funkcji zarówno dla zwykłych użytkowników, jak i administratorów:

* **Zarządzanie Przepisami:**
    * **Dodawanie:** Zalogowani użytkownicy mogą tworzyć nowe przepisy, podając tytuł, składniki, kroki przygotowania, czas przygotowania, poziom trudności, kategorię oraz opcjonalne tagi.
    * **Przeglądanie:** Dostępna jest lista wszystkich przepisów z możliwością sortowania i filtrowania. Każdy przepis ma dedykowaną stronę szczegółów.
    * **Wyszukiwanie i Filtrowanie:** Użytkownicy mogą wyszukiwać przepisy po tytule oraz filtrować listę według kategorii, poziomu trudności (Łatwy, Średni, Trudny) oraz przypisanych tagów.
    * **Edycja i Usuwanie:** Autorzy przepisów (oraz administratorzy) mogą edytować i usuwać swoje publikacje.

* **System Użytkowników:**
    * **Rejestracja:** Nowi użytkownicy mogą zakładać konta, podając unikalną nazwę użytkownika, adres e-mail i hasło (z walidacją).
    * **Logowanie i Wylogowywanie:** Bezpieczny system logowania i możliwość wylogowania.
    * **Profile Użytkowników:** Każdy zalogowany użytkownik ma dostęp do swojego profilu, gdzie widoczne są jego podstawowe dane (nazwa, e-mail, rola) oraz lista dodanych przez niego przepisów. Istnieje możliwość edycji adresu e-mail oraz zmiany hasła.

* **Interakcja Społecznościowa:**
    * **Komentarze:** Zalogowani użytkownicy mogą dodawać komentarze pod przepisami.
    * **Oceny:** Możliwość oceniania przepisów w skali od 1 do 5 gwiazdek.

* **Organizacja Treści:**
    * **Kategorie:** Przepisy są przypisane do kategorii (np. "Dania główne", "Zupy"), co ułatwia przeglądanie tematyczne.
    * **Tagi:** Możliwość dodawania wielu tagów do przepisu (np. "wegetariańskie", "szybkie", "na imprezę") dla precyzyjniejszego kategoryzowania.

* **Panel Administratora:**
    * **Dashboard:** Podsumowanie statystyk serwisu (liczba użytkowników, przepisów, komentarzy).
    * **Zarządzanie Użytkownikami:** Przegląd listy zarejestrowanych użytkowników wraz z ich rolami.
    * **Zarządzanie Przepisami:** Możliwość przeglądania i usuwania wszystkich przepisów w serwisie.
    * **Zarządzanie Komentarzami:** Przeglądanie i usuwanie komentarzy dodanych przez użytkowników.
    * **Zarządzanie Tagami:** Możliwość dodawania nowych tagów i usuwania istniejących.

## Technologie 💻

Projekt został zrealizowany przy użyciu następujących technologii:

* **Backend:**
    * Java
    * Spring Boot (w tym Spring Web, Spring Data JPA, Spring Security - dla obsługi logowania)
* **Frontend:**
    * Thymeleaf (silnik szablonów)
    * HTML
    * CSS
    * Bootstrap 5 (framework CSS)
    * Bootstrap Icons (ikony)
* **Baza danych:**
    * PostgreSQL
* **Narzędzie budowania:**
    * Gradle

---

*Projekt stworzony przez Agatę Stramską*
