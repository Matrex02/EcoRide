package com.ECO.login_System.appuser;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {


    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Reservation> previousReservations;

    @SequenceGenerator(
            name = "Eco_sequence",
            sequenceName = "Eco_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Eco_sequence"
    )

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String titel;
    private String adresse;
    private String plz;
    private String stadt;
    private String telefonnummer;
    private String geburtsdatum;
    private String geburtsort;
    private String fuehrerscheinnummer;
    private String erteilungsdatum;
    private String ablaufdatum;
    private String ausstellungsort;
    private String personalausweisnummer;
    private String reisepassnummer;
    private String tarif;
    private String payMethod;

    private String operatingSystem;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked = false;
    private Boolean enabled = false;



    public AppUser(String userName, String firstName, String lastName, String email, String password,
                   String titel, String adresse, String plz, String stadt, String telefonnummer,
                   String geburtsdatum, String geburtsort, String fuehrerscheinnummer, String erteilungsdatum,
                   String ablaufdatum, String ausstellungsort, String personalausweisnummer,
                   String reisepassnummer, String tarif, AppUserRole appUserRole, String payMethod,
                   String operatingSystem) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.titel = titel;
        this.adresse = adresse;
        this.plz = plz;
        this.stadt = stadt;
        this.telefonnummer = telefonnummer;
        this.geburtsdatum = geburtsdatum;
        this.geburtsort = geburtsort;
        this.tarif = tarif;
        this.fuehrerscheinnummer = fuehrerscheinnummer;
        this.erteilungsdatum = erteilungsdatum;
        this.ablaufdatum = ablaufdatum;
        this.ausstellungsort = ausstellungsort;
        this.personalausweisnummer = personalausweisnummer;
        this.reisepassnummer = reisepassnummer;
        this.appUserRole = appUserRole;
        this.payMethod = payMethod;
        this.operatingSystem = operatingSystem;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return titel;
    }


    public String getAdresse() {
        return adresse;
    }

    public String getPlz() {
        return plz;
    }

    public String getStadt() {
        return stadt;
    }


    public String getTelefonnummer() {
        return telefonnummer;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getGeburtsort() {
        return geburtsort;
    }

    public String getFuehrerscheinnummer() {
        return fuehrerscheinnummer;
    }

    public String getErteilungsdatum() {
        return erteilungsdatum;
    }

    public String getAblaufdatum() {
        return ablaufdatum;
    }

    public String getAusstellungsort() {
        return ausstellungsort;
    }

    public String getPersonalausweisnummer() {
        return personalausweisnummer;
    }

    public String getReisepassnummer() {
        return reisepassnummer;
    }

    public String getTarif() {
        return tarif;
    }

    public AppUserRole getAppUserRole() {
        return appUserRole;
    }

    public Boolean getLocked() {
        return locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPayMethod() {
        return payMethod;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {

        this.operatingSystem = operatingSystem;
    }
}